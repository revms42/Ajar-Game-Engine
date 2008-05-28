package org.interaction;

public interface IAction<K> extends IAccountant{

	public void performAction(IEntity<K> subject, IEntity<K>... objects);
	
}
