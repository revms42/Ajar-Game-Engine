package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step7GameState extends DefaultState<Step7Attributes> {

	@SuppressWarnings("unchecked")
	public Step7GameState(Step7HBounceState h,Step7VBounceState v, Step7DBounceState d){
		this.put(new Step7VerticalBounceEffect(Step7ActionType.BOUNCE_V,v));
		this.put(new Step7HorizontalBounceEffect(Step7ActionType.BOUNCE_H,h));
		this.put(new CompoundEffect<Step7Attributes>(
				Step7ActionType.BOUNCE_D,
				d,
				new Step7VerticalBounceEffect(Step7ActionType.BOUNCE_V,v),
				new Step7HorizontalBounceEffect(Step7ActionType.BOUNCE_H,h)
		));
		this.put(new Step7Accell(Step7ActionType.ACCELL_XPOS,this));
		this.put(new Step7Accell(Step7ActionType.ACCELL_YPOS,this));
		this.put(new Step7Accell(Step7ActionType.ACCELL_XNEG,this));
		this.put(new Step7Accell(Step7ActionType.ACCELL_YNEG,this));
		this.put(new Step7MoveEffect(Step7ActionType.MOVE,this));
		this.put(new CompoundEffect<Step7Attributes>(
				null,
				this,
				new Step7MoveEffect(null,this),
				new Step7AnimateEffect(Step7ActionType.ANIMATE,this)
		));
	}
}
