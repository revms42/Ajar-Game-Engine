package org.mdmk3.sprint1.step5;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step5HorizontalBounceEffect extends AbstractEffect<Step5Attributes> {

	public Step5HorizontalBounceEffect(Action a, State<Step5Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step5Attributes> state) {
		int dx = state.getAttributes().getXVel();
		if(dx > 0){
			dx = dx - 1;
		}else if(dx < 0){
			dx = dx + 1;
		}
		state.getAttributes().setXVel(-dx);
	}

}
