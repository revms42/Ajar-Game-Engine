/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 13-Jun-10 Matthew Stockbridge
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
 * org.mdmk2.core.collision
 * Solid.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.collision;

import org.mdmk2.core.attributed.AttributedImp;
import org.mdmk2.core.logic.Action;

/**
 * @author mstockbridge
 * 13-Jun-10
 */
public interface Collidable<R,A extends AttributedImp> {

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param s
	 * @return
	 */
	public Action<A> collideWith(Collidable<R,A> s);
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public CollidableImp<R,A> getImplementation();
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public boolean needsCollisionCheck();
}
