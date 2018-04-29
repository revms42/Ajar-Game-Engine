package org.mdmk3.sprint1.step2;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step2GameState extends DefaultState<Step2Attributes> {

	@SuppressWarnings("unchecked")
	public Step2GameState(){
		this.put(new Step2VerticalBounceEffect(Step2ActionType.BOUNCE_V,this));
		this.put(new Step2HorizontalBounceEffect(Step2ActionType.BOUNCE_H,this));
		this.put(new CompoundEffect<Step2Attributes>(
				Step2ActionType.BOUNCE_D,
				this,
				new Step2VerticalBounceEffect(Step2ActionType.BOUNCE_V,this),
				new Step2HorizontalBounceEffect(Step2ActionType.BOUNCE_H,this)
		));
		this.put(new Step2MoveEffect(Step2ActionType.MOVE,this));
		this.put(new Step2MoveEffect(null,this));
	}
}
