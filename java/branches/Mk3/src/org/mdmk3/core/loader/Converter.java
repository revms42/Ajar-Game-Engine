package org.mdmk3.core.loader;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

public interface Converter<D,A extends Attributes> {

	public Node<A> toNode(D value);
}
