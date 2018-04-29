package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step7NullEffect extends AbstractEffect<Step7Attributes> {

	public Step7NullEffect(Action a, State<Step7Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step7Attributes> state) {
		//DO NOTHING!
	}

}
