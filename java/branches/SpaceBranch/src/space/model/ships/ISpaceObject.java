package space.model.ships;

import java.awt.Point;
import java.util.Vector;

import space.model.component.IComponent;
import space.model.component.IComponentType;

public interface ISpaceObject<I> {
	
	public Vector<ISpaceObject<I>> getChildObjects();
	public void addChildObject(ISpaceObject<I> child);
	public void removeChildObject(ISpaceObject<I> child);
	
	public ISpaceObject<I> getParent();
	public void setParent(ISpaceObject<I> parent);
	
	public IComponent<I> getComponentAt(Point p);
	public Point locateComponent(IComponent<I> comp);
	public Vector<IComponent<I>> getAllOfType(IComponentType type);
	
	public Vector<Vector<IComponent<I>>> getMap();
}
