package org.mdmk3.sprint1.step5;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step5NullEffect extends AbstractEffect<Step5Attributes> {

	public Step5NullEffect(Action a, State<Step5Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step5Attributes> state) {
		//DO NOTHING!
	}

}
