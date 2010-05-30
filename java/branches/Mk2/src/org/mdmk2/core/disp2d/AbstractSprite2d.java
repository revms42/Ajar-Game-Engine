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

import java.awt.Shape;

import org.mdmk2.core.logic.Bounded;
import org.mdmk2.core.logic.Entity;
import org.mdmk2.core.logic.EntityState;
import org.mdmk2.core.logic.Stated;

/**
 * @author mstockbridge
 * 16-May-10
 */
public abstract class AbstractSprite2d<R extends Shape, E extends AbstractSprite2d<R,E>> 
		extends AbstractDisplayable 
		implements Entity, Stated<E>, Bounded<R> 
{
	private EntityState<E> currentState;
	
	private R collisionBounds;
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#getState()
	 */
	public EntityState<E> getState() {
		return currentState;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#setState(org.mdmk2.core.logic.EntityState)
	 */
	public void setState(EntityState<E> s) {
		this.currentState = s;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#getCollisionBounds()
	 */
	public R getCollisionBounds() {
		return collisionBounds;
	}
	
	public void setCollisionBounds(R bounds) {
		this.collisionBounds = bounds;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#updateStatus()
	 */
	public void updateStatus() {
		currentState.apply(thisAsE());
	}
	
	protected abstract E thisAsE();

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Bounded#collidesWith(org.mdmk2.core.logic.Entity)
	 */
	public boolean collidesWith(Bounded<R> r) {
		R bounds = r.getCollisionBounds();
		if(collisionBounds != null && bounds != null){
			return collisionBounds.intersects(r.getCollisionBounds().getBounds2D());
		}else{
			return false;
		}
	}
}
