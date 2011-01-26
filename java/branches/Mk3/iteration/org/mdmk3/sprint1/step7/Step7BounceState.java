package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step7BounceState extends DefaultState<Step7Attributes> {

	@SuppressWarnings("unchecked")
	public Step7BounceState(){
		this.put(new Step7AnimateEffect(Step7ActionType.ANIMATE,this));
		this.put(new Step7VerticalBounceEffect(Step7ActionType.BOUNCE_V,this));
		this.put(new Step7HorizontalBounceEffect(Step7ActionType.BOUNCE_H,this));
		this.put(new CompoundEffect<Step7Attributes>(
				Step7ActionType.BOUNCE_D,
				this,
				new Step7VerticalBounceEffect(Step7ActionType.BOUNCE_V,this),
				new Step7HorizontalBounceEffect(Step7ActionType.BOUNCE_H,this)
		));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_XPOS,this));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_YPOS,this));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_XNEG,this));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_YNEG,this));
	}
}
