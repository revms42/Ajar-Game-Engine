package org.mdmk3.core.cull;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

public interface CullingSurface<A extends Attributes> {

	public boolean isInRange(Node<A> node);
}
