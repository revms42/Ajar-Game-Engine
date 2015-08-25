/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 13, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t3.logic
 * AbstractChainableEffect.java
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
 * @author mstockbr
 *
 */
public abstract class AbstractChainableEffect<A extends Attributes> extends AbstractEffect<A> implements ChainableEffect<A> {

	protected ChainableEffect<A> child;
	
	/**
	 * @param result
	 */
	public AbstractChainableEffect(State<A> result) {
		super(result);
	}
	
	@Override
	public State<A> perform(Entity<A> entity, A attributes){
		State<A> state = super.perform(entity,attributes);
		if(child != null){
			State<A> cState = child.perform(entity,attributes);
			if(state == null) state = cState;
		}
		return state;
	}

	@Override
	public ChainableEffect<A> addToChain(ChainableEffect<A> child){
		if(this.child == null){
			this.child = child;
		}else{
			this.child.addToChain(child);
		}
		
		return this;
	}
	
	@Override
	public boolean hasChild(){
		return child != null;
	}
	
	@Override
	public ChainableEffect<A> getChild(){
		return child;
	}
	
	@Override
	public void setChild(ChainableEffect<A> child){
		this.child = child;
	}
	
	@Override
	public ChainableEffect<A> removeLastFromChain(){
		if(this.child != null){
			if(this.child.hasChild()){
				return child.removeLastFromChain();
			}else{
				ChainableEffect<A> orphan = this.child;
				this.child = null;
				return orphan;
			}
		}else{
			return null;
		}
	}
	
	@Override
	public ChainableEffect<A> removeFromChain(ChainableEffect<A> effect){
		if(this.child != null){
			if(this.child == effect){
				ChainableEffect<A> grandchild = this.child.getChild();
				this.child = grandchild;
				return effect;
			}else{
				return this.child.removeFromChain(effect);
			}
		}else{
			return null;
		}
	}

}
