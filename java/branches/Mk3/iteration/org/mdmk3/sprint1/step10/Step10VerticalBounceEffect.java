package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step10VerticalBounceEffect extends AbstractEffect<Step10Attributes> {

	public Step10VerticalBounceEffect(Action a, State<Step10Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step10Attributes> state) {
		int dy = state.getAttributes().getYVel();
		
		double x = state.getAttributes().getXPos();
		double y = state.getAttributes().getYPos();
		
		state.getAttributes().setPosition(x, y-dy);
		
		if(dy > 0){
			dy = dy - 1;
		}else if(dy < 0){
			dy = dy + 1;
		}
		state.getAttributes().setYVel(-dy);
	}

}
