package org.mdmk3.sprint1.step4;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step4HorizontalBounceEffect extends AbstractEffect<Step4Attributes> {

	public Step4HorizontalBounceEffect(Action a, State<Step4Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step4Attributes> state) {
		int dx = state.getAttributes().getXVel();
		if(dx > 0){
			dx = dx - 1;
		}else if(dx < 0){
			dx = dx + 1;
		}
		state.getAttributes().setXVel(-dx);
	}

}
