package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step9AnimateEffect extends AbstractEffect<Step9Attributes> {

	public Step9AnimateEffect(Action a, State<Step9Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step9Attributes> state) {
		int currentFrame = state.getAttributes().getCurrentFrame();
		if(currentFrame >= 29){
			state.getAttributes().setCurrentFrame(10);
		}else{
			state.getAttributes().setCurrentFrame(++currentFrame);
		}
	}

}
