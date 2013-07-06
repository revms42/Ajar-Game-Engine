package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step7HorizontalBounceEffect extends AbstractEffect<Step7Attributes> {

	public Step7HorizontalBounceEffect(Action a, State<Step7Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step7Attributes> state) {
		int dx = state.getAttributes().getXVel();
		if(dx > 0){
			dx = dx - 1;
		}else if(dx < 0){
			dx = dx + 1;
		}
		state.getAttributes().setXVel(-dx);
	}

}
