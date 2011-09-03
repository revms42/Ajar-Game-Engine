package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step9VBounceState extends DefaultState<Step9Attributes> {
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
	public Step9VBounceState(Step9DBounceState d){
		this.put(new Step9AnimateEffect(Step9ActionType.ANIMATE,this));
		this.put(new Step9NullEffect(Step9ActionType.BOUNCE_V,this));
		this.put(new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,d));
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
				d,
				new Step9PowerUpEffect(Step9ActionType.POWER_UP_H,d),
				new Step9HorizontalBounceEffect(Step9ActionType.BOUNCE_H,d)
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
	}
}
