/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Dec 1, 2010 Matthew Stockbridge
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
 * mdj
 * org.mdj.core.collision
 * Collidable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core.collision;

import org.mdj.core.Attributes;
import org.mdj.core.Decorator;
import org.mdj.core.logic.Action;

/**
 * The Collidable interface defines classes concerned with detecting collisions and determining the results from
 * those collisions.
 * @see org.mdj.core.GameLoop#collision()
 * @see org.mdj.core.GameLoop#getCollidableClass()
 * @author revms
 * @since 0.0.0.153
 */
public interface Collidable<A extends Attributes> extends Decorator<A> {

	/**
	 * Asks this object to provide the <code>Action</code> outcome from a collision with the supplied object.
	 * @param s the <code>Collidable</code> to check for collision with.
	 * @return the <code>Action</code> outcome of the collision, or <code>null</code> if nothing happened.
	 */
	public Action collideWith(Collidable<A> s);
	
	/**
	 * Queries this object to determine if it needs to be checked for collision.
	 * @return <code>true</code> if this object needs to be checked for collisions.
	 */
	public boolean needsCollisionCheck();
}
