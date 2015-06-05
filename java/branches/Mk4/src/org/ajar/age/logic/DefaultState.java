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
 * DefaultState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import java.util.HashMap;
import java.util.Set;

import org.ajar.age.Attributes;

/**
 * TODO: Rewrite
 * This class is a default implementation of the <code>State</code> interface. It provides the intended functionality of
 * <code>State</code> as well as having some convenience methods for dealing with mapping actions to effects.
 * @author revms
 * @since 0.0.0.153
 */
public class DefaultState<A extends Attributes> implements State<A> {

	private final HashMap<Action,Effect<A>> effectMap;
	
	/**
	 * Constructs a <code>DefaultState</code> backed by an empty <code>Hashmap</code>.
	 */
	public DefaultState(){
		effectMap = new HashMap<Action,Effect<A>>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public State<A> perform(Entity<A> subject, Event<A> e) {
		Effect<A> eff = null;
		if(e == null){
			eff = effectMap.get(null);
		}else{
			eff = effectMap.get(e.getAction());
		}
		
		if(eff != null){
			if(e == null){
				return eff.perform(subject, null);
			}else{
				return eff.perform(subject, e.getAttributes());
			}
		}else{
			return null;
		}
	}
	
	/**
	 * Returns the <code>Set</code> of actions currently handled by this state.
	 * @return the key set of the wrapped <code>HashMap</code>.
	 */
	public Set<Action> getActions() {
		return effectMap.keySet();
	}
	
	/**
	 * Returns the <code>HashMap</code> that contains all the Action<->Effect mappings.
	 * @return the wrapped <code>HashMap</code>.
	 */
	public HashMap<Action,Effect<A>> getEffectMap() {
		return effectMap;
	}

	/**
	 * Inserts a new <code>Effect</code> into this <code>State</code>, using the effect's associated action.
	 * <p>
	 * Returns any previously mapped effect.
	 * @param a the action to map to the effect.
	 * @param arg1 the effect to add to this state.
	 * @return any effect previously mapped to the new effect's action.
	 */
	public Effect<A> put(Action a, Effect<A> arg1) {
		return effectMap.put(a, arg1);
	}
}
