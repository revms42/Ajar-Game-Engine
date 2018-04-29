package org.mdmk3.sprint1.step8;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step8VerticalBounceEffect extends AbstractEffect<Step8Attributes> {

	public Step8VerticalBounceEffect(Action a, State<Step8Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step8Attributes> state) {
		int dy = state.getAttributes().getYVel();
		if(dy > 0){
			dy = dy - 1;
		}else if(dy < 0){
			dy = dy + 1;
		}
		state.getAttributes().setYVel(-dy);
	}

}
