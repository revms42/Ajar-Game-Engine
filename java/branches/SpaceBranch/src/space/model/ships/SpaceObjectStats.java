package space.model.ships;

import space.display.DisplayStats;
import space.model.component.IComponentType;

public interface SpaceObjectStats extends DisplayStats {

	public final static String STAT_WIDTH = "Width";
	public final static String STAT_HEIGHT = "Width";
	public final static String STAT_MASS = IComponentType.STAT_MASS;
	public final static String STAT_SIGNATURE = IComponentType.STAT_SIGNATURE;
}
