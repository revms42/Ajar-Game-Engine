package org.mdmk3.sprint1.step5;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step5Accell extends AbstractEffect<Step5Attributes> {

	public Step5Accell(Step5ActionType a, State<Step5Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step5Attributes> state) {
		Step5ActionType a = (Step5ActionType)getAction();
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
