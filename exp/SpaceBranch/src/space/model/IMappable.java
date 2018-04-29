package space.model;

import java.awt.Point;

public interface IMappable {

	public static final String STAT_MAP_X = "Map X Pos";
	public static final String STAT_MAP_Y = "Map Y Pos";
	
	public Point getPosition();
	
}
