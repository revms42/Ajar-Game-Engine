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
 * mdj
 * org.mdj.core
 * GameLoop.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core;

import java.util.Vector;

import org.mdj.core.collision.Collidable;
import org.mdj.core.cull.CullingSurface;
import org.mdj.core.disp2d.Displayable;
import org.mdj.core.logic.Entity;

/**
 * GameLoop is the primary loop for game updates. I performs all the gunt work
 * as far as culling which things need to be updated, but is designed to hand
 * off all the display and logic work to other classes (though you could just
 * as easily implement {@link #render()} and {@link #logic()}
 * to do it as well). GameLoop also handles game pause and frameskip.
 * @author revms
 * @since 0.0.0.153
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
	//@TODO This prevents it from working in 3D, so it may need to come out later.
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
	 * the {@link #update(Node, CullingSurface)} method. Allowances are made to attempt
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
	 * based on the "range", specified via the provided <code>CullingSurface</code>  
	 * , then displays and updates as necessary.
	 * <p>
	 * This function should execute in O=log(n) time.
	 * @param 	root	the {@link Node} representing the root to be updated (
	 * 					generally the {@link GameLoop#displayRoot} field).
	 * @param 	culler	the <code>CullingSurface</code> representing what's currently in view
	 * 					or under consideration for update.
	 * @see org.mdj.core.cull.CullingSurface
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
	 * Checks the provided node to determine if it can collide with other nodes during this update period.
	 * @param node the node to check for collisions.
	 * @see org.mdj.core.collision.Collidable#needsCollisionCheck()
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
	 * Checks the provided node to determine if it needs to have it's logic state updated.
	 * @param node the node to check for a logic update.
	 * @see org.mdj.core.logic.Entity#needsStateUpdate()
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
	 * Checks the provided node to determine if it needs to have itself displayed on the screen.
	 * @param node the node to check for a display update.
	 * @see org.mdj.core.disp2d.Displayable#needsDisplayUpdate()
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
	 * Performs the collision checking of {@link Node}s during
	 * the {@link #update(Node, CullingSurface)} call.
	 */
	public abstract void collision();

	/**
	 * Performs the rendering of {@link Node}s to the screen during
	 * the {@link #update(Node, CullingSurface)} call.
	 */
	public abstract void render();
	
	/**
	 * Performs any logic updates on {@link Node}s identified during
	 * the {@link #update(Node, CullingSurface)} call.
	 */
	public abstract void logic();

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

	/**
	 * Sets the CullingSurface, used for culling non-active nodes from logic and display updates.
	 * @param cullingSurface the CullingSurface to use for culling.
	 * @see #update(Node, CullingSurface)
	 */
	public void setCullingSurface(CullingSurface<A> cullingSurface) {
		this.cullingSurface = cullingSurface;
	}

	/**
	 * Gets the CullingSurface being used for culling.
	 * @return the CullingSurface currently being used for culling.
	 * @see #setCullingSurface(CullingSurface)
	 */
	public CullingSurface<A> getCullingSurface() {
		return cullingSurface;
	}

	/**
	 * Gets the <code>Class</code> currently being used for display update checking.
	 * <p>
	 * The GameLoop makes use of each Node's <code>hasCapability</code> method to test whether or not they 
	 * are capable of being displayed. The ones that are get scheduled for a display update.
	 * @return the Class currently being used for display.
	 * @see Node#hasCapability(Class)
	 * @see org.mdj.core.disp2d.Displayable
	 * @see #render()
	 */
	public Class<? extends Displayable<A>> getDisplayableClass() {
		return dispClass;
	}

	/**
	 * Sets the <code>Class</code> to be used for display.
	 * @param dispClass the class to use for display update checking.
	 * @see #getDisplayableClass()
	 */
	public void setDisplayableClass(Class<? extends Displayable<A>> dispClass) {
		this.dispClass = dispClass;
	}
	
	/**
	 * Gets the <code>Class</code> currently being used for logic update checking.
	 * <p>
	 * The GameLoop makes use of each Node's <code>hasCapability</code> method to test whether or not they 
	 * are capable of logic updating. The ones that are get scheduled for a logic update.
	 * @return the class used for logic update checking.
	 * @see Node#hasCapability(Class)
	 * @see org.mdj.core.logic.Entity
	 * @see #logic()
	 */
	public Class<? extends Entity<A>> getEntityClass() {
		return entityClass;
	}

	/**
	 * Sets the <code>Class</code> to be used for logic update checking.
	 * @param entityClass the class to use for logic update checking.
	 * @see #getEntityClass()
	 */
	public void setEntityClass(Class<? extends Entity<A>> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Gets the <code>Class</code> to be used for collision checking.
	 * <p>
	 * The GameLoop makes use of each Node's <code>hasCapability</code> method to test whether or not they 
	 * will interact through collision. The ones that are get scheduled for collision checking.
	 * @return the class used for collision checking.
	 * @see Node#hasCapability(Class)
	 * @see org.mdj.core.collision.Collidable
	 * @see #collision()
	 */
	public Class<? extends Collidable<A>> getCollidableClass() {
		return collClass;
	}

	/**
	 * Sets the <code>Class</code> to be used for collision checking.
	 * @param collClass the class to use for collision checking.
	 * @see #getCollidableClass()
	 */
	public void setCollidableClass(Class<? extends Collidable<A>> collClass) {
		this.collClass = collClass;
	}
}
