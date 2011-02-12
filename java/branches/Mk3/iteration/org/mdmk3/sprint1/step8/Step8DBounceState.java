package org.mdmk3.sprint1.step8;

import org.mdmk3.core.logic.DefaultState;

public class Step8DBounceState extends DefaultState<Step8Attributes> {
	/*
	 * D:
	 * N|/|/|/
	 * -+-+-+-
	 * D|D|D|D
	 */
	public Step8DBounceState(){
		this.put(new Step8AnimateEffect(Step8ActionType.ANIMATE,this));
		this.put(new Step8NullEffect(Step8ActionType.BOUNCE_V,this));
		this.put(new Step8NullEffect(Step8ActionType.BOUNCE_H,this));
		this.put(new Step8NullEffect(Step8ActionType.BOUNCE_D,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_XPOS,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_YPOS,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_XNEG,this));
		this.put(new Step8NullEffect(Step8ActionType.ACCELL_YNEG,this));
	}
}
