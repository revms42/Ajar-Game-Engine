package org.mdmk3.sprint1.step7;

import org.mdmk3.core.logic.DefaultState;

public class Step7DBounceState extends DefaultState<Step7Attributes> {
	/*
	 * D:
	 * N|/|/|/
	 * -+-+-+-
	 * D|D|D|D
	 */
	public Step7DBounceState(){
		this.put(new Step7AnimateEffect(Step7ActionType.ANIMATE,this));
		this.put(new Step7NullEffect(Step7ActionType.BOUNCE_V,this));
		this.put(new Step7NullEffect(Step7ActionType.BOUNCE_H,this));
		this.put(new Step7NullEffect(Step7ActionType.BOUNCE_D,this));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_XPOS,this));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_YPOS,this));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_XNEG,this));
		this.put(new Step7NullEffect(Step7ActionType.ACCELL_YNEG,this));
	}
}
