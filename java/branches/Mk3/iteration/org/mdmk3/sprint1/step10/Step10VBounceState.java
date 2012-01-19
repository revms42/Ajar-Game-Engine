package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step10VBounceState extends DefaultState<Step10Attributes> {
	/*
	 * TODO:
	 * This needs to be extended to multiple bounce states: If there is a H or V bounce, then D needs to get disabled.
	 * If there is a D then H and V need to get disabled.
	 * 
	 * Command on top. Resultant state on bottom.
	 * 
	 * N:
	 * N|H|V|D
	 * -+-+-+-
	 * N|H|V|D
	 * 
	 * H:
	 * N|/|V|/
	 * -+-+-+-
	 * H|H|D|H
	 * 
	 * V:
	 * N|H|/|/
	 * -+-+-+-
	 * V|D|V|/
	 * 
	 * D:
	 * N|/|/|/
	 * -+-+-+-
	 * D|D|D|D
	 */
	@SuppressWarnings("unchecked")
	public Step10VBounceState(Step10DBounceState d){
		this.put(new Step10AnimateEffect(Step10ActionType.ANIMATE,this));
		this.put(new Step10NullEffect(Step10ActionType.BOUNCE_V,this));
		this.put(new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,d));
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
				d,
				new Step10PowerUpEffect(Step10ActionType.POWER_UP_H,d),
				new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,d)
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
	}
}
