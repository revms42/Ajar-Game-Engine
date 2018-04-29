/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 10, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t3
 * VerState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t7.logic;

import java.util.HashMap;

import org.ajar.age.logic.Action;
import org.ajar.age.logic.Effect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.Event;
import org.ajar.age.logic.State;

import ver.ajar.age.t7.VerAttributes;

/**
 * This is a first stab at having a more simplified logic structure.
 * The idea here is that you can create a state that only declares certain
 * action+effect pairings, and then hands off the other actions to a parent state.
 * 
 * There is no loop checking, so infinite loops are possible.
 * 
 * There is still no good solution for creating the states to begin with (working on that).
 * 
 * @author mstockbr
 *
 */
public class VerState implements State<VerAttributes> {
	
	private final HashMap<Action, Effect<VerAttributes>> map;
	private final State<VerAttributes> parentState;
	
	public VerState(){
		this.map = new HashMap<Action,Effect<VerAttributes>>();
		parentState = null;
	}
	
	public VerState(State<VerAttributes> parentState){
		this.map = new HashMap<Action,Effect<VerAttributes>>();
		this.parentState = parentState;
	}
	
	public void setAction(Action e, Effect<VerAttributes> effect){
		map.put(e, effect);
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.State#perform(org.ajar.age.logic.Entity, org.ajar.age.logic.Action)
	 */
	@Override
	public State<VerAttributes> perform(Entity<VerAttributes> subject, Event<VerAttributes> e) {
		State<VerAttributes> result = null;
		if(map.containsKey(e)){
			Effect<VerAttributes> ae = map.get(e);
			result = ae.perform(subject,null);
		}else if(parentState != null){
			result = parentState.perform(subject, e);
		}
		
		return result == null ? this : result;
	}

}
