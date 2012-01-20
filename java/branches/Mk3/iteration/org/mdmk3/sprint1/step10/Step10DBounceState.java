package org.mdmk3.sprint1.step10;

import java.io.IOException;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step10DBounceState extends DefaultState<Step10Attributes> {
	/*
	 * D:
	 * N|/|/|/
	 * -+-+-+-
	 * D|D|D|D
	 */
	@SuppressWarnings("unchecked")
	public Step10DBounceState() throws IOException{
		this.put(new Step10AnimateEffect(Step10ActionType.ANIMATE,this));
		this.put(new Step10NullEffect(Step10ActionType.BOUNCE_V,this));
		this.put(new Step10NullEffect(Step10ActionType.BOUNCE_H,this));
		this.put(new Step10NullEffect(Step10ActionType.BOUNCE_D,this));
		this.put(new Step10NullEffect(Step10ActionType.ACCELL_XPOS,this));
		this.put(new Step10NullEffect(Step10ActionType.ACCELL_YPOS,this));
		this.put(new Step10NullEffect(Step10ActionType.ACCELL_XNEG,this));
		this.put(new Step10NullEffect(Step10ActionType.ACCELL_YNEG,this));
		this.put(new CompoundEffect<Step10Attributes>(
				Step10ActionType.POWER_UP_V,
				this,
				new Step10PowerUpEffect(Step10ActionType.POWER_UP_V,this),
				new Step10VerticalBounceEffect(Step10ActionType.BOUNCE_V,this)
		));
		this.put(new CompoundEffect<Step10Attributes>(
				Step10ActionType.POWER_UP_H,
				this,
				new Step10PowerUpEffect(Step10ActionType.POWER_UP_H,this),
				new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,this)
		));
		this.put(new CompoundEffect<Step10Attributes>(
				Step10ActionType.POWER_UP_D,
				this,
				new Step10PowerUpEffect(Step10ActionType.POWER_UP_D,this),
				new CompoundEffect<Step10Attributes>(
						Step10ActionType.BOUNCE_D,
						this,
						new Step10VerticalBounceEffect(Step10ActionType.BOUNCE_V,this),
						new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,this)
				)
		));
		this.put(new Step10GameOverEffect(this));
		this.put(new Step10ShootEffect(this));
	}
}
