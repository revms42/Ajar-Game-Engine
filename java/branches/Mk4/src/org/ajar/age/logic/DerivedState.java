/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Dec 13, 2013 Matthew Stockbridge
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
 * org.ajar.age.logic
 * DerivedState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import org.ajar.age.Attributes;

/**
 * TODO: This deserves more documentation. Basically copied from the
 * t9 VerState.
 * @author reverend
 *
 */
public class DerivedState<A extends Attributes> extends DefaultState<A> {
	protected final State<A> parentState;
	
	public DerivedState(){
		super();
		parentState = null;
	}
	
	public DerivedState(State<A> parentState){
		super();
		this.parentState = parentState;
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.State#perform(org.ajar.age.logic.Entity, org.ajar.age.logic.Action)
	 */
	@Override
	public State<A> perform(Entity<A> subject, Event<A> e) {
		State<A> result = null;
		if(this.getEffectMap().containsKey(e)){
			//Effect<A> ae = this.getEffectMap().get(e);
			result = super.perform(subject, e);
		}else if(parentState != null){
			result = parentState.perform(subject, e);
		}
		//TODO: Changing this logic to be in DefaultEntity.
		//return result == null ? this : result;
		return result;
	}
}
