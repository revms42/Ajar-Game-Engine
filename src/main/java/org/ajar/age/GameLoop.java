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

    public GameLoop(Node displayRoot){
        this.rootNode = displayRoot;
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
        assessUpdate(root);
        if(running){
            if(isPaused){
                pausedGuide.visit();
            }else{
                tourGuide.visit();
            }
        }
    }

    protected void frameSkipUpdate(Node root){
        assessFrameSkipUpdate(root);
        if(running){
            if(isPaused){
                pausedBudgetGuide.visit();
            }else{
                budgetGuide.visit();
            }
        }
    }

    protected void assessUpdate(Node root){
        if(tourGuide != null) root.accept(tourGuide);
    }

    protected void assessFrameSkipUpdate(Node root){
        if(budgetGuide != null) root.accept(budgetGuide);
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

    public class GameLoopBuilder {
        //TODO: Replace this with one list that has a wrapper that takes a value determining when it can be skipped.
        protected Visitor culling;
        protected List<Visitor> always;
        protected List<Visitor> pauseSkip;
        protected List<Visitor> frameSkip;

        public GameLoopBuilder() {
            always = new ArrayList<>();
            pauseSkip = new ArrayList<>();
            frameSkip = new ArrayList<>();
        }

        public GameLoopBuilder culling(Visitor visitor){
            culling = visitor;
            return this;
        }

        public GameLoopBuilder always(Visitor visitor){
            always.add(visitor);
            return this;
        }

        public GameLoopBuilder canFrameskip(Visitor visitor){
            frameSkip.add(visitor);
            return this;
        }

        public GameLoopBuilder skipDuringPause(Visitor visitor){
            pauseSkip.add(visitor);
            return this;
        }

        public GameLoop build(Node root) {
            //TODO: As noted above, this doesn't give the layout options we want.
            List<Visitor> skipPause = new ArrayList<>();
            skipPause.add(culling);
            skipPause.addAll(always);

            List<Visitor> paused = new ArrayList<>();
            paused.addAll(skipPause);
            paused.addAll(frameSkip);

            List<Visitor> skipped = new ArrayList<>();
            skipped.addAll(skipPause);
            skipped.addAll(pauseSkip);

            List<Visitor> all = new ArrayList<>();
            all.addAll(skipPause);
            all.addAll(pauseSkip);
            all.addAll(frameSkip);

            GameLoop loop = new GameLoop(root);
            loop.tourGuide = new CompositeVisitor(all);
            loop.budgetGuide = new CompositeVisitor(skipped);
            loop.pausedGuide = new CompositeVisitor(paused);
            loop.pausedBudgetGuide = new CompositeVisitor(skipPause);

            return loop;
        }
    }
}
