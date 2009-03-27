package space.model.ships;

import java.awt.Point;
import java.util.Vector;

import space.model.component.IComponent;
import space.model.component.IComponentType;

public interface ICombatObject<I> {
	
	public Vector<ICombatObject<I>> getChildObjects();
	public void addChildObject(ICombatObject<I> child);
	public void removeChildObject(ICombatObject<I> child);
	
	public ICombatObject<I> getParent();
	public void setParent(ICombatObject<I> parent);
	
	public IComponent<I> getComponentAt(Point p);
	public Point locateComponent(IComponent<I> comp);
	public Vector<IComponent<I>> getAllOfType(IComponentType type);
	
	public Vector<Vector<IComponent<I>>> getMap();
}
