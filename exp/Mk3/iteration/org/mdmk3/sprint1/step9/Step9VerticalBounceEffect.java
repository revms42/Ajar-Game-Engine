package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step9VerticalBounceEffect extends AbstractEffect<Step9Attributes> {

	public Step9VerticalBounceEffect(Action a, State<Step9Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step9Attributes> state) {
		int dy = state.getAttributes().getYVel();
		if(dy > 0){
			dy = dy - 1;
		}else if(dy < 0){
			dy = dy + 1;
		}
		state.getAttributes().setYVel(-dy);
	}

}
