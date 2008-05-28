package test.ai;

import java.awt.Rectangle;
import java.awt.Shape;

import org.AbstractCharacter;
import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.model.CircularStat;
import org.model.IStats;
import org.model.LinearStat;
import org.model.Stats;

public class Ship extends AbstractCharacter<String,String> implements FieldConstants {

	public Ship(
			IStats<String> stats, 
			IDisplayContext<String,String> context, 
			IDisplayFactory<String,String> factory) 
	{
		super(stats, context, factory);
	}
	
	public static Stats<String> makeStats(){
		Stats<String> stats = new Stats<String>();
		
		stats.put(HEADING, new CircularStat(0,2*Math.PI,-(2*Math.PI),0));
		stats.put(INERTIA, new LinearStat(0.0d,Math.PI/18,-Math.PI/18,0.0d));
		stats.put(POWER, new LinearStat(0.0d,1.0d,-1.0d,0.0d));
		stats.put(RANGE, new LinearStat(25,25,25,25));
		stats.put(REFRESH, new LinearStat(1,-1,0,0));
		stats.put(THRUST, new LinearStat(0.0d,2.0d,-2.0d,0.0d));
		stats.put(TURNING, new LinearStat(0.0d,Math.PI/90,-Math.PI/90,0.0d));
		stats.put(VELOCITY, new LinearStat(0.0d,10.0d,-10.0d,0.0d));
		stats.put(X, new LinearStat(0.0d,380.0d,20.0d,0.0d));
		stats.put(Y, new LinearStat(0.0d,380.0d,20.0d,0.0d));
		
		return stats;
	}

	@Override
	public Ship clone() {
		return new Ship(
				this.getStats().clone(),
				this.getDisplayContext(),
				this.getDisplayFactory()
		);
	}

	@Override
	public int getDisplayDepth() {
		return 0;
	}

	@Override
	public boolean isOnScreen(Rectangle screen) {
		return true;
	}

	@Override
	public Shape getBounds() {
		return null;
	}

}
