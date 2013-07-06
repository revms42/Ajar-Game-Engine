package org.mdmk3.sprint1.step4;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step4Accell extends AbstractEffect<Step4Attributes> {

	public Step4Accell(Step4ActionType a, State<Step4Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step4Attributes> state) {
		Step4ActionType a = (Step4ActionType)getAction();
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
