package org.mdmk3.sprint1.step3;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step3HorizontalBounceEffect extends AbstractEffect<Step3Attributes> {

	public Step3HorizontalBounceEffect(Action a, State<Step3Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step3Attributes> state) {
		state.getAttributes().setXVel(-state.getAttributes().getXVel());
	}

}
