package org.mdmk3.core.disp2d;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.DefaultDecorator;
import org.mdmk3.core.Node;

public abstract class AbstractDisplayable<A extends Attributes> extends DefaultDecorator<A> implements Displayable<A> {

	public AbstractDisplayable(Node<A> node) {
		super(node);
	}

}
