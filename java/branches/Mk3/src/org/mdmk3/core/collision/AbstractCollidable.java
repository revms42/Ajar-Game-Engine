package org.mdmk3.core.collision;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.DefaultDecorator;
import org.mdmk3.core.Node;

public abstract class AbstractCollidable<A extends Attributes> extends DefaultDecorator<A> implements Collidable<A> {

	public AbstractCollidable(Node<A> node) {
		super(node);
	}

}
