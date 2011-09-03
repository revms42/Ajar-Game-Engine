package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step9GameState extends DefaultState<Step9Attributes> {

	@SuppressWarnings("unchecked")
	public Step9GameState(Step9HBounceState h,Step9VBounceState v, Step9DBounceState d){
		this.put(new Step9VerticalBounceEffect(Step9ActionType.BOUNCE_V,v));
		this.put(new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,h));
		this.put(new CompoundEffect<Step9Attributes>(
				Step9ActionType.BOUNCE_D,
				d,
				new Step9VerticalBounceEffect(Step9ActionType.BOUNCE_V,v),
				new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,h)
		));
		
		this.put(new CompoundEffect<Step9Attributes>(
				Step9ActionType.POWER_UP_V,
				v,
				new Step9PowerUpEffect(Step9ActionType.POWER_UP_V,v),
				new Step9VerticalBounceEffect(Step9ActionType.BOUNCE_V,v)
		));
		this.put(new CompoundEffect<Step9Attributes>(
				Step9ActionType.POWER_UP_H,
				h,
				new Step9PowerUpEffect(Step9ActionType.POWER_UP_H,h),
				new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,h)
		));
		this.put(new CompoundEffect<Step9Attributes>(
				Step9ActionType.POWER_UP_D,
				d,
				new Step9PowerUpEffect(Step9ActionType.POWER_UP_D,d),
				new CompoundEffect<Step9Attributes>(
						Step9ActionType.BOUNCE_D,
						d,
						new Step9VerticalBounceEffect(Step9ActionType.BOUNCE_V,v),
						new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,h)
				)
		));
		
		this.put(new Step9Accell(Step9ActionType.ACCELL_XPOS,this));
		this.put(new Step9Accell(Step9ActionType.ACCELL_YPOS,this));
		this.put(new Step9Accell(Step9ActionType.ACCELL_XNEG,this));
		this.put(new Step9Accell(Step9ActionType.ACCELL_YNEG,this));
		this.put(new Step9MoveEffect(Step9ActionType.MOVE,this));
		this.put(new CompoundEffect<Step9Attributes>(
				null,
				this,
				new Step9MoveEffect(null,this),
				new Step9AnimateEffect(Step9ActionType.ANIMATE,this)
		));
	}
}
