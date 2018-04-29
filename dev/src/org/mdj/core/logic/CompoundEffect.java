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
 * CompoundEffect.java
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
 * This class represents an effect that performs a variety of effects as part of its operation.
 * <p>
 * It wraps a series of other effects and performs them in order when called to update an entity. 
 * @author revms
 * @since 0.0.0.153
 */
public class CompoundEffect<A extends Attributes> extends AbstractEffect<A> {

	private final Effect<A>[] effects;
	
	/**
	 * Constructs a new <code>CompoundEffect</code> from the specified action, resultant state, and effects.
	 * @param a the action this effect represents.
	 * @param result the resultant state of this effect.
	 * @param effects the series of effects to perform.
	 */
	public CompoundEffect(Action a, State<A> result, Effect<A>... effects) {
		super(a, result);
		this.effects = effects;
	}

	/**
	 * Performs all the effects specified during construction in the order they were specified.
	 * @param entity the entity being updated.
	 */
	@Override
	protected void doAction(Entity<A> entity) {
		for(Effect<A> e : effects){
			e.perform(entity);
		}
	}

}
