package org.ajar.age.logic.loader.test;

import org.ajar.age.logic.AbstractEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;

public class TestEffect2 extends AbstractEffect<HashAttributes> {

	private static Entity<HashAttributes> target;
	
	public TestEffect2(State<HashAttributes> result) {
		super(result);
	}

	@Override
	protected void doAction(Entity<HashAttributes> entity, HashAttributes a) {
		target = entity;
	}
	
	public static Entity<HashAttributes> getTarget(){
		return target;
	}

	public State<HashAttributes> getResult() {
		return this.result;
	}
}
