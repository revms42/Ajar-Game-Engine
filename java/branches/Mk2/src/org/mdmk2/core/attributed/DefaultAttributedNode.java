/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Sep 9, 2010 Matthew Stockbridge
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
 * org.mdmk2.core.attributed
 * DefaultAttributedNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.attributed;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.mdmk2.core.cull.CullingMethod;
import org.mdmk2.core.node.DefaultNode;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * Sep 9, 2010
 */
public class DefaultAttributedNode<R,A extends AttributedImp> extends DefaultNode<R> implements AttributedNode<R, A> {

	private final A aImp;
	
	protected Shape[] boundingSurface;
	protected AffineTransform transform;
	
	/**
	 * 
	 * @param dImp
	 */
	public DefaultAttributedNode(A aImp){
		super();
		this.aImp = aImp;
	}
	
	public DefaultAttributedNode(CullingMethod<R> method, A aImp){
		super(method);
		this.aImp = aImp;
	}
	
	/**
	 * 
	 * @param prototype
	 * @param dImp
	 */
	public DefaultAttributedNode(Node<R> prototype, A aImp){
		super(prototype.getCullingMethod());
		this.aImp = aImp;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.Attributed#getAttributes()
	 */
	@Override
	public A getAttributes() {
		return aImp;
	}
}
