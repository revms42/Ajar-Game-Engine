package strategy.model.map;

import org.model.IStat;
import org.model.LinearStat;

import strategy.model.StrategyStat;

public enum MapStat implements StrategyStat {
	MAP_WIDTH,
	MAP_HEIGHT,
	MAP_TILE,
	MAP_ANIM_FRAME,
	Z_DEPTH;
	
	public static int X_MAX;
	public static int Y_MAX;
	public static int Z_MAX;
	public static int TILE_SIZE;
	public static int ANIMATION_MAX;
	
	public static MapStat[] STATS = {MAP_WIDTH,MAP_HEIGHT,MAP_TILE,Z_DEPTH};
	
	public static void initialize(int x_max, int y_max, int z_max, int tile, int animax){
		X_MAX = x_max;
		Y_MAX = y_max;
		Z_MAX = z_max;
		TILE_SIZE = tile;
		ANIMATION_MAX = animax;
	}
	
	public static IStat getStat(MapStat stat){
		switch(stat){
		case MAP_WIDTH:
			return new LinearStat(0,X_MAX,0,0);
		case MAP_HEIGHT:
			return new LinearStat(0,Y_MAX,0,0);
		case Z_DEPTH:
			return new LinearStat(0,Z_MAX,0,0);
		case MAP_ANIM_FRAME:
			return new LinearStat(0,ANIMATION_MAX,0,0);
		default:
			return null;
		}
	}
}
