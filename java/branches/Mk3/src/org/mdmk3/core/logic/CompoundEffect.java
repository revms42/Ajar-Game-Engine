package org.mdmk3.core.logic;

import org.mdmk3.core.Attributes;

public class CompoundEffect<A extends Attributes> extends AbstractEffect<A> {

	private final Effect<A>[] effects;
	
	public CompoundEffect(Action a, State<A> result, Effect<A>... effects) {
		super(a, result);
		this.effects = effects;
	}

	@Override
	protected void doAction(Entity<A> state) {
		for(Effect<A> e : effects){
			e.perform(state);
		}
	}

}
