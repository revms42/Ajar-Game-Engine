package org.mdmk3.sprint1.step10;

import java.io.IOException;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step10GameState extends DefaultState<Step10Attributes> {

	@SuppressWarnings("unchecked")
	public Step10GameState(Step10HBounceState h,Step10VBounceState v, Step10DBounceState d) throws IOException{
		this.put(new Step10VerticalBounceEffect(Step10ActionType.BOUNCE_V,v));
		this.put(new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,h));
		this.put(new CompoundEffect<Step10Attributes>(
				Step10ActionType.BOUNCE_D,
				d,
				new Step10VerticalBounceEffect(Step10ActionType.BOUNCE_V,v),
				new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,h)
		));
		
		this.put(new CompoundEffect<Step10Attributes>(
				Step10ActionType.POWER_UP_V,
				v,
				new Step10PowerUpEffect(Step10ActionType.POWER_UP_V,v),
				new Step10VerticalBounceEffect(Step10ActionType.BOUNCE_V,v)
		));
		this.put(new CompoundEffect<Step10Attributes>(
				Step10ActionType.POWER_UP_H,
				h,
				new Step10PowerUpEffect(Step10ActionType.POWER_UP_H,h),
				new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,h)
		));
		this.put(new CompoundEffect<Step10Attributes>(
				Step10ActionType.POWER_UP_D,
				d,
				new Step10PowerUpEffect(Step10ActionType.POWER_UP_D,d),
				new CompoundEffect<Step10Attributes>(
						Step10ActionType.BOUNCE_D,
						d,
						new Step10VerticalBounceEffect(Step10ActionType.BOUNCE_V,v),
						new Step10HorizontalBounceEffect(Step10ActionType.BOUNCE_H,h)
				)
		));
		
		this.put(new Step10Accell(Step10ActionType.ACCELL_XPOS,this));
		this.put(new Step10Accell(Step10ActionType.ACCELL_YPOS,this));
		this.put(new Step10Accell(Step10ActionType.ACCELL_XNEG,this));
		this.put(new Step10Accell(Step10ActionType.ACCELL_YNEG,this));
		this.put(new Step10MoveEffect(Step10ActionType.MOVE,this));
		this.put(new CompoundEffect<Step10Attributes>(
				null,
				this,
				new Step10MoveEffect(null,this),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,this)
		));
		this.put(new Step10GameOverEffect(this));
		this.put(new Step10ShootEffect(this));
	}
}
