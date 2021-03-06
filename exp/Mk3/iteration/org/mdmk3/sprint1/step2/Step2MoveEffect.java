package org.mdmk3.sprint1.step2;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step2MoveEffect extends AbstractEffect<Step2Attributes> {

	public Step2MoveEffect(Action a, State<Step2Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step2Attributes> state) {
		double x = state.getAttributes().getXPos();
		double y = state.getAttributes().getYPos();
		
		x = x + state.getAttributes().getXVel();
		y = y + state.getAttributes().getYVel();
		
		state.getAttributes().setPosition(x, y);
	}

}
