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
 * State.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import org.ajar.age.Attributes;

/**
 * TODO: Rewrite
 * Classes that implement this interface provide mappings from one state to another, given 
 * an <code>Event</code> input.
 * <p>
 * Typically, a single state will map a variety of actions to a variety of new states by 
 * performing an <code>Effect</code>, determined by the input action, on an <code>Entity</code> and then
 * taking the resultant state from the effect that was performed. 
 * @author revms
 * @since 0.0.0.153
 * @see Effect
 * @see Effect#perform(Entity, Attributes)
 * @see Entity
 */
public interface State<A extends Attributes> {

	/**
	 * Performs a given <code>Effect</code> on the provided <code>Entity</code> by selecting the effect
	 * associated by the provided <code>Event</code>.
	 * @param subject the entity on which the effect will be performed
	 * @param e the event indicating the effect to perform
	 * @return the resultant <code>State</code> specified by the <code>Effect</code> that was performed.
	 * @see Effect#perform(Entity, Attributes)
	 */
	public State<A> perform(Entity<A> subject, Event<A> e);
}
