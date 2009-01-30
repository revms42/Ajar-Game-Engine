package space.model.ships;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.Vector;

import org.AbstractCharacter;
import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.model.IStats;

import space.display.DisplayStats;
import space.model.component.IComponent;
import space.model.component.IComponentType;

public abstract class AbstractSpaceObject<I> extends AbstractCharacter<I,String> implements ISpaceObject<I>, DisplayStats {
	protected final static int SCALE = 6;
	
	private final HashMap<IComponentType,Vector<IComponent<I>>> lists;
	protected final Vector<Vector<IComponent<I>>> map;
	private Shape outline;
	
	private ISpaceObject<I> parent;
	private Vector<ISpaceObject<I>> children;
	
	public AbstractSpaceObject(Vector<Vector<IComponent<I>>> map, IStats<String> stats, IDisplayContext<I,String> context, IDisplayFactory<I,String> factory) {
		super(stats, context, factory);
		
		lists = new HashMap<IComponentType,Vector<IComponent<I>>>();
		
		this.map = map; 
		
		collapseStats();
	}
	
	public abstract void collapseStats();

	@Override
	public int getDisplayDepth() {
		return 0;
	}

	public Vector<ISpaceObject<I>> getChildObjects(){
		return children;
	}
	
	public void addChildObject(ISpaceObject<I> child){
		if(children == null) children = new Vector<ISpaceObject<I>>();
		
		children.add(child);
	}
	
	public void removeChildObject(ISpaceObject<I> child){
		if(children != null){
			children.remove(child);
		}
	}
	
	public ISpaceObject<I> getParent(){
		return parent;
	}
	
	public void setParent(ISpaceObject<I> parent){
		this.parent = parent;
	}
	
	public Vector<IComponent<I>> getAllOfType(IComponentType type) {
		if(!lists.containsKey(type)){
			final Vector<IComponent<I>> v = new Vector<IComponent<I>>();
			
			for(Vector<IComponent<I>> c : map){
				for(IComponent<I> comp : c){
					if(comp.getPrimaryType() == type){
						v.add(comp);
					}else{
						for(IComponentType second : comp.getSecondaryTypes()){
							if(type == second){
								v.add(comp);
							}
						}
					}
				}
			}
			
			lists.put(type, v);
		}
		
		return lists.get(type);
	}

	public IComponent<I> getComponentAt(Point p) {
		return map.get(p.x).get(p.y);
	}

	public Vector<Vector<IComponent<I>>> getMap() {
		return map;
	}

	public Point locateComponent(IComponent<I> comp) {
		for(Vector<IComponent<I>> x : map){
			for(IComponent<I> y : x){
				if(y == comp){
					return new Point(map.indexOf(x),x.indexOf(y));
				}
			}
		}
		return null;
	}

	public Shape getBounds() {
		if(outline == null){
			Area a = new Area();
			
			for(Vector<IComponent<I>> x : map){
				for(IComponent<I> y : x){
					if(y != null){
						Rectangle r = new Rectangle(map.indexOf(x)*SCALE,x.indexOf(y)*SCALE,SCALE,SCALE);
						Area b = new Area(r);
						
						a.add(b);
					}
				}
			}
		}
		
		return outline;
	}

}
