/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Jan 5, 2011 Matthew Stockbridge
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * (but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * MDMk3
 * org.mdmk3.core.loader
 * MergingCollidable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.loader;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;
import org.mdmk3.core.collision.AbstractCollidable;

/**
 * This class is a defines two abstract methods for use with map optimization methods. The general idea is that if there
 * are two (or more) nodes that share a common boundary and common attributes then their <code>Collidable</code>
 * decorators can be merged into a single decorator for a parent that contains them both.
 * @author revms
 * @since 0.0.0.156
 * @TODO Determine if this should be part of a utility package.
 */
public abstract class MergingCollidable<A extends Attributes> extends AbstractCollidable<A> {

	/**
	 * Constructs an MergingCollidable using the supplied node as the node the MergingCollidable will decorate.
	 * @param node the node the new MergingCollidable will decorate.
	 */
	public MergingCollidable(Node<A> node) {
		super(node);
	}

	/**
	 * Returns <code>true</code> if this <code>MergingCollidable</code> can be merged with the target.
	 * @param target <code>MergingCollidable</code> to check.
	 * @return <code>true</code> if this node can join the specified node.
	 */
	public abstract boolean canMergeWith(MergingCollidable<A> target);
	
	/**
	 * Merges the specified children into a common MergingCollidable, decorates the parent with the new 
	 * MergingCollidable decorator, update's the parents attributes, then removes the MergingCollidable
	 * decorators from the children.
	 * @param parent the <code>Node</code> that will inherit the collision facet of the specified children.
	 * @param children the <code>Node</code>s that will loose their collision decorators to provide a new
	 * decorator with their combined area.
	 */
	public abstract void merge(Node<A> parent, MergingCollidable<A>... children);
}
