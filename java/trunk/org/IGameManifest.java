package org;

import java.util.Collection;

import org.control.IController;
import org.display.IDisplayable;
import org.display.IEnvironment;
import org.interaction.IEntity;

public interface IGameManifest {

	public Collection<IEnvironment<?,?>> getEnvironments(Object caller);
	public Collection<IDisplayable<?,?>> getDisplayables(Object caller);
	
	public Collection<IEntity<?>> getEntities(Object caller);
	
	public Collection<IController<?,?>> getControllers(Object caller);
	
	public int getMaxDepth();
}
