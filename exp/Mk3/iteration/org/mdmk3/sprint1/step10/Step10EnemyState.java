package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.DefaultState;

public class Step10EnemyState extends DefaultState<Step10Attributes> {

	public Step10EnemyState() {
		this.put(new Step10AnimateEffect(null,this));
		this.put(new Step10DieEffect(Step10ActionType.DIE,this));
	}
}
