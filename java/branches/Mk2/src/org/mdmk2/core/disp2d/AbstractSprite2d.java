/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 16-May-10 Matthew Stockbridge
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
 * Sprite2d.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.disp2d;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.mdmk2.core.AbstractSprite;
import org.mdmk2.core.logic.Bounded;

/**
 * @author mstockbridge
 * 16-May-10
 */
public abstract class AbstractSprite2d<R extends Shape, E extends AbstractSprite2d<R,E>> 
		extends AbstractSprite<Rectangle,E,R> implements Displayable, Node2d
{
	private Node2d parent;
	private AffineTransform transform;
	/**
	 * @param bounds
	 */
	public AbstractSprite2d(R bounds) {
		super(bounds);
		transform = AffineTransform.getTranslateInstance(0.0d, 0.0d);
	}
	
	protected abstract E thisAsE();

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Bounded#collidesWith(org.mdmk2.core.logic.Entity)
	 */
	public boolean collidesWith(Bounded<R> r) {
		R bounds = r.getCollisionBounds();
		if(bounds != null && bounds != null){
			return bounds.intersects(r.getCollisionBounds().getBounds2D());
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#getDrawTransform()
	 */
	public AffineTransform getDrawTransform() {
		AffineTransform local = getTransform();
		AffineTransform ret = null;
		
		if(hasParent()){
			AffineTransform pt = getParent().getDrawTransform();
			
			if(pt != null){
				ret = (AffineTransform) pt.clone();
				
				if(local != null){
					ret.concatenate(local);
				}
			}
		}else if(local != null){
			ret = (AffineTransform) local.clone();
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#getParent()
	 */
	public Node2d getParent() {
		return parent;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#setParent(org.mdmk2.core.Node)
	 */
	public void setParent(Node2d parent) {
		this.parent = parent;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#hasParent()
	 */
	public boolean hasParent() {
		return parent != null;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#getTransform()
	 */
	public AffineTransform getTransform() {
		return transform;
	}
}
