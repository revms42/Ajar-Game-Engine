package space.model.ships;

import org.display.IDisplayable;
import space.model.Resource;

public interface IComponent<I> extends IDisplayable<I,String> {

	public int getCost(Resource resource);
}
