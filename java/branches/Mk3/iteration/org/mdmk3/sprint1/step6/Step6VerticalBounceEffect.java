package org.mdmk3.sprint1.step6;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step6VerticalBounceEffect extends AbstractEffect<Step6Attributes> {

	public Step6VerticalBounceEffect(Action a, State<Step6Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step6Attributes> state) {
		int dy = state.getAttributes().getYVel();
		if(dy > 0){
			dy = dy - 1;
		}else if(dy < 0){
			dy = dy + 1;
		}
		state.getAttributes().setYVel(-dy);
	}

}
