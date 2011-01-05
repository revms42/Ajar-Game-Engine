package org.mdmk3.core.loader;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;
import org.mdmk3.core.collision.AbstractCollidable;

public abstract class MergingCollidable<A extends Attributes> extends AbstractCollidable<A> {

	public MergingCollidable(Node<A> node) {
		super(node);
	}

	public abstract boolean canMergeWith(MergingCollidable<A> target);
	
	public abstract void merge(Node<A> parent, MergingCollidable<A>... children);
}
