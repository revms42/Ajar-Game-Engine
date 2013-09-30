/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 26, 2013 Matthew Stockbridge
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
 * AGE
 * org.ajar.age.collision
 * Collidable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */

/**
 * @author mstockbr
 */
package org.ajar.age.collision;

import org.ajar.age.Attributes;
import org.ajar.age.Decorator;
import org.ajar.age.logic.Action;

/**
 * TOOD: Rewrite.
 * The Collidable interface defines classes concerned with detecting collisions and determining the results from
 * those collisions.
 * @see org.ajar.age.GameLoop#collision()
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
	
}
