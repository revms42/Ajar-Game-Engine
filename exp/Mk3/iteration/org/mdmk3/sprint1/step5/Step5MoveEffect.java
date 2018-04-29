package org.mdmk3.sprint1.step5;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step5MoveEffect extends AbstractEffect<Step5Attributes> {

	public Step5MoveEffect(Action a, State<Step5Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step5Attributes> state) {
		double x = state.getAttributes().getXPos();
		double y = state.getAttributes().getYPos();
		
		x = x + state.getAttributes().getXVel();
		y = y + state.getAttributes().getYVel();
		
		state.getAttributes().setPosition(x, y);
	}

}
