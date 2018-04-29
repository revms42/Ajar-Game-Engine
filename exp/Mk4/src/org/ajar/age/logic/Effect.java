/*
 * This file is part of Ajar Game Engine.
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
 * age
 * org.ajar.age.logic
 * Effect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import org.ajar.age.Attributes;

/**
 * TODO: Rewrite.
 * Classes that implement this interface change the <code>Attributes</code> of <code>Entities</code>, and in the process
 * change (or can change) the <code>State</code> of the entity whose attributes have been changed. 
 * @author revms
 * @since 0.0.0.153
 */
public interface Effect<A extends Attributes> {

	/**
	 * Updates the attributes of the provided <code>Entity</code>, using values derived
	 * from the <code>parameters</code> argument, and returns the resultant state.
	 * @param entity the entity whose attributes will be modified.
	 * @param parameters the <code>Attributes</code> to use to derive the magnitude of the effect.
	 * @return the <code>State</code> of the entity after the update.
	 * @since 0.0.0.207
	 */
	public State<A> perform(Entity<A> entity, A parameters);
	
	/*
	 * The <code>Action</code> that is associated with this <code>Effect</code>.
	 * @return this effect's associated action.
	public Action getAction();
	 */
	
	/*
	 * The <code>State</code> that results from the application of this <code>Effect</code>.
	 * @return the state resulting from the application of this <code>Effect</code>.
	public State<A> getResultState();
	 */
	
}
