package org.mdmk3.core.logic;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.DefaultDecorator;
import org.mdmk3.core.Node;

public abstract class AbstractEntity<A extends Attributes> extends DefaultDecorator<A> implements Entity<A> {

	public AbstractEntity(Node<A> node) {
		super(node);
	}

}
