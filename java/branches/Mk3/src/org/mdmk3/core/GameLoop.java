package org.mdmk3.core;
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

import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.cull.CullingSurface;
import org.mdmk3.core.disp2d.Displayable;
import org.mdmk3.core.logic.Entity;

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
public abstract class GameLoop<A extends Attributes> implements Runnable {

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
	
	protected final Node<A> displayRoot;
	protected final Vector<Entity<A>> needsStatusUpdate;
	//TODO: This prevents it from working in 3D, so it may need to come out later.
	protected final Vector<Displayable<A>> needsDisplayUpdate;
	protected final Vector<Collidable<A>> needsCollisionCheck;
	private CullingSurface<A> cullingSurface;
	
	//Used to enable debugging messages.
	public static boolean debug = false;
	
	private Class<? extends Displayable<A>> dispClass;
	private Class<? extends Entity<A>> entityClass;
	private Class<? extends Collidable<A>> collClass;
	
	public GameLoop(Node<A> displayRoot){
		this.displayRoot = displayRoot;
		needsStatusUpdate = new Vector<Entity<A>>();
		needsDisplayUpdate = new Vector<Displayable<A>>();
		needsCollisionCheck = new Vector<Collidable<A>>();
	}
	
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
			update(displayRoot, getCullingSurface());
			
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
				frameSkipUpdate(displayRoot, getCullingSurface());
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
	protected void update(Node<A> root, CullingSurface<A> culler){
		assessUpdate(root,culler);
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
	
	protected void frameSkipUpdate(Node<A> root, CullingSurface<A> culler){
		assessFrameSkipUpdate(root,culler);
		if(running){
			if(!isPaused){
				logic();
				collision();
			}
		}
		needsStatusUpdate.removeAllElements();
		needsCollisionCheck.removeAllElements();
	}
	
	protected void assessUpdate(Node<A> root, CullingSurface<A> culler){
		for(Node<A> node : root.getChildren()){
			if(culler.isInRange(node)){
				checkDisplayUpdate(node);
				checkStateUpdate(node);
				checkCollisionUpdate(node);
				
				if(node.hasChildren()){
					assessUpdate(node,culler);
				}
			}
		}
	}
	
	protected void assessFrameSkipUpdate(Node<A> root, CullingSurface<A> culler){
		for(Node<A> node : root.getChildren()){
			if(culler.isInRange(node)){
				checkStateUpdate(node);
				checkCollisionUpdate(node);
				
				if(node.hasChildren()){
					assessUpdate(node,culler);
				}
			}
		}
	}

	/**
	 * @param node
	 */
	protected void checkCollisionUpdate(Node<A> node) {
		if(node.hasCapability(collClass)){
			Collidable<A> c = node.getDecorator(collClass);
			
			if(c.needsCollisionCheck()){
				needsCollisionCheck.add(c);
			}
		}
	}

	/**
	 * @param node
	 */
	protected void checkStateUpdate(Node<A> node) {
		if(node.hasCapability(entityClass)){
			Entity<A> s = node.getDecorator(entityClass);
			
			if(s.needsStateUpdate()){
				needsStatusUpdate.add(s);
			}
		}
	}

	/**
	 * @param node
	 */
	protected void checkDisplayUpdate(Node<A> node) {
		if(node.hasCapability(dispClass)){
			Displayable<A> d = node.getDecorator(dispClass);
			
			if(d.needsDisplayUpdate()){
				needsDisplayUpdate.add(d);
			}
		}
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

	/**
	 * @param cullingSurface the cullingSurface to set
	 */
	public void setCullingSurface(CullingSurface<A> cullingSurface) {
		this.cullingSurface = cullingSurface;
	}

	/**
	 * @return the cullingSurface
	 */
	public CullingSurface<A> getCullingSurface() {
		return cullingSurface;
	}

	public Class<? extends Displayable<A>> getDisplayableClass() {
		return dispClass;
	}

	public void setDisplayableClass(Class<? extends Displayable<A>> dispClass) {
		this.dispClass = dispClass;
	}
	
	public Class<? extends Entity<A>> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<? extends Entity<A>> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<? extends Collidable<A>> getCollidableClass() {
		return collClass;
	}

	public void setCollidableClass(Class<? extends Collidable<A>> collClass) {
		this.collClass = collClass;
	}
}
