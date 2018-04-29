package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.DefaultState;

public class Step9EnemyState extends DefaultState<Step9Attributes> {

	public Step9EnemyState() {
		this.put(new Step9AnimateEffect(null,this));
	}
}
