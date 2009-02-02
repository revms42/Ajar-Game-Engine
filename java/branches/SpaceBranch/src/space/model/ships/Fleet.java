package space.model.ships;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Vector;

import org.AbstractCharacter;
import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.model.LinearStat;
import org.model.Stats;

import space.model.IMappable;
import space.model.IMovable;
import space.model.TaskPoint;

public class Fleet<I> extends AbstractCharacter<I,String> implements IMovable {

	public final String name;
	private final Vector<TaskPoint> waypoints;
	private final Vector<DefaultShip<I>> ships;
	
	public Fleet(String name, Point position, IDisplayContext<I,String> context, IDisplayFactory<I,String> factory) {
		super(new Stats<String>(
					new String[]{IMappable.STAT_MAP_X,IMappable.STAT_MAP_Y},
					new LinearStat[]{
							new LinearStat(position.x,Integer.MAX_VALUE,Integer.MIN_VALUE,0),
							new LinearStat(position.y,Integer.MAX_VALUE,Integer.MIN_VALUE,0)
						}
				),
				context,
				factory
		);
		this.name = name;
		waypoints = new Vector<TaskPoint>();
		ships = new Vector<DefaultShip<I>>();
	}
	
	public Vector<DefaultShip<I>> getShips(){
		return ships;
	}
	
	public void addShip(DefaultShip<I>... ships){
		for(DefaultShip<I> ship : ships){
			if(!this.ships.contains(ship)){
				this.ships.add(ship);
			}
		}
	}
	
	public void removeShip(DefaultShip<I>... ships){
		for(DefaultShip<I> ship : ships){
			if(this.ships.contains(ship)){
				this.ships.remove(ship);
			}
		}
	}
	
	public Fleet<I> splitFleet(String name, DefaultShip<I>... ships){
		Fleet<I> clone = this.clone();
		
		this.removeShip(ships);
		clone.addShip(ships);
		
		return clone;
	}
	
	@SuppressWarnings("unchecked")
	public void mergeInFleet(Fleet<I> fleet){
		DefaultShip<I>[] ships = (DefaultShip<I>[])fleet.getShips().toArray();
		this.addShip(ships);
		fleet.removeShip(ships);
		fleet = null;
	}

	@Override
	public Fleet<I> clone() {
		return new Fleet<I>(name + " 2", this.getPosition(),this.getDisplayContext(),this.getDisplayFactory());
	}

	@Override
	public int getDisplayDepth() {
		return 0;
	}

	@Override
	public boolean isOnScreen(Rectangle screen) {
		return screen.contains(getPosition());
	}

	public Point getPosition() {
		return new Point(value(IMappable.STAT_MAP_X).intValue(),value(IMappable.STAT_MAP_Y).intValue());
	}

	public Shape getBounds() {
		// TODO No use?
		return null;
	}

	public void addDestination(TaskPoint dest) {
		waypoints.add(dest);
	}

	public Vector<TaskPoint> getDestination() {
		return waypoints;
	}

	public double getHeading() {
		Point p = getPosition();
		Point t = waypoints.get(0).getTarget().getPosition();
		return Math.atan2((t.getX() - p.getX()), (t.getY() - p.getY()));
	}

	public void removeDestination(TaskPoint dest) {
		waypoints.remove(dest);
	}

	public void setDestination(TaskPoint... dest) {
		for(TaskPoint t : dest){
			waypoints.add(t);
		}
	}

}
