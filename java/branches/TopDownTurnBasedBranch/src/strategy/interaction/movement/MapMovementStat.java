package strategy.interaction.movement;

import java.awt.Point;

import org.model.IStat;
import org.model.Stats;

import strategy.model.StrategyStat;
import strategy.model.map.object.MapObjectStat;

public class MapMovementStat implements StrategyStat {

	public static enum Type {
		DEST_X("Dest X: Step "),
		DEST_Y("Dest Y: Step ");
		
		protected final String type;
		
		private Type(String type){
			this.type = type;
		}
	}
	
	public final Type type;
	public final int degree;
	public MapMovementStat next;
	public MapMovementStat previous;
	
	private MapMovementStat(Type type, int degree){
		this.type = type;
		this.degree = degree;
	}
	
	//TODO: This should be an action.
	public static void addStep(Stats<StrategyStat> stats, Point p) {
		MapMovementStat member_x = null;
		MapMovementStat member_y = null;
		for(StrategyStat stat : stats.keySet()){
			if(stat instanceof MapMovementStat){
				MapMovementStat member = (MapMovementStat) stat;
				
				switch(member.type){
				case DEST_X:
					member_x = member;
					break;
				case DEST_Y:
					member_y = member;
					break;
				default:
					//FAIL!
					break;
				}
				
				if(member_x != null && member_y != null) break; 
			}
		}
		
		if(member_x != null && member_y != null){
			for(; member_x.next != null; member_x = member_x.next){
				assert member_x.next.degree > member_x.degree && member_x.next != member_x;
			}
			for(; member_y.next != null; member_y = member_y.next){
				assert member_y.next.degree > member_y.degree && member_y.next != member_y;
			}
			
			assert member_x.degree == member_y.degree;
			
			MapMovementStat x = new MapMovementStat(Type.DEST_X,member_x.degree + 1);
			MapMovementStat y = new MapMovementStat(Type.DEST_Y,member_x.degree + 1);
			
			IStat stat_x = MapObjectStat.getStat(MapObjectStat.MAP_X_POS);
			IStat stat_y = MapObjectStat.getStat(MapObjectStat.MAP_Y_POS);
			
			stat_x.value(p.x);
			stat_y.value(p.y);
			
			stats.setStat(x, stat_x);
			stats.setStat(y, stat_y);
			
			x.previous = member_x;
			y.previous = member_y;
			
			member_x.next = x;
			member_y.next = y;
			
			stats.max(MapObjectStat.MAP_PATH, x.degree);
			
			return;
		}else{
			MapMovementStat x = new MapMovementStat(Type.DEST_X,0);
			MapMovementStat y = new MapMovementStat(Type.DEST_Y,0);
			
			IStat stat_x = MapObjectStat.getStat(MapObjectStat.MAP_X_POS);
			IStat stat_y = MapObjectStat.getStat(MapObjectStat.MAP_Y_POS);
			
			stat_x.value(p.x);
			stat_y.value(p.y);
			
			stats.setStat(x, stat_x);
			stats.setStat(y, stat_y);
			
			return;
		}
	}
	
	//TODO: This should be an action.
	public static void setNextStep(Stats<StrategyStat> stats){
		int degree = stats.plusEq(MapObjectStat.MAP_PATH,1).intValue();
		
		MapMovementStat member_x = null;
		MapMovementStat member_y = null;
		for(StrategyStat stat : stats.keySet()){
			if(stat instanceof MapMovementStat){
				MapMovementStat member = (MapMovementStat) stat;
				
				switch(member.type){
				case DEST_X:
					if(member.degree > stats.max(MapObjectStat.MAP_PATH).intValue()) member_x = member;
					break;
				case DEST_Y:
					if(member.degree > stats.max(MapObjectStat.MAP_PATH).intValue()) member_y = member;
					break;
				default:
					//FAIL!
					break;
				}
				
				if(member_x != null && member_y != null) break; 
			}
		}
		
		if(member_x != null && member_y != null){
			for(; member_x.degree == degree; member_x = member_x.degree > degree ? member_x.previous : member_x.next){
				assert member_x.previous.degree < member_x.degree && member_x.previous != member_x;
			}
			for(; member_y.degree == degree; member_y = member_y.degree > degree ? member_y.previous : member_y.next){
				assert member_y.previous.degree < member_y.degree && member_y.previous != member_y;
			}
			
			stats.value(MapObjectStat.MAP_X_DEST,stats.value(member_x));
			stats.value(MapObjectStat.MAP_Y_DEST,stats.value(member_y));
		}
	}
	
	//TODO: This should be an action.
	public static void purgeMapPath(Stats<StrategyStat> stats){
		for(StrategyStat stat : stats.keySet()){
			if(stat instanceof MapMovementStat){
				stats.remove(stat);
			}
		}
	}
}
