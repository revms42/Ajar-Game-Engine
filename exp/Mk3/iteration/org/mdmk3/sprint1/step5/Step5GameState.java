package org.mdmk3.sprint1.step5;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step5GameState extends DefaultState<Step5Attributes> {

	@SuppressWarnings("unchecked")
	public Step5GameState(Step5BounceState bounce){
		this.put(new Step5VerticalBounceEffect(Step5ActionType.BOUNCE_V,bounce));
		this.put(new Step5HorizontalBounceEffect(Step5ActionType.BOUNCE_H,bounce));
		this.put(new CompoundEffect<Step5Attributes>(
				Step5ActionType.BOUNCE_D,
				this,
				new Step5VerticalBounceEffect(Step5ActionType.BOUNCE_V,bounce),
				new Step5HorizontalBounceEffect(Step5ActionType.BOUNCE_H,bounce)
		));
		this.put(new Step5Accell(Step5ActionType.ACCELL_XPOS,this));
		this.put(new Step5Accell(Step5ActionType.ACCELL_YPOS,this));
		this.put(new Step5Accell(Step5ActionType.ACCELL_XNEG,this));
		this.put(new Step5Accell(Step5ActionType.ACCELL_YNEG,this));
		this.put(new Step5MoveEffect(Step5ActionType.MOVE,this));
		this.put(new Step5MoveEffect(null,this));
	}
}
