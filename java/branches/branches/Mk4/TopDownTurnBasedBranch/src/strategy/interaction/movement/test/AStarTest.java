package strategy.interaction.movement.test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.IGameManifest;
import org.junit.Before;
import org.junit.Test;

import strategy.Strategy;
import strategy.interaction.movement.AStar;
import strategy.model.map.object.MapObject;
import strategy.model.map.object.MapObjectStat;

public class AStarTest {

	public IGameManifest manifest;
	public AStar pather;
	
	@Before
	public void setUp() throws Exception {
		manifest = new Strategy();
		((Strategy)manifest).start();
		pather = new AStar(manifest);
	}

	@Test
	public void testFindPath() {
		Point one = new Point(0,0);
		Point two = new Point(9,9);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean result = pather.findPath(one, two);
		for(Point p : pather.path){
			MapObject mo = (MapObject)manifest.getEntities(this).toArray()[0];
			MapObject clone = (MapObject)mo.clone();
			clone.value(MapObjectStat.MAP_X_POS, p.x * MapObjectStat.TILE_SIZE);
			clone.value(MapObjectStat.MAP_Y_POS, p.y * MapObjectStat.TILE_SIZE);
			clone.value(MapObjectStat.MAP_X_DEST, p.x * MapObjectStat.TILE_SIZE);
			clone.value(MapObjectStat.MAP_Y_DEST, p.y * MapObjectStat.TILE_SIZE);
			manifest.getEntities(this).add(clone);
			System.out.println(p);
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(result);
	}

}
