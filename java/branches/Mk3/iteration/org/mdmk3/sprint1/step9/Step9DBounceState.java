package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step9DBounceState extends DefaultState<Step9Attributes> {
	/*
	 * D:
	 * N|/|/|/
	 * -+-+-+-
	 * D|D|D|D
	 */
	@SuppressWarnings("unchecked")
	public Step9DBounceState(){
		this.put(new Step9AnimateEffect(Step9ActionType.ANIMATE,this));
		this.put(new Step9NullEffect(Step9ActionType.BOUNCE_V,this));
		this.put(new Step9NullEffect(Step9ActionType.BOUNCE_H,this));
		this.put(new Step9NullEffect(Step9ActionType.BOUNCE_D,this));
		this.put(new Step9NullEffect(Step9ActionType.ACCELL_XPOS,this));
		this.put(new Step9NullEffect(Step9ActionType.ACCELL_YPOS,this));
		this.put(new Step9NullEffect(Step9ActionType.ACCELL_XNEG,this));
		this.put(new Step9NullEffect(Step9ActionType.ACCELL_YNEG,this));
		this.put(new CompoundEffect<Step9Attributes>(
				Step9ActionType.POWER_UP_V,
				this,
				new Step9PowerUpEffect(Step9ActionType.POWER_UP_V,this),
				new Step9VerticalBounceEffect(Step9ActionType.BOUNCE_V,this)
		));
		this.put(new CompoundEffect<Step9Attributes>(
				Step9ActionType.POWER_UP_H,
				this,
				new Step9PowerUpEffect(Step9ActionType.POWER_UP_H,this),
				new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,this)
		));
		this.put(new CompoundEffect<Step9Attributes>(
				Step9ActionType.POWER_UP_D,
				this,
				new Step9PowerUpEffect(Step9ActionType.POWER_UP_D,this),
				new CompoundEffect<Step9Attributes>(
						Step9ActionType.BOUNCE_D,
						this,
						new Step9VerticalBounceEffect(Step9ActionType.BOUNCE_V,this),
						new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,this)
				)
		));
		this.put(new Step9GameOverEffect(this));
	}
}
