package space.model.component;

import java.util.Vector;

import org.display.IDisplayable;
import space.model.Resource;

public interface IComponent<I> extends IDisplayable<I,String> {
	public String getName();
	public int getCost(Resource resource);
	public String getDescription();
	public IComponentType getPrimaryType();
	public Vector<IComponentType> getSecondaryTypes();
}
