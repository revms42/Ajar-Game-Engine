package org.mdmk3.sprint1.step6;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step6HorizontalBounceEffect extends AbstractEffect<Step6Attributes> {

	public Step6HorizontalBounceEffect(Action a, State<Step6Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step6Attributes> state) {
		int dx = state.getAttributes().getXVel();
		if(dx > 0){
			dx = dx - 1;
		}else if(dx < 0){
			dx = dx + 1;
		}
		state.getAttributes().setXVel(-dx);
	}

}
