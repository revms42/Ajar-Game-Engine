package org.mdmk3.sprint1.step6;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step6BounceState extends DefaultState<Step6Attributes> {

	@SuppressWarnings("unchecked")
	public Step6BounceState(){
		this.put(new Step6VerticalBounceEffect(Step6ActionType.BOUNCE_V,this));
		this.put(new Step6HorizontalBounceEffect(Step6ActionType.BOUNCE_H,this));
		this.put(new CompoundEffect<Step6Attributes>(
				Step6ActionType.BOUNCE_D,
				this,
				new Step6VerticalBounceEffect(Step6ActionType.BOUNCE_V,this),
				new Step6HorizontalBounceEffect(Step6ActionType.BOUNCE_H,this)
		));
		this.put(new Step6NullEffect(Step6ActionType.ACCELL_XPOS,this));
		this.put(new Step6NullEffect(Step6ActionType.ACCELL_YPOS,this));
		this.put(new Step6NullEffect(Step6ActionType.ACCELL_XNEG,this));
		this.put(new Step6NullEffect(Step6ActionType.ACCELL_YNEG,this));
	}
}
