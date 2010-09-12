package org.mdmk2.core;
/**
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
 * MDMk2
 * org.mdmk2.core.display
 * Displayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */

import java.util.Vector;

import org.mdmk2.core.collision.Collidable;
import org.mdmk2.core.disp2d.Displayable;
import org.mdmk2.core.logic.Stated;
import org.mdmk2.core.node.Node;

/**
 * GameLoop is the primary loop for game updates. I performs all the gunt work
 * as far as culling which things need to be updated, but is designed to hand
 * off all the display and logic work to other classes (though you could just
 * as easily implement {@link GameLoop.render(Node<R>)} and {@link GameLoop.logic(Node<R>)}
 * to do it as well). GameLoop also handles game pause and frameskip.
 * @author mstockbridge
 * 15-May-10
 * @param <R>	the "range" type parameter, indicating the format of the screen's view.
 */
public abstract class GameLoop<R> implements Runnable {

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
	
	protected final Node<R,?> displayRoot;
	protected final Vector<Stated<?>> needsStatusUpdate;
	//TODO: This prevents it from working in 3D, so it may need to come out later.
	protected final Vector<Displayable> needsDisplayUpdate;
	protected final Vector<Collidable<?>> needsCollisionCheck;
	
	//Used to enable debugging messages.
	public static boolean debug = false;
	
	public GameLoop(Node<R,?> displayRoot){
		this.displayRoot = displayRoot;
		needsStatusUpdate = new Vector<Stated<?>>();
		needsDisplayUpdate = new Vector<Displayable>();
		needsCollisionCheck = new Vector<Collidable<?>>();
	}
	
	/**
	 * Returns the current "range" in consideration. e.g. for 2D display
	 * based on a Swing component, this would probably be a {@link Rectangle}
	 * representing the displayable area.
	 * mstockbridge
	 * May 10, 2010
	 * @return	an <code>R</code> representation of the range.
	 */
	public abstract R getRange();
	
	/**
	 * Returns the whether the game is paused.
	 * mstockbridge
	 * May 10, 2010
	 * @return	<code>true</code> if the game is paused, else <code>false</code>.
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * Toggles the value of <code>isPaused</code>.
	 * mstockbridge
	 * May 10, 2010
	 */
	public void pause() {
		isPaused = !isPaused;
	}

	/**
	 * The primary method of the game loop. Recursively updates the game by calling
	 * the {@link GameLoop.update(Node<R>,R)} method. Allowances are made to attempt
	 * to maintain the speed specified by the {@link GameLoop.updatePeriod} field, 
	 * with sleep added when the loop is running fast. When the loop runs slow this 
	 * method will attempt to run multiple logic updates without displaying (note that
	 * in the current configuration this will be limited to just those entities 
	 * defined to be in range during the last full update). Also, there are 
	 * periodic {@link Thread.yield()} calls made when the game is running 
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
			update(displayRoot, getRange());
			
			end = System.nanoTime();
			updateInterval = end - start;
			if(debug)System.out.println(updateInterval/NANO_TO_MILLI);
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
				logic();
				skips++;
				if(debug)System.out.println("Skip!");
			}
		}
		
		System.exit(0);
	}
	
	/**
	 * This method performs the culling of elements from the display root
	 * based on the "range", usually obtained via the {@link GameLoop.getRange()} 
	 * function, then displays and updates as necessary.
	 * <p>
	 * This function should execute in O=log(n) time.
	 * mstockbridge
	 * May 13, 2010
	 * @param 	root	the {@link Node} representing the root to be updated (
	 * 					generally the {@link GameLoop.displayRoot} field).
	 * @param 	range	the <code>R</code> type "range", or area currently in view
	 * 					or under consideration for update. 
	 */
	protected void update(Node<R,?> root, R range){
		for(Node<R,?> node : root.getChildren()){
			if(node.isInRange(range)){
				if(node instanceof Displayable){
					Displayable d = (Displayable)node;
					
					if(d.needsDisplayUpdate()){
						needsDisplayUpdate.add(d);
					}
				}
				if(node instanceof Stated){
					Stated<?> s = (Stated<?>)node;
					
					if(s.needsStateUpdate()){
						needsStatusUpdate.add(s);
					}
				}
				if(node instanceof Collidable){
					@SuppressWarnings("unchecked")
					Collidable<?> c = (Collidable<?>)node;
					
					if(c.needsCollisionCheck()){
						needsCollisionCheck.add(c);
					}
				}
				
				if(node.hasChildren()){
					update(node,range);
				}
			}
		}
		if(running){
			if(!isPaused){
				logic();
				collision();
			}
			render();
		}
		needsStatusUpdate.removeAllElements();
		needsDisplayUpdate.removeAllElements();
		needsCollisionCheck.removeAllElements();
	}
	
	/**
	 * mstockbridge
	 * 13-Jun-10
	 */
	public abstract void collision();

	/**
	 * Performs the rendering of {@link Node}s to the screen during
	 * the {@link GameLoop.update(Node<R>,R)} call.
	 * mstockbridge
	 * May 10, 2010
	 */
	public abstract void render();
	
	/**
	 * Performs any logic updates on {@link Node}s identified during
	 * the {@link GameLoop.update(Node<R>,R)} call.
	 * mstockbridge
	 * May 10, 2010
	 */
	public abstract void logic();

	/**
	 * Returns the current target iteration duration for a game update.
	 * mstockbridge
	 * May 10, 2010
	 * @return	<code>long</code> target duration in milliseconds.
	 */
	public long getUpdatePeriod() {
		return updatePeriod / NANO_TO_MILLI;
	}
	
	/**
	 * Sets the current target iteration duration for a game update.
	 * mstockbridge
	 * May 13, 2010
	 * @param 	updatePeriod	<code>long</code> target duration in milliseconds.
	 */
	public void setUpdatePeriod(long updatePeriod) {
		this.updatePeriod = updatePeriod * NANO_TO_MILLI;
	}
}
