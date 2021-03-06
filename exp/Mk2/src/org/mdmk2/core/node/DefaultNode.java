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
 * DefaultNode.java
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
import java.util.Vector;

import org.mdmk2.core.Position;
import org.mdmk2.core.attributed.AttributedImp;

/**
 * @author mstockbridge
 * 30-May-10
 */
public class DefaultNode<A extends AttributedImp> implements Node<A> {

	private final Vector<Node<A>> children;
	private Node<A> parent;
	private Position position;
	private Shape[] boundingSurface;
	private final A attributes;
	
	public DefaultNode(A attributes){
		children = new Vector<Node<A>>();
		position = new Position(0.0d,0.0d,0.0d);
		this.attributes = attributes;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#addChild(org.mdmk2.core.Node)
	 */
	public void addChild(Node<A> child) {
		children.add(child);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#getChildren()
	 */
	public List<Node<A>> getChildren() {
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
	public void removeChild(Node<A> child) {
		children.remove(child);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#getParent()
	 */
	public Node<A> getParent() {
		return this.parent;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.Node#getAbsolutePosition()
	 */
	public Position getAbsolutePosition() {
		if(parent != null){
			return parent.getRelativePosition().sum(getRelativePosition());
		}else{
			return getRelativePosition();
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.Node#getRelativePosition()
	 */
	public Position getRelativePosition() {
		return position;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.Node#setRelativePosition(org.mdmk2.core.Position)
	 */
	public void setRelativePosition(Position pos) {
		this.position = pos;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.Node#getBoundingSurface()
	 */
	@Override
	public Shape[] getBoundingSurface() {
		return boundingSurface;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.Node#setBoundingSurface(java.awt.Shape[])
	 */
	@Override
	public void setBoundingSurface(Shape... boundingSurface) {
		this.boundingSurface = boundingSurface;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.Attributed#getAttributes()
	 */
	@Override
	public A getAttributes() {
		return attributes;
	}
}
