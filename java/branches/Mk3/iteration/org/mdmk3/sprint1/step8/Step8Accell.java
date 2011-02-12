package org.mdmk3.sprint1.step8;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step8Accell extends AbstractEffect<Step8Attributes> {

	public Step8Accell(Step8ActionType a, State<Step8Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step8Attributes> state) {
		Step8ActionType a = (Step8ActionType)getAction();
		switch(a){
		case ACCELL_XPOS:
			state.getAttributes().setXVel(state.getAttributes().getXVel() + 1);
			break;
		case ACCELL_XNEG:
			state.getAttributes().setXVel(state.getAttributes().getXVel() - 1);
			break;
		case ACCELL_YPOS:
			state.getAttributes().setYVel(state.getAttributes().getYVel() + 1);
			break;
		case ACCELL_YNEG:
			state.getAttributes().setYVel(state.getAttributes().getYVel() - 1);
			break;
		default:
			break;
		}
	}

}
