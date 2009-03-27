package strategy.model.map.object;

import org.model.IStat;
import org.model.LinearStat;

import strategy.model.StrategyStat;

public enum MapObjectStat implements StrategyStat {
	MAP_X_POS,
	MAP_Y_POS,
	MAP_WIDTH,
	MAP_HEIGHT,
	MAP_TILE,
	MAP_X_DEST,
	MAP_Y_DEST,
	MAP_FACING,
	MAP_ANIM_FRAME,
	Z_DEPTH;
	
	public static int X_MAX;
	public static int Y_MAX;
	public static int Z_MAX;
	public static int X_MIN;
	public static int Y_MIN;
	public static int Z_MIN;
	public static int TILE_SIZE;
	public static int ANIMATION_MAX;
	
	public final static int MAP_FACE_E = 0;
	public final static int MAP_FACE_N = 1;
	public final static int MAP_FACE_W = 2;
	public final static int MAP_FACE_S = 3;
	
	public static MapObjectStat[] STATS = {MAP_X_POS,MAP_Y_POS,MAP_X_DEST,MAP_Y_DEST,MAP_WIDTH,MAP_HEIGHT,MAP_TILE,MAP_FACING,Z_DEPTH};
	
	public static void initialize(int x_max, int y_max, int z_max, int x_min, int y_min, int z_min, int tile, int animax){
		X_MAX = x_max;
		Y_MAX = y_max;
		Z_MAX = z_max;
		X_MIN = x_min;
		Y_MIN = y_min;
		Z_MIN = z_min;
		TILE_SIZE = tile;
		ANIMATION_MAX = animax;
	}
	
	public static IStat getStat(MapObjectStat stat){
		switch(stat){
		case MAP_X_POS:
			return new LinearStat(0,X_MAX,X_MIN,0);
		case MAP_Y_POS:
			return new LinearStat(0,Y_MAX,Y_MIN,0);
		case MAP_WIDTH:
			return new LinearStat(1,X_MAX,X_MIN,1);
		case MAP_HEIGHT:
			return new LinearStat(1,Y_MAX,Y_MIN,1);
		case MAP_X_DEST:
			return new LinearStat(0,X_MAX,X_MIN,0);
		case MAP_Y_DEST:
			return new LinearStat(0,Y_MAX,Y_MIN,0);
		case Z_DEPTH:
			return new LinearStat(0,Z_MAX,Z_MIN,0);
		case MAP_ANIM_FRAME:
			return new LinearStat(0,ANIMATION_MAX,0,0);
		case MAP_FACING:
			return new LinearStat(MAP_FACE_N,MAP_FACE_S,MAP_FACE_E,MAP_FACE_N);
		default:
			return null;
		}
	}
}
