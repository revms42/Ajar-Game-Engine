package org.control;

import java.awt.AWTEvent;

import org.interaction.IEntity;

public interface IControlMapping<C,K> {

	public C getMapingFor(IEntity<K> entity, AWTEvent e);
}
