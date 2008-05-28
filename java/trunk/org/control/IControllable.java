package org.control;

import org.interaction.IEntity;

public interface IControllable<C,K> {

	public IControlMapping<C,K> getMapping();
	public void setMapping(IControlMapping<C,K> mapping);
	public IEntity<K> getAsEntity();
	
}
