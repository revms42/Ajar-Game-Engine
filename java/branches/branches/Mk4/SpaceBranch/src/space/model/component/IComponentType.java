package space.model.component;

import java.util.Vector;


public interface IComponentType {
	public final static String STAT_RATING = "rating";
	public final static String STAT_HITPOINTS = "hitpoints";
	public final static String STAT_ENERGY = "energy";
	public final static String STAT_DAMAGETYPE = "type";
	public final static String STAT_MASS = "mass";
	public final static String STAT_SIGNATURE = "signature";
	public final static String STAT_POWER = "power";
	public final static String STAT_STORAGE = "storage";
	public final static String STAT_RANGE = "range";
	public final static String STAT_RADIUS = "radius";
	public final static String STAT_SPEED = "speed";
	public final static String STAT_AGILITY = "agility";
	public final static String STAT_FIRINGARC = "firing-arc";
	public final static String STAT_ACCURACY = "accuracy";
	public final static String STAT_RELOAD = "reload";
	public final static String STAT_BURST = "burst";
	public final static String STAT_WIDTH = "bay width";
	public final static String STAT_HEIGHT = "bay height";
	
	public Vector<String> getAssociatedStats();
	public String getName();
	public String getCatagoryName();
}
