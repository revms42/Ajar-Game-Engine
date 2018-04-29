package org.mdmk3.sprint1.step3;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step3GameState extends DefaultState<Step3Attributes> {

	@SuppressWarnings("unchecked")
	public Step3GameState(){
		this.put(new Step3VerticalBounceEffect(Step3ActionType.BOUNCE_V,this));
		this.put(new Step3HorizontalBounceEffect(Step3ActionType.BOUNCE_H,this));
		this.put(new CompoundEffect<Step3Attributes>(
				Step3ActionType.BOUNCE_D,
				this,
				new Step3VerticalBounceEffect(Step3ActionType.BOUNCE_V,this),
				new Step3HorizontalBounceEffect(Step3ActionType.BOUNCE_H,this)
		));
		this.put(new Step3MoveEffect(Step3ActionType.MOVE,this));
		this.put(new Step3MoveEffect(null,this));
	}
}
