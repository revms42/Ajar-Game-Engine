package org.ajar.age.logic.loader.test;

import org.ajar.age.logic.AbstractChainableEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;

public class TestChainable1 extends AbstractChainableEffect<HashAttributes> {

	public TestChainable1(State<HashAttributes> result) {
		super(result);
	}

	@Override
	protected void doAction(Entity<HashAttributes> entity, HashAttributes a) {}

	public State<HashAttributes> getResult() {
		return this.result;
	}
}
