package org.ajar.age.logic.loader.test;

import org.ajar.age.logic.AbstractEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;

public class TestEffect1 extends AbstractEffect<HashAttributes> {

	private static Entity<HashAttributes> target;
	
	public TestEffect1(State<HashAttributes> result) {
		super(result);
	}

	@Override
	protected void doAction(Entity<HashAttributes> entity) {
		target = entity;
	}
	
	public static Entity<HashAttributes> getTarget(){
		return target;
	}
	
	public State<HashAttributes> getResult() {
		return this.result;
	}

}
