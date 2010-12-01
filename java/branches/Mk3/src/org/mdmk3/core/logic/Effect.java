package org.mdmk3.core.logic;

import org.mdmk3.core.Attributes;

public interface Effect<A extends Attributes> {

	public State<A> perform(Entity<A> state);
	public Action getAction();
	public State<A> getResultState();
	
}
