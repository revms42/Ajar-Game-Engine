/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Jan 19, 2012 Matthew Stockbridge
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
 * org.mdj.core.logic
 * Controller.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core.logic;

import org.mdj.core.Attributes;

/**
 * Classes that implement this interface are sources of <code>Entity</code> intentional action (as opposed to 
 * unintentional action as a result of collisions).
 * <p>
 * Typically, this equates to either player input or AI algorithm input.
 * @author revms
 * @since 0.0.0.165
 */
public interface Controller<A extends Attributes> {

	/**
	 * This method indicates that the provided entity is looking for input from this controller, which will be added to
	 * its action map via the {@link Entity#addAction(Action) addAction} method in Entity.
	 * @param entity the entity requesting controller input.
	 */
	public void pollForInput(Entity<A> entity);
}
