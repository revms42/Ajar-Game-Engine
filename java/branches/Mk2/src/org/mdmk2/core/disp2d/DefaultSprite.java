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
 * AbstractDisplayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.disp2d;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.mdmk2.core.attributed.AttributedImp;
import org.mdmk2.core.node.DefaultNode;
import org.mdmk2.core.node.Node;

/**
 * 
 * @author mstockbridge
 * 13-Jun-10
 * @param <R>
 */
public class DefaultSprite<A extends AttributedImp> extends DefaultNode<A> implements Sprite<A> {

	private final DisplayableImp<A> dImp;
	
	protected Shape[] boundingSurface;
	protected AffineTransform transform;
	
	/**
	 * 
	 * @param dImp
	 */
	public DefaultSprite(A attributes, DisplayableImp<A> dImp){
		super(attributes);
		this.dImp = dImp;
	}
	
	/**
	 * 
	 * @param prototype
	 * @param dImp
	 */
	public DefaultSprite(Node<A> prototype, DisplayableImp<A> dImp){
		super(prototype.getAttributes());
		this.dImp = dImp;
	}

	/**
	 * Gets the local transform of this object relative to it's parent.
	 * mstockbridge
	 * 15-May-10
	 * @return	the local <code>AffineTransform</code>
	 */
	public AffineTransform getTransform() {
		return getDisplayableImp().getTransform();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Displayable#updateDisplay(java.awt.Graphics2D)
	 */
	public void updateDisplay(Graphics2D g2) {
		getDisplayableImp().updateDisplay(g2);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Displayable#getDisplayableImp()
	 */
	public DisplayableImp<A> getDisplayableImp() {
		return dImp;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Displayable#needsDisplayUpdate()
	 */
	public boolean needsDisplayUpdate() {
		return getDisplayableImp().needsDisplayUpdate();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.NodeDecorator#getNode()
	 */
	@Override
	public Node<A> getNode() {
		return this;
	}
}
