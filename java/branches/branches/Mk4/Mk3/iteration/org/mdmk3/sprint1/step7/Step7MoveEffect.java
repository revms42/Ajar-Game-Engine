package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step7MoveEffect extends AbstractEffect<Step7Attributes> {

	public Step7MoveEffect(Action a, State<Step7Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step7Attributes> state) {
		double x = state.getAttributes().getXPos();
		double y = state.getAttributes().getYPos();
		
		x = x + state.getAttributes().getXVel();
		y = y + state.getAttributes().getYVel();
		
		state.getAttributes().setPosition(x, y);
	}

}
