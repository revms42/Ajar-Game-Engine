package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step10HorizontalBounceEffect extends AbstractEffect<Step10Attributes> {

	public Step10HorizontalBounceEffect(Action a, State<Step10Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step10Attributes> state) {
		int dx = state.getAttributes().getXVel();
		if(dx > 0){
			dx = dx - 1;
		}else if(dx < 0){
			dx = dx + 1;
		}
		state.getAttributes().setXVel(-dx);
	}

}
