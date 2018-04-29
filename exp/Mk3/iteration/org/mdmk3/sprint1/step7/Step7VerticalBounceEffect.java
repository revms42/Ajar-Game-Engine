package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step7VerticalBounceEffect extends AbstractEffect<Step7Attributes> {

	public Step7VerticalBounceEffect(Action a, State<Step7Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step7Attributes> state) {
		int dy = state.getAttributes().getYVel();
		if(dy > 0){
			dy = dy - 1;
		}else if(dy < 0){
			dy = dy + 1;
		}
		state.getAttributes().setYVel(-dy);
	}

}
