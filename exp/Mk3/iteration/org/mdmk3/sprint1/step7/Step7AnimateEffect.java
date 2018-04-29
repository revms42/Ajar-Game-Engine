package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step7AnimateEffect extends AbstractEffect<Step7Attributes> {

	public Step7AnimateEffect(Action a, State<Step7Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step7Attributes> state) {
		int currentFrame = state.getAttributes().getCurrentFrame();
		if(currentFrame >= 17){
			state.getAttributes().setCurrentFrame(10);
		}else{
			state.getAttributes().setCurrentFrame(++currentFrame);
		}
	}

}
