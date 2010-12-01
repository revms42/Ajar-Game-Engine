package org.mdmk3.core.logic;

import org.mdmk3.core.Attributes;

public interface State<A extends Attributes> {

	public State<A> perform(Entity<A> subject, Action e);
}
