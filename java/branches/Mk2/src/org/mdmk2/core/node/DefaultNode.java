/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 30-May-10 Matthew Stockbridge
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
 * AbstractNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.node;

import java.util.List;
import java.util.Vector;

import org.mdmk2.core.Position;
import org.mdmk2.core.cull.CullableImp;

/**
 * @author mstockbridge
 * 30-May-10
 */
public class DefaultNode<R> implements Node<R> {

	private final Vector<Node<R>> children;
	private final CullableImp<R> cImp;
	private Node<R> parent;
	private Position pos;
	
	public DefaultNode(CullableImp<R> cImp){
		this.cImp = cImp;
		this.pos = new Position();
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
	 * @see org.mdmk2.core.Node#removeChild(org.mdmk2.core.Node)
	 */
	public void removeChild(Node<R> child) {
		children.remove(child);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#getParent()
	 */
	public Node<R> getParent() {
		return this.parent;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.cull.Cullable#getImplementation()
	 */
	public CullableImp<R> getCullableImp() {
		return cImp;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.cull.Cullable#getPosition()
	 */
	public Position getRelativePosition() {
		return this.pos;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.cull.Cullable#isInRange(java.lang.Object)
	 */
	public boolean isInRange(R range) {
		return cImp.isInRange(range);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.cull.Cullable#getAbsolutePosition()
	 */
	public Position getAbsolutePosition() {
		if(getParent() != null){
			return this.getRelativePosition().sum(getParent().getAbsolutePosition());
		}else{
			return this.getRelativePosition().clone();
		}
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.cull.Cullable#setRelativePosition(org.mdmk2.core.Position)
	 */
	public void setRelativePosition(Position pos) {
		this.pos = pos;
	}
}
