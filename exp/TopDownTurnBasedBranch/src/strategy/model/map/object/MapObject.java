package strategy.model.map.object;

import java.awt.Rectangle;
import java.awt.Shape;

import org.AbstractCharacter;
import org.control.IControlMapping;
import org.control.IControllable;
import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.interaction.IEntity;
import org.model.IStats;

import strategy.interaction.StrategyCondition;
import strategy.model.StrategyStat;

public class MapObject extends AbstractCharacter<String,StrategyStat> implements IControllable<StrategyCondition,StrategyStat> {

	private final Rectangle bounds;
	private IControlMapping<StrategyCondition, StrategyStat> mapping;
	
	public MapObject(IStats<StrategyStat> stats, IDisplayContext<String, StrategyStat> context, IDisplayFactory<String, StrategyStat> factory) {
		super(stats, context, factory);
		for(MapObjectStat stat : MapObjectStat.STATS){
			if(stats.getStat(stat) == null){
				stats.setStat(stat, MapObjectStat.getStat(stat));
			}
		}
		bounds = new Rectangle();
		updateBounds();
	}
	
	public void updateBounds() {
		bounds.x = value(MapObjectStat.MAP_X_POS).intValue();
		bounds.y = value(MapObjectStat.MAP_Y_POS).intValue();
		bounds.width = value(MapObjectStat.MAP_WIDTH).intValue();
		bounds.height = value(MapObjectStat.MAP_HEIGHT).intValue();
	}

	@Override
	public MapObject clone() {
		IStats<StrategyStat> nstats = getStats().clone();
		return new MapObject(nstats, this.getDisplayContext(), this.getDisplayFactory());
	}

	@Override
	public int getDisplayDepth() {
		return value(MapObjectStat.Z_DEPTH).intValue();
	}

	@Override
	public boolean isOnScreen(Rectangle screen) {
		return screen.intersects(bounds) || screen.contains(bounds);
	}

	public Shape getBounds() {
		return bounds;
	}

	public IEntity<StrategyStat> getAsEntity() {
		return this;
	}

	public IControlMapping<StrategyCondition, StrategyStat> getMapping() {
		return mapping;
	}

	public void setMapping(IControlMapping<StrategyCondition, StrategyStat> mapping) {
		this.mapping = mapping;
	}

}
