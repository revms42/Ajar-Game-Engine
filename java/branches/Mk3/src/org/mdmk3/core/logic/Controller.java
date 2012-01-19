package org.mdmk3.core.logic;

import org.mdmk3.core.Attributes;

public interface Controller<A extends Attributes> {

	public void pollForInput(Entity<A> entity);
}
