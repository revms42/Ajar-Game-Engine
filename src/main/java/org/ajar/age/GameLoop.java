/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) May 10, 2010 Matthew Stockbridge
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * (but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * ajar
 * org.ajar.age
 * GameLoop.java
 */
package org.ajar.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GameLoop is the primary loop for game updates. I performs all the gunt work
 * as far as culling which things need to be updated, but is designed to hand
 * off all the display and logic work to other classes. GameLoop also handles
 * game pause and frameskip.
 * @author revms42
 * @since 0.0.0.153
 */
public class GameLoop implements Runnable {

    //Convert a nanosecond value to milliseconds.
    private static final long NANO_TO_MILLI = 1000000L;
    //Number of times to execute without delays before yeilding to other threads.
    private static final int NO_DELAYS_BEFORE_YEILD = 10;
    //Max number of logic cycles to run before animating.
    private static final int MAX_FRAMESKIP = 10;

    //Are you paused?
    private volatile boolean isPaused = false;
    //Are you running?
    private volatile boolean running = true;

    //Target update speed.
    private long updatePeriod = 100L * NANO_TO_MILLI;
    //Start time, end time, actual update length, time to sleep.
    private long start, end, updateInterval, sleepPeriod;
    //Time you've spent in sleep in excess of the target speed.
    private long oversleepTime = 0L;
    //Number of times you haven't yielded to other threads.
    private int noDelays = 0;
    //Amount of time left over from previous operations.
    private long excess = 0L;

    protected final Node rootNode;

    private LogWrapper logger;

    private CompositeVisitor tourGuide;
    private CompositeVisitor budgetGuide;
    private CompositeVisitor pausedGuide;
    private CompositeVisitor pausedBudgetGuide;

    //Used to enable debugging messages.
    public static boolean debug = false;

    protected GameLoop(Node displayRoot){
        this.rootNode = displayRoot;
        this.logger = new StreamLogger();
    }

    /**
     * Returns the whether the game is paused.
     * @return	<code>true</code> if the game is paused, else <code>false</code>.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Toggles the value of <code>isPaused</code>.
     */
    public void pause() {
        isPaused = !isPaused;
    }

    /**
     * The primary method of the game loop. Recursively updates the game by calling
     * the {@link #update(Node)} method. Allowances are made to attempt
     * to maintain the speed specified by the {@link GameLoop#updatePeriod} field,
     * with sleep added when the loop is running fast. When the loop runs slow this
     * method will attempt to run multiple logic updates without displaying (note that
     * in the current configuration this will be limited to just those entities
     * defined to be in range during the last full update). Also, there are
     * periodic {@link Thread#yield()} calls made when the game is running
     * slow to prevent monopolizing.
     * <p>
     * This implementation is based heavily on the one from "Killer Game Programming
     * with Java" by Andrew Davidson. Copyright 2005 O'Reilly Media, Inc., 0-596-00730-2.
     */
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        start = System.nanoTime();
        while(running){
            update(rootNode);

            end = System.nanoTime();
            updateInterval = end - start;
            if(debug) logTime(start,"Total");
            if(debug) logger.log(LogWrapper.LogLevel.VERBOSE,"Target: " + updatePeriod/NANO_TO_MILLI + " ms");
            sleepPeriod = (updatePeriod - updateInterval) - oversleepTime;

            if(sleepPeriod > 0){
                try{
                    Thread.sleep(sleepPeriod/NANO_TO_MILLI);
                }catch (InterruptedException ie){
                    //Do nothing.
                }
            }else{
                excess -= sleepPeriod;
                oversleepTime = 0L;

                if(++noDelays >= NO_DELAYS_BEFORE_YEILD) {
                    Thread.yield();
                    noDelays = 0;
                }
            }

            start = System.nanoTime();

            int skips = 0;
            while((excess > updatePeriod) && (skips < MAX_FRAMESKIP)){
                excess -= updatePeriod;
                frameSkipUpdate(rootNode);
                skips++;
                if(debug) logger.log(LogWrapper.LogLevel.VERBOSE, "Skip!");
            }
        }

        System.exit(0);
    }

    /**
     * This method performs an update of the entire <code>Node</code> tree starting at the
     * root node.
     * This function should execute in O=Log(n) time.
     * @param 	root	the {@link Node} representing the root to be updated (
     * 					generally the {@link GameLoop#rootNode} field).
     */
    protected void update(Node root){
        if(running){
            logger.log(LogWrapper.LogLevel.VERBOSE, "Starting update");
            if(isPaused){
                assessUpdate(root, pausedGuide);
                if(debug) logTime(start, "Finished assessment (paused) in");
                pausedGuide.visit();
                if(debug) logTime(start, "Finished pass (paused) in");
            }else{
                assessUpdate(root, tourGuide);
                if(debug) logTime(start, "Finished assessment in");
                tourGuide.visit();
                if(debug) logTime(start, "Finished pass in");
            }
        }
    }

    protected void frameSkipUpdate(Node root){
        if(running){
            logger.log(LogWrapper.LogLevel.VERBOSE, "Starting frameskip update");
            if(debug) start = System.nanoTime();
            if(isPaused){
                assessUpdate(root, pausedBudgetGuide);
                if(debug) logTime(start, "Finished assessment (paused) in");
                pausedBudgetGuide.visit();
                if(debug) logTime(start, "Finished pass (paused) in");
            }else{
                assessUpdate(root, budgetGuide);
                if(debug) logTime(start, "Finished assessment in");
                budgetGuide.visit();
                if(debug) logTime(start, "Finished pass in");
            }
        }
    }

    protected void assessUpdate(Node root, Visitor assessor){
        if(assessor != null) root.accept(assessor);
    }

    private void logTime(long start, String prefix){
        long end = System.nanoTime();
        if(end - start < NANO_TO_MILLI){
            logger.log(LogWrapper.LogLevel.VERBOSE, prefix + ": " + (end - start) + " ns");
        }else{
            logger.log(LogWrapper.LogLevel.VERBOSE, prefix + ": " + ((end - start)/NANO_TO_MILLI) + " ms");
        }
    }

    /**
     * Returns the current target iteration duration for a game update.
     * @return	<code>long</code> target duration in milliseconds.
     */
    public long getUpdatePeriod() {
        return updatePeriod / NANO_TO_MILLI;
    }

    /**
     * Sets the current target iteration duration for a game update.
     * @param 	updatePeriod	<code>long</code> target duration in milliseconds.
     */
    public void setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod * NANO_TO_MILLI;
    }

    public LogWrapper getLogger() {
        return logger;
    }

    public void setLogger(LogWrapper logger) {
        this.logger = logger;
    }

    public enum UpdateMode {
        NODE_CULLING(1),
        SKIPPABLE(2),
        PAUSABLE(4);

        private final int flag;

        UpdateMode(int flag) {
            this.flag = flag;
        }
    }

    public static class GameLoopBuilder {
        private final class OrderedVisitorEntry {

            private final Visitor visitor;
            private final int flags;

            private OrderedVisitorEntry(Visitor visitor, int flags) {
                this.visitor = visitor;
                this.flags = flags;
            }
        }

        protected List<OrderedVisitorEntry> visitors;

        public GameLoopBuilder() {
            visitors = new ArrayList<>();
        }

        /**
         * Adds a new visitor with the specified <code>UpdateMode</code> flags.
         * <p>
         * There are three important things to realize about this method. The first is that order
         * is preserved for the visitors added. This means that they will be called for execution
         * in the order they are added. The second is that if no flags are specified the visitor
         * will be called at every execution, regardless of whether the game is paused or is lagging.
         * Finally, at least one visitor should be specified {@link UpdateMode#NODE_CULLING} in
         * order for the finished GameLoop to work correctly.
         * @param visitor
         * @param flags
         * @return this <code>GameLoopBuilder</code>
         */
        public GameLoopBuilder add(Visitor visitor, UpdateMode... flags){
            int combined = 0;
            for(UpdateMode i : flags) combined = combined | i.flag;

            visitors.add(new OrderedVisitorEntry(visitor,combined));
            return this;
        }

        public GameLoop build(Node root) {
            List<Visitor> runInSkipWhilePaused = new ArrayList<>();
            List<Visitor> runWhilePaused = new ArrayList<>();
            List<Visitor> runInFrameSkip = new ArrayList<>();
            List<Visitor> runInSmoothSailing = new ArrayList<>();

            Visitor culling = visitors.stream().filter(v -> (v.flags & UpdateMode.NODE_CULLING.flag) > 0).findFirst().get().visitor;

            runInSkipWhilePaused.add(culling);
            runWhilePaused.add(culling);
            runInFrameSkip.add(culling);
            runInSmoothSailing.add(culling);

            for(OrderedVisitorEntry ove : visitors) {
                if(ove.visitor == culling) continue;

                runInSmoothSailing.add(ove.visitor);

                boolean unSkippable = false;
                if((ove.flags & UpdateMode.SKIPPABLE.flag) == 0) {
                    runInFrameSkip.add(ove.visitor);
                    unSkippable = true;
                }
                if((ove.flags & UpdateMode.PAUSABLE.flag) == 0) {
                    runWhilePaused.add(ove.visitor);

                    if(unSkippable) {
                        runInSkipWhilePaused.add(ove.visitor);
                    }
                }
            }

            GameLoop loop = new GameLoop(root);
            loop.tourGuide = new CompositeVisitor(runInSmoothSailing);
            loop.budgetGuide = new CompositeVisitor(runInFrameSkip);
            loop.pausedGuide = new CompositeVisitor(runWhilePaused);
            loop.pausedBudgetGuide = new CompositeVisitor(runInSkipWhilePaused);

            return loop;
        }
    }
}
