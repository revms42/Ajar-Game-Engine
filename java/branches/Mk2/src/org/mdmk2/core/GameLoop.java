package org.mdmk2.core;

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
	private long updatePeriod = 100L;
	//Start time, end time, actual update length, time to sleep.
	private long start, end, updateInterval, sleepPeriod;
	//Time you've spent in sleep in excess of the target speed.
	private long oversleepTime = 0L;
	//Number of times you haven't yielded to other threads.
	private int noDelays = 0;
	//Amount of time left over from previous operations.
	private long excess = 0L;
	
	public boolean isPaused() {
		return isPaused;
	}

	public void pause() {
		isPaused = !isPaused;
	}

	public void run() {
		start = System.nanoTime();
		while(running){
			logic();
			render();
			
			end = System.nanoTime();
			updateInterval = end - start;
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
			}
		}
		
		System.exit(0);
	}
	
	public void render(){
		if(!isPaused && running){
			//TODO: Render here.
		}
	}
	
	public void logic(){
		if(!isPaused && running){
			//TODO: logic here.
		}
	}

	public long getUpdatePeriod() {
		return updatePeriod;
	}

	public void setUpdatePeriod(long updatePeriod) {
		this.updatePeriod = updatePeriod;
	}
}
