package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step9Accell extends AbstractEffect<Step9Attributes> {

	public Step9Accell(Step9ActionType a, State<Step9Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step9Attributes> state) {
		Step9ActionType a = (Step9ActionType)getAction();
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
