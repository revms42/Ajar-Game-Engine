package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step7GameState extends DefaultState<Step7Attributes> {

	@SuppressWarnings("unchecked")
	public Step7GameState(Step7BounceState bounce){
		this.put(new Step7VerticalBounceEffect(Step7ActionType.BOUNCE_V,bounce));
		this.put(new Step7HorizontalBounceEffect(Step7ActionType.BOUNCE_H,bounce));
		this.put(new CompoundEffect<Step7Attributes>(
				Step7ActionType.BOUNCE_D,
				this,
				new Step7VerticalBounceEffect(Step7ActionType.BOUNCE_V,bounce),
				new Step7HorizontalBounceEffect(Step7ActionType.BOUNCE_H,bounce)
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
