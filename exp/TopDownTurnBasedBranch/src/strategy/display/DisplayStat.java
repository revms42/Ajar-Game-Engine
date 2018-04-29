package strategy.display;

import org.model.IStat;
import org.model.LinearStat;

import strategy.model.StrategyStat;

public enum DisplayStat implements StrategyStat {
	DISPLAY_X_POS,
	DISPLAY_Y_POS,
	DISPLAY_WIDTH,
	DISPLAY_HEIGHT,
	DISPLAY_X_OFFSET,
	DISPLAY_Y_OFFSET,
	DISPLAY_FRAME,
	Z_DEPTH;
	
	public static int X_MAX;
	public static int Y_MAX;
	public static int Z_MAX;
	public static int X_MIN;
	public static int Y_MIN;
	public static int Z_MIN;
	public static int TILE_WIDTH;
	public static int TILE_HEIGHT;
	public static int ANIMATION_MAX;
	
	public static DisplayStat[] STATS = {
		DISPLAY_X_POS,
		DISPLAY_Y_POS,
		DISPLAY_WIDTH,
		DISPLAY_HEIGHT,
		DISPLAY_X_OFFSET,
		DISPLAY_Y_OFFSET,
		DISPLAY_FRAME,
		Z_DEPTH
	};
	
	public static void initialize(int x_max, int y_max, int z_max, int x_min, int y_min, int z_min, int width, int height, int animax){
		X_MAX = x_max;
		Y_MAX = y_max;
		Z_MAX = z_max;
		X_MIN = x_min;
		Y_MIN = y_min;
		Z_MIN = z_min;
		TILE_WIDTH = width;
		TILE_HEIGHT = height;
		ANIMATION_MAX = animax;
	}
	
	public static IStat getStat(DisplayStat stat){
		switch(stat){
		case DISPLAY_X_POS:
			return new LinearStat(0,X_MAX,X_MIN,0);
		case DISPLAY_Y_POS:
			return new LinearStat(0,Y_MAX,Y_MIN,0);
		case DISPLAY_WIDTH:
			return new LinearStat(TILE_WIDTH,X_MAX,X_MIN,TILE_WIDTH);
		case DISPLAY_HEIGHT:
			return new LinearStat(TILE_HEIGHT,Y_MAX,Y_MIN,TILE_HEIGHT);
		case DISPLAY_X_OFFSET:
			return new LinearStat(0,X_MAX,X_MIN,0);
		case DISPLAY_Y_OFFSET:
			return new LinearStat(0,ANIMATION_MAX,0,0);
		case DISPLAY_FRAME:
			return new LinearStat(0,Z_MAX,Z_MIN,0);
		case Z_DEPTH:
			return new LinearStat(0,Z_MAX,Z_MIN,0);
		default:
			return null;
		}
	}
	
}
