package org.mdmk3.sprint1.step2;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step2HorizontalBounceEffect extends AbstractEffect<Step2Attributes> {

	public Step2HorizontalBounceEffect(Action a, State<Step2Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step2Attributes> state) {
		state.getAttributes().setXVel(-state.getAttributes().getXVel());
	}

}
