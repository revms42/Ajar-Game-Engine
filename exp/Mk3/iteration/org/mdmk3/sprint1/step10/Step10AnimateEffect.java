package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step10AnimateEffect extends AbstractEffect<Step10Attributes> {

	public Step10AnimateEffect(Action a, State<Step10Attributes> result) {
		super(a, result);
	}

	@Override
	protected void doAction(Entity<Step10Attributes> state) {
		int currentFrame = state.getAttributes().getCurrentFrame();
		if(currentFrame >= 29){
			state.getAttributes().setCurrentFrame(10);
		}else{
			state.getAttributes().setCurrentFrame(++currentFrame);
		}
	}

}
