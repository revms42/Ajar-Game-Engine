package org.mdmk3.sprint1.step8;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step8NullEffect extends AbstractEffect<Step8Attributes> {

	public Step8NullEffect(Action a, State<Step8Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step8Attributes> state) {
		//DO NOTHING!
	}

}
