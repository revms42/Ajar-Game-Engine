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
 * org.mdj.core.logic
 * Effect.java
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
 * Classes that implement this interface change the <code>Attributes</code> of <code>Entities</code>, and in the process
 * change (or can change) the <code>State</code> of the entity whose attributes have been changed. 
 * @author revms
 * @since 0.0.0.153
 */
public interface Effect<A extends Attributes> {

	/**
	 * Updates the attributes of the provided <code>Entity</code>, and returns the resultant state. 
	 * @param entity the entity whose attributes will be modified.
	 * @return the <code>State</code> of the entity after the update.
	 */
	public State<A> perform(Entity<A> entity);
	
	/**
	 * The <code>Action</code> that is associated with this <code>Effect</code>.
	 * @return this effect's associated action.
	 */
	public Action getAction();
	
	/**
	 * The <code>State</code> that results from the application of this <code>Effect</code>.
	 * @return the state resulting from the application of this <code>Effect</code>.
	 */
	public State<A> getResultState();
	
}
