package org.mdmk3.sprint1.step9;

import org.mdmk3.core.Node;

public class Step9EnemyEntity extends Step9Entity {
	
	public Step9EnemyEntity(Node<Step9Attributes> node) {
		super(node);
		setState(new Step9EnemyState());
	}

}
