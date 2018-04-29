package org.mdmk3.sprint1.step8;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step8DBounceState extends DefaultState<Step8Attributes> {
	/*
	 * D:
	 * N|/|/|/
	 * -+-+-+-
	 * D|D|D|D
	 */
	@SuppressWarnings("unchecked")
	public Step8DBounceState(){
		this.put(new Step8AnimateEffect(Step8ActionType.ANIMATE,this));
		this.put(new Step8NullEffect(Step8ActionType.BOUNCE_V,this));
		this.put(new Step8NullEffect(Step8ActionType.BOUNCE_H,this));
		this.put(new Step8NullEffect(Step8ActionType.BOUNCE_D,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_XPOS,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_YPOS,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_XNEG,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_YNEG,this));
		this.put(new CompoundEffect<Step8Attributes>(
				Step8ActionType.POWER_UP_V,
				this,
				new Step8PowerUpEffect(Step8ActionType.POWER_UP_V,this),
				new Step8VerticalBounceEffect(Step8ActionType.BOUNCE_V,this)
		));
		this.put(new CompoundEffect<Step8Attributes>(
				Step8ActionType.POWER_UP_H,
				this,
				new Step8PowerUpEffect(Step8ActionType.POWER_UP_H,this),
				new Step8HorizontalBounceEffect(Step8ActionType.BOUNCE_H,this)
		));
		this.put(new CompoundEffect<Step8Attributes>(
				Step8ActionType.POWER_UP_D,
				this,
				new Step8PowerUpEffect(Step8ActionType.POWER_UP_D,this),
				new CompoundEffect<Step8Attributes>(
						Step8ActionType.BOUNCE_D,
						this,
						new Step8VerticalBounceEffect(Step8ActionType.BOUNCE_V,this),
						new Step8HorizontalBounceEffect(Step8ActionType.BOUNCE_H,this)
				)
		));
	}
}
