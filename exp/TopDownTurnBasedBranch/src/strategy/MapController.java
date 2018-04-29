package strategy;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collection;
import java.util.Vector;

import org.IGameManifest;
import org.control.IControllable;
import org.control.IController;
import org.interaction.ConditionPalette;
import org.interaction.IEntity;
import org.model.IStats;
import org.model.Stats;

import strategy.interaction.StrategyCondition;
import strategy.model.StrategyStat;
import strategy.model.map.object.MapObjectStat;

public class MapController extends MouseAdapter implements IController<StrategyCondition,StrategyStat> {
	private ConditionPalette<StrategyCondition, ?, StrategyStat> palette;
	private final IGameManifest manifest;
	private final Vector<AWTEvent> events;
	private final Vector<IControllable<StrategyCondition, StrategyStat>> targets;
	
	public MapController(IGameManifest manifest){
		super();
		this.manifest = manifest;
		events = new Vector<AWTEvent>();
		targets = new Vector<IControllable<StrategyCondition, StrategyStat>>();
	}
	
	public void mouseClicked(MouseEvent e){
		events.add(e);
		//System.out.println(e.toString());
		super.mouseClicked(e);
	}
	
	public void mouseDragged(MouseEvent e){
		events.add(e);
		super.mouseDragged(e);
	}
	
	public void mouseEntered(MouseEvent e){
		//events.add(e);
		super.mouseEntered(e);
	}
	
	public void mouseExited(MouseEvent e){
		//events.add(e);
		super.mouseExited(e);
	}
	
	public void mouseMoved(MouseEvent e){
		//events.add(e);
		super.mouseMoved(e);
	}
	
	public void mousePressed(MouseEvent e){
		events.add(e);
		super.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e){
		events.add(e);
		super.mouseReleased(e);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e){
		events.add(e);
		super.mouseWheelMoved(e);
	} 

	public void evaluateAllEvents() {
		while(events.size() > 0){
			evaluateNextEvent();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void evaluateNextEvent() {
		AWTEvent e = events.remove(0);
		
		if(palette != null){
			EventEntity object = null;
			if(e instanceof MouseEvent){
				object = new EventEntity();
				object.setStat(MapObjectStat.MAP_X_POS, MapObjectStat.getStat(MapObjectStat.MAP_X_POS));
				object.setStat(MapObjectStat.MAP_Y_POS, MapObjectStat.getStat(MapObjectStat.MAP_Y_POS));
				
				Point p = ((MouseEvent) e).getPoint();
				object.value(MapObjectStat.MAP_X_POS,p.x);
				object.value(MapObjectStat.MAP_Y_POS,p.y);
			}
			for(IControllable<StrategyCondition, StrategyStat> target : targets){
				IEntity<StrategyStat> entity = target.getAsEntity();
				StrategyCondition mapping = target.getMapping().getMapingFor(entity, e);
				palette.performAction(mapping, entity, object);
			}
		}
	}

	public void flushEvents() {
		events.removeAllElements();
	}

	public Vector<AWTEvent> getEvents() {
		return events;
	}

	public ConditionPalette<StrategyCondition, ?, StrategyStat> getPalette() {
		return palette;
	}

	public Collection<IControllable<StrategyCondition, StrategyStat>> getTargets() {
		return targets;
	}

	public void mapTarget(IControllable<StrategyCondition, StrategyStat> target) {
		if(!targets.contains(target)){
			targets.add(target);
		}
	}

	public void setPalette(ConditionPalette<StrategyCondition, ?, StrategyStat> palette) {
		this.palette = palette;
	}

	public boolean unMapTarget(IControllable<StrategyCondition, StrategyStat> target) {
		if(targets.contains(target)){
			targets.remove(target);
			return true;
		}else{
			return false;
		}
	}

	private class EventEntity extends Stats<StrategyStat> implements IEntity<StrategyStat> {
		private static final long serialVersionUID = -1232048832873910110L;

		public IStats<StrategyStat> getStats() {
			return this;
		}

		public void setStats(IStats<StrategyStat> stats) {
			//TODO: ??? Don't know what to do here.
		}
	}
}
