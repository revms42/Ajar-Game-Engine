package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step9PowerUpEffect extends AbstractEffect<Step9Attributes> {

	public Step9PowerUpEffect(Action a, State<Step9Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step9Attributes> state) {
		state.getAttributes().setBlue(true);
	}

}
