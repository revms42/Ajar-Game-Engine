package space.model.ships;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Vector;

import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.model.CircularStat;
import org.model.IStats;
import org.model.LinearStat;

import space.model.component.IComponent;

public class DefaultShip<I> extends AbstractSpaceObject<I> {
	public final String name;

	public DefaultShip(String name, Vector<Vector<IComponent<I>>> map, IStats<String> stats, IDisplayContext<I,String> context, IDisplayFactory<I,String> factory) {
		super(map, stats, context, factory);
		
		this.name = name;
	}

	@Override
	public void collapseStats() {
		for(ShipStats stat : ShipStats.STATS){
			if(this.getStat(stat.name) == null){
				if(stat.name != ShipStats.STAT_ROTATION.name){
					this.setStat(stat.name, new LinearStat(0,Integer.MAX_VALUE,0,0));
				}else{
					this.setStat(stat.name, new CircularStat(0.0d,Math.PI,-Math.PI,0.0d));
				}
			}
		}
		
		for(Vector<IComponent<I>> v : map){
			for(IComponent<I> comp : v){
				if(comp != null){
					for(ShipStats stat : ShipStats.COLLAPSABLE){
						if(comp.getStat(stat.name) != null){
							this.plusEq(stat.name, comp.value(stat.name));
						}
					}
					for(ShipStats stat : ShipStats.TOPABLE){
						if(comp.getStat(stat.name) != null){
							if(this.value(stat.name).compareTo(comp.value(stat.name)) < 0){
								this.value(stat.name,comp.value(stat.name).intValue());
							}
						}
					}
					for(ShipStats stat : ShipStats.CARGO){
						if(comp.getStat(stat.name) != null){
							this.max(stat.name).plusEq(comp.max(stat.name));
						}
					}
					for(ShipStats stat : ShipStats.PRICE){
						if(comp.getStat(stat.name) != null){
							this.value(stat.name).plusEq(comp.value(stat.name));
							this.nominal(stat.name).plusEq(comp.value(stat.name));
						}
					}
				}
			}
		}
	}

	@Override
	public DefaultShip<I> clone() {
		Vector<Vector<IComponent<I>>> map = new Vector<Vector<IComponent<I>>>();
		for(Vector<IComponent<I>> v : this.map){
			Vector<IComponent<I>> column = new Vector<IComponent<I>>();
			for(IComponent<I> comp : v){
				column.add((IComponent<I>)comp.clone());
			}
			map.add(column);
		}
		
		return new DefaultShip<I>(name, map,this.getStats().clone(),this.getDisplayContext(),this.getDisplayFactory());
	}

	@Override
	public boolean isOnScreen(Rectangle screen) {
		Area a = new Area(this.getBounds());
		AffineTransform at = new AffineTransform();
		
		at.translate(this.value(ShipStats.STAT_X_POS.name).doubleValue(), this.value(ShipStats.STAT_Y_POS.name).doubleValue());
		at.rotate(this.value(ShipStats.STAT_ROTATION.name).doubleValue());
		
		a.transform(at);
		
		return a.intersects(screen);
	}

}
