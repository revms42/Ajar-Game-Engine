package org.mdmk3.sprint1.step5;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step5VerticalBounceEffect extends AbstractEffect<Step5Attributes> {

	public Step5VerticalBounceEffect(Action a, State<Step5Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step5Attributes> state) {
		int dy = state.getAttributes().getYVel();
		if(dy > 0){
			dy = dy - 1;
		}else if(dy < 0){
			dy = dy + 1;
		}
		state.getAttributes().setYVel(-dy);
	}

}
