package org.ajar.age.logic.loader.test;

import org.ajar.age.logic.AbstractCondition;
import org.ajar.age.logic.Effect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;

public class TestCondition3 extends AbstractCondition<HashAttributes> {

	public TestCondition3(Effect<HashAttributes> trueEffect, Effect<HashAttributes> falseEffect) {
		super(trueEffect, falseEffect);
	}

	@Override
	public boolean meetsCondition(Entity<HashAttributes> entity) {
		return false;
	}
	
	public Effect<HashAttributes> trueEffect(){
		return trueEffect;
	}
	
	public Effect<HashAttributes> falseEffect(){
		return falseEffect;
	}

}
