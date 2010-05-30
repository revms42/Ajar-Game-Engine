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
 * org.mdmk2.core.disp2d
 * RootNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core;

import java.util.List;
import java.util.Vector;


/**
 * RootNode is a simple implementation of Node that acts as a lowest level node for a
 * content graph.
 * @author mstockbridge
 * 15-May-10
 * @param <R>	the type parameter of the view range.
 */
public class RootNode<R> implements Node<R> {
	
	protected final Vector<Node<R>> children;
	
	public RootNode(){
		children = new Vector<Node<R>>();
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#addChild(org.mdmk2.core.Node)
	 */
	public void addChild(Node<R> child) {
		children.add(child);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#getChildren()
	 */
	public List<Node<R>> getChildren() {
		return children;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#hasChildren()
	 */
	public boolean hasChildren() {
		return children.size() != 0;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#isInRange(java.lang.Object)
	 */
	public boolean isInRange(R range) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#removeChild(org.mdmk2.core.Node)
	 */
	public void removeChild(Node<R> child) {
		children.remove(child);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#needsUpdate()
	 */
	public org.mdmk2.core.Node.UpdateType needsUpdate() {
		return UpdateType.RENDER_AND_STATUS;
	}

}
