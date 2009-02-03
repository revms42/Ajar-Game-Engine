package org;

import java.util.Collection;

import org.control.IController;
import org.display.IDisplayable;
import org.display.IEnvironment;
import org.interaction.IEntity;

public interface IGameManifest {

	public <V extends IEnvironment<?,?>> Collection<V> getEnvironments(Object caller);
	public <D extends IDisplayable<?,?>> Collection<D> getDisplayables(Object caller);
	
	public <E extends IEntity<?>> Collection<E> getEntities(Object caller);
	
	public <C extends IController<?,?>> Collection<C> getControllers(Object caller);
	
	public int getMaxDepth();
}
