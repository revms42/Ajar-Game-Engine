package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step10Accell extends AbstractEffect<Step10Attributes> {

	public Step10Accell(Step10ActionType a, State<Step10Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step10Attributes> state) {
		Step10ActionType a = (Step10ActionType)getAction();
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
