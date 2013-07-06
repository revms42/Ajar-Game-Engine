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
package org.ajar.age;

/**
 * TODO: Update;
 * GameLoop is the primary loop for game updates. I performs all the gunt work
 * as far as culling which things need to be updated, but is designed to hand
 * off all the display and logic work to other classes (though you could just
 * as easily implement {@link #render()} and {@link #logic()}
 * to do it as well). GameLoop also handles game pause and frameskip.
 * @author revms
 * @since 0.0.0.153
 */
public class GameLoop<A extends Attributes> implements Runnable {

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
	
	private Tourguide<A> tourGuide;
	private Tourguide<A> budgetGuide;
	private Visitor<A> cullingVisitor;
	private Visitor<A> collisionVisitor;
	private Visitor<A> displayVisitor;
	private Visitor<A> logicVisitor;
	private Visitor<A> soundVisitor;
	
	//Used to enable debugging messages.
	public static boolean debug = false;
	
	public GameLoop(Node<A> displayRoot){
		this.displayRoot = displayRoot;
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
	 * TODO: Update;
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
			update(displayRoot);
			
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
				frameSkipUpdate(displayRoot);
				skips++;
				if(debug)System.out.println("Skip!");
			}
		}
		
		System.exit(0);
	}
	
	/**
	 * TODO: Needs update.
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
	protected void update(Node<A> root){
		assessUpdate(root);
		if(running){
			if(!isPaused){
				logic();
				collision();
			}
			render();
			sound();
		}
	}
	
	protected void frameSkipUpdate(Node<A> root){
		assessFrameSkipUpdate(root);
		if(running){
			if(!isPaused){
				logic();
				collision();
			}
		}
	}
	
	protected void assessUpdate(Node<A> root){
		if(tourGuide == null){
			tourGuide = new Tourguide<A>(
					cullingVisitor,
					collisionVisitor,
					logicVisitor,
					displayVisitor,
					soundVisitor
			);
		}
		root.accept(tourGuide);
	}
	
	protected void assessFrameSkipUpdate(Node<A> root){
		if(budgetGuide == null){
			budgetGuide = new Tourguide<A>(
					cullingVisitor,
					collisionVisitor,
					logicVisitor
			);
		}
		root.accept(budgetGuide);
	}
	
	/**
	 * TODO: Update.
	 * Performs the collision checking of {@link Node}s during
	 * the {@link #update(Node, CullingSurface)} call.
	 */
	public void collision(){
		collisionVisitor.process();
	}

	/**
	 * TODO: Update;
	 * Performs the rendering of {@link Node}s to the screen during
	 * the {@link #update(Node, CullingSurface)} call.
	 */
	public void render(){
		displayVisitor.process();
	}
	
	/**
	 * TODO: Update.
	 * Performs any logic updates on {@link Node}s identified during
	 * the {@link #update(Node, CullingSurface)} call.
	 */
	public void logic(){
		logicVisitor.process();
	}

	/**
	 * TODO: Update.
	 * Performs any sound updates on {@link Node}s identified during
	 * the {@link #update(Node, CullingSurface)} call.
	 */
	public void sound(){
		soundVisitor.process();
	}
	
	/**
	 * Returns the current target iteration duration for a game update.
	 * @return	<code>long</code> target duration in milliseconds.
	 */
	public long getUpdatePeriod() {
		return updatePeriod / NANO_TO_MILLI;
	}
	
	public Visitor<A> getCollisionVisitor() {
		return collisionVisitor;
	}

	public void setCollisionVisitor(Visitor<A> collisionVisitor) {
		if(tourGuide != null){
			if(this.collisionVisitor != null){
				tourGuide.remove(this.collisionVisitor);
			}
			tourGuide.add(this.collisionVisitor);
		}
		if(budgetGuide != null){
			if(this.collisionVisitor != null){
				budgetGuide.remove(this.collisionVisitor);
			}
			budgetGuide.add(this.collisionVisitor);
		}
		this.collisionVisitor = collisionVisitor;
	}

	public Visitor<A> getDisplayVisitor() {
		return displayVisitor;
	}

	public void setDisplayVisitor(Visitor<A> displayVisitor) {
		if(tourGuide != null){
			if(this.displayVisitor != null){
				tourGuide.remove(this.displayVisitor);
			}
			tourGuide.add(this.displayVisitor);
		}
		this.displayVisitor = displayVisitor;
	}

	public Visitor<A> getLogicVisitor() {
		return logicVisitor;
	}

	public void setLogicVisitor(Visitor<A> logicVisitor) {
		if(tourGuide != null){
			if(this.logicVisitor != null){
				tourGuide.remove(this.logicVisitor);
			}
			tourGuide.add(this.logicVisitor);
		}
		if(budgetGuide != null){
			if(this.logicVisitor != null){
				budgetGuide.remove(this.logicVisitor);
			}
			budgetGuide.add(this.logicVisitor);
		}
		this.logicVisitor = logicVisitor;
	}

	public Visitor<A> getSoundVisitor() {
		return soundVisitor;
	}

	public void setSoundVisitor(Visitor<A> soundVisitor) {
		if(tourGuide != null){
			if(this.soundVisitor != null){
				tourGuide.remove(this.soundVisitor);
			}
			tourGuide.add(this.soundVisitor);
		}
		this.soundVisitor = soundVisitor;
	}

	/**
	 * Sets the current target iteration duration for a game update.
	 * @param 	updatePeriod	<code>long</code> target duration in milliseconds.
	 */
	public void setUpdatePeriod(long updatePeriod) {
		this.updatePeriod = updatePeriod * NANO_TO_MILLI;
	}

	public Visitor<A> getCullingVisitor() {
		return cullingVisitor;
	}

	public void setCullingVisitor(Visitor<A> cullingVisitor) {
		if(tourGuide != null){
			tourGuide.setLeader(this.cullingVisitor);
		}
		if(budgetGuide != null){
			budgetGuide.setLeader(this.cullingVisitor);
		}
		this.cullingVisitor = cullingVisitor;
	}
}
