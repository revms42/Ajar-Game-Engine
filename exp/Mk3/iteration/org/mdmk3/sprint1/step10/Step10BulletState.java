package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultState;

public class Step10BulletState extends DefaultState<Step10Attributes> {

	@SuppressWarnings("unchecked")
	public Step10BulletState() {
		this.put(new Step10DieEffect(Step10ActionType.DIE,this));
		this.put(new CompoundEffect<Step10Attributes>(
				null,
				this,
				new Step10MoveEffect(null,this),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,this)
		));
	}
}
