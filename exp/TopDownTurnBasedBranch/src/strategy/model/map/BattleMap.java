package strategy.model.map;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import org.AbstractLevel;
import org.display.IEnvironmentContext;
import org.display.ITileFactory;
import org.model.IStats;

import strategy.model.StrategyStat;

public class BattleMap extends AbstractLevel<Integer, StrategyStat> {

	private final Rectangle bounds;
	private int currentBounds;
	
	public BattleMap(Vector<BufferedImage> maps, IStats<StrategyStat> stats, IEnvironmentContext<Integer> context, ITileFactory<Integer> factory) {
		super(maps, stats, context, factory);
		int max_x = 0;
		int max_y = 0;
		
		for(BufferedImage img : maps){
			max_x = img.getWidth() > max_x ? img.getWidth() : max_x;
			max_y = img.getHeight() > max_y ? img.getHeight() : max_y;
		}
		
		for(MapStat stat : MapStat.STATS){
			if(stats.getStat(stat) == null){
				stats.setStat(stat, MapStat.getStat(stat));
			}
		}
		
		stats.value(MapStat.MAP_WIDTH, max_x);
		stats.value(MapStat.MAP_HEIGHT, max_y);
		
		bounds = new Rectangle(0,0,max_x*MapStat.TILE_SIZE,max_y*MapStat.TILE_SIZE);
		currentBounds = 0;
	}
	
	protected void setBounds(int map){
		BufferedImage img = getMaps().get(map);
		bounds.width = img.getWidth()* MapStat.TILE_SIZE;
		bounds.height = img.getHeight()* MapStat.TILE_SIZE;
		currentBounds = map;
	}

	@Override
	public AbstractLevel<Integer, StrategyStat> clone() {
		IStats<StrategyStat> nstats = getStats().clone();
		return new BattleMap(this.getMaps(), nstats, this.getEnvironmentContext(), this.getTileFactory());
	}

	@Override
	public boolean isOnScreen(Rectangle arg0, int arg1) {
		if(arg1 != currentBounds){
			setBounds(arg1);
		}
		return arg0.intersects(bounds);
	}

}
