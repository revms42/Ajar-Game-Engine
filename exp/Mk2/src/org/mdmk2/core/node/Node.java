/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) May 4, 2010 Matthew Stockbridge
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
 * MDMk2
 * org.mdmk2.core
 * Node.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.node;

import java.awt.Shape;
import java.util.List;

import org.mdmk2.core.Position;
import org.mdmk2.core.attributed.Attributed;
import org.mdmk2.core.attributed.AttributedImp;

/**
 * Nodes are the primary structures for object culling for both visual and logic operations.
 * The generic "R" is taken to be a geometric construct, but could be anything that would
 * allow the <code>isInRange</code> method to determine whether or not this Node should be
 * culled from display and logic.
 * 
 * @author mstockbridge
 * May 4, 2010
 * @param	<R>		the type parameter of the view range.
 */
public interface Node<A extends AttributedImp> extends Attributed<A> {
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public Position getRelativePosition();
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param pos
	 */
	public void setRelativePosition(Position pos);
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public Position getAbsolutePosition();
	
	/**
	 * Returns <code>true</code> if this Node currently has any child Nodes.
	 * @return	<code>true</code> if this Node has child Nodes, otherwise <code>false</code>
	 */
	public boolean hasChildren();
	
	/**
	 * Adds a child Node to this Node.
	 * @param	child	the child Node to add under this Node.
	 */
	public void addChild(Node<A> child);
	
	/**
	 * Removes a child Node from this Node.
	 * @param	child	the child Node to be removed.
	 */
	public void removeChild(Node<A> child);
	
	/**
	 * Returns a {@link List} of the children of this Node.
	 * @return	the list of children under this Node.
	 */
	public List<Node<A>> getChildren();

	/**
	 * Returns the Node that claims this Node as it's child.
	 * <p>
	 * Note: Technically a Node may be a child of any number of other Nodes, but
	 * only one will be considered it's "parent".
	 * mstockbridge
	 * 15-May-10
	 * @return	the Node that claims this Node as it's child.
	 */
	public Node<A> getParent();

	/**
	 * Returns any <code>Shapes</code> currently defining the bounding surface
	 * being used in the <code>isInRange</code> method.
	 * mstockbridge
	 * 15-May-10
	 * @return	the current bounding surface.
	 */
	public Shape[] getBoundingSurface();
	
	/**
	 * Sets the bounding surface to the supplied <code>Shape</code>(s), to be used
	 * in the <code>isInRange</code> method.
	 * mstockbridge
	 * 15-May-10
	 * @param	boundingSurface	one or more <code>Shape</code>s to be used as the
	 * 							bounding surface.
	 */
	public void setBoundingSurface(Shape... boundingSurface);
}
