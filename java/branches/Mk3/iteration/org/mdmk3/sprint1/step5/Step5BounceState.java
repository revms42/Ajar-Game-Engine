package org.mdmk3.sprint1.step5;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step5BounceState extends DefaultState<Step5Attributes> {

	@SuppressWarnings("unchecked")
	public Step5BounceState(){
		this.put(new Step5VerticalBounceEffect(Step5ActionType.BOUNCE_V,this));
		this.put(new Step5HorizontalBounceEffect(Step5ActionType.BOUNCE_H,this));
		this.put(new CompoundEffect<Step5Attributes>(
				Step5ActionType.BOUNCE_D,
				this,
				new Step5VerticalBounceEffect(Step5ActionType.BOUNCE_V,this),
				new Step5HorizontalBounceEffect(Step5ActionType.BOUNCE_H,this)
		));
		this.put(new Step5NullEffect(Step5ActionType.ACCELL_XPOS,this));
		this.put(new Step5NullEffect(Step5ActionType.ACCELL_YPOS,this));
		this.put(new Step5NullEffect(Step5ActionType.ACCELL_XNEG,this));
		this.put(new Step5NullEffect(Step5ActionType.ACCELL_YNEG,this));
	}
}
