package org.mdmk3.sprint1.step8;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step8GameState extends DefaultState<Step8Attributes> {

	@SuppressWarnings("unchecked")
	public Step8GameState(Step8HBounceState h,Step8VBounceState v, Step8DBounceState d){
		this.put(new Step8VerticalBounceEffect(Step8ActionType.BOUNCE_V,v));
		this.put(new Step8HorizontalBounceEffect(Step8ActionType.BOUNCE_H,h));
		this.put(new CompoundEffect<Step8Attributes>(
				Step8ActionType.BOUNCE_D,
				d,
				new Step8VerticalBounceEffect(Step8ActionType.BOUNCE_V,v),
				new Step8HorizontalBounceEffect(Step8ActionType.BOUNCE_H,h)
		));
		
		this.put(new CompoundEffect<Step8Attributes>(
				Step8ActionType.POWER_UP_V,
				v,
				new Step8PowerUpEffect(Step8ActionType.POWER_UP_V,v),
				new Step8VerticalBounceEffect(Step8ActionType.BOUNCE_V,v)
		));
		this.put(new CompoundEffect<Step8Attributes>(
				Step8ActionType.POWER_UP_H,
				h,
				new Step8PowerUpEffect(Step8ActionType.POWER_UP_H,h),
				new Step8HorizontalBounceEffect(Step8ActionType.BOUNCE_H,h)
		));
		this.put(new CompoundEffect<Step8Attributes>(
				Step8ActionType.POWER_UP_D,
				d,
				new Step8PowerUpEffect(Step8ActionType.POWER_UP_D,d),
				new CompoundEffect<Step8Attributes>(
						Step8ActionType.BOUNCE_D,
						d,
						new Step8VerticalBounceEffect(Step8ActionType.BOUNCE_V,v),
						new Step8HorizontalBounceEffect(Step8ActionType.BOUNCE_H,h)
				)
		));
		
		this.put(new Step8Accell(Step8ActionType.ACCELL_XPOS,this));
		this.put(new Step8Accell(Step8ActionType.ACCELL_YPOS,this));
		this.put(new Step8Accell(Step8ActionType.ACCELL_XNEG,this));
		this.put(new Step8Accell(Step8ActionType.ACCELL_YNEG,this));
		this.put(new Step8MoveEffect(Step8ActionType.MOVE,this));
		this.put(new CompoundEffect<Step8Attributes>(
				null,
				this,
				new Step8MoveEffect(null,this),
				new Step8AnimateEffect(Step8ActionType.ANIMATE,this)
		));
	}
}
