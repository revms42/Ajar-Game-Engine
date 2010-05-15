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
package org.mdmk2.core;

import java.util.List;

/**
 * Nodes are the primary structures for object culling for both visual and logic operations.
 * The generic "R" is taken to be a geometric construct, but could be anything that would
 * allow the <code>isInRange</code> method to determine whether or not this Node should be
 * culled from display and logic.
 * 
 * @author mstockbridge
 * May 4, 2010
 *
 */
public interface Node<R> {

	/**
	 * Determines whether this Node is can be updated or displayed based on the range
	 * provided.
	 * @param	range	the range currently in the "view" of the game. 
	 * @return			whether this Node is current in the provided range.
	 */
	public boolean isInRange(R range);
	
	/**
	 * Returns <code>true</code> if this Node currently has any child Nodes.
	 * @return	<code>true</code> if this Node has child Nodes, otherwise <code>false</code>
	 */
	public boolean hasChildren();
	
	/**
	 * Adds a child Node to this Node.
	 * @param	child	the child Node to add under this Node.
	 */
	public void addChild(Node<R> child);
	
	/**
	 * Removes a child Node from this Node.
	 * @param	child	the child Node to be removed.
	 */
	public void removeChild(Node<R> child);
	
	/**
	 * Returns a {@link List} of the children of this Node.
	 * @return	the list of children under this Node.
	 */
	public List<Node<R>> getChildren();


}
