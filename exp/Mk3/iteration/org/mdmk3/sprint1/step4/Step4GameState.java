package org.mdmk3.sprint1.step4;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step4GameState extends DefaultState<Step4Attributes> {

	@SuppressWarnings("unchecked")
	public Step4GameState(Step4BounceState bounce){
		this.put(new Step4VerticalBounceEffect(Step4ActionType.BOUNCE_V,bounce));
		this.put(new Step4HorizontalBounceEffect(Step4ActionType.BOUNCE_H,bounce));
		this.put(new CompoundEffect<Step4Attributes>(
				Step4ActionType.BOUNCE_D,
				this,
				new Step4VerticalBounceEffect(Step4ActionType.BOUNCE_V,bounce),
				new Step4HorizontalBounceEffect(Step4ActionType.BOUNCE_H,bounce)
		));
		this.put(new Step4Accell(Step4ActionType.ACCELL_XPOS,this));
		this.put(new Step4Accell(Step4ActionType.ACCELL_YPOS,this));
		this.put(new Step4Accell(Step4ActionType.ACCELL_XNEG,this));
		this.put(new Step4Accell(Step4ActionType.ACCELL_YNEG,this));
		this.put(new Step4MoveEffect(Step4ActionType.MOVE,this));
		this.put(new Step4MoveEffect(null,this));
	}
}
