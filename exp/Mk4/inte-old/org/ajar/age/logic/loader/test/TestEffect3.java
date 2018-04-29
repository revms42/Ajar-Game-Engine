package org.ajar.age.logic.loader.test;

import org.ajar.age.logic.AbstractEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;

public class TestEffect3 extends AbstractEffect<HashAttributes> {
	
	private static Entity<HashAttributes> target;
	public Number[] args;
	public TestEnum en;
	
	public TestEffect3(State<HashAttributes> result) {
		super(result);
	}
	
	public TestEffect3(State<HashAttributes> result, int i, long j, float k, double l, TestEnum e) {
		super(result);
		args = new Number[4];
		args[0] = i;
		args[1] = j;
		args[2] = k;
		args[3] = l;
		en = e;
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
