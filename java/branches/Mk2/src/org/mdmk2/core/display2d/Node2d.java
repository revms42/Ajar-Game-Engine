/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 15-May-10 Matthew Stockbridge
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
 * org.mdmk2.core.display2d
 * Node2d.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.display2d;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import org.mdmk2.core.Node;

/**
 * Node2d provides additional methods to Node in order to provide classes in the display2d package
 * to with methods to facilitate in display.
 * @author mstockbridge
 * 15-May-10
 */
public interface Node2d extends Node<Rectangle> {
	
	/**
	 * Creates a view offset transform for drawing by concatenating all AffineTransforms above
	 * this Node by climbing the parent hierarchy. 
	 * mstockbridge
	 * 15-May-10
	 * @return	a concatenated {@link AffineTransform} that contains the AffineTransform for
	 * 			this Node as well as all this Node's parents.
	 */
	public AffineTransform getDrawTransform();
	
	/**
	 * Returns <code>true</code> if this Node has no set parent.
	 * mstockbridge
	 * 15-May-10
	 * @return	<code>true</code> if this Node has no set parent.
	 */
	public boolean hasParent();
	
	/**
	 * Returns the Node that claims this Node as it's child.
	 * <p>
	 * Note: Technically a Node may be a child of any number of other Nodes, but
	 * only one will be considered it's "parent".
	 * mstockbridge
	 * 15-May-10
	 * @return	the Node that claims this Node as it's child.
	 */
	public Node2d getParent();
	
	/**
	 * Sets the parent of this Node.
	 * mstockbridge
	 * 15-May-10
	 * @param	parent	the Node that claims this Node as it's child.
	 */
	public void setParent(Node2d parent);
}
