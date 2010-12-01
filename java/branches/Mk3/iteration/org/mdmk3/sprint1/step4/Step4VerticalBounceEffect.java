package org.mdmk3.sprint1.step4;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step4VerticalBounceEffect extends AbstractEffect<Step4Attributes> {

	public Step4VerticalBounceEffect(Action a, State<Step4Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step4Attributes> state) {
		int dy = state.getAttributes().getYVel();
		if(dy > 0){
			dy = dy - 1;
		}else if(dy < 0){
			dy = dy + 1;
		}
		state.getAttributes().setYVel(-dy);
	}

}
