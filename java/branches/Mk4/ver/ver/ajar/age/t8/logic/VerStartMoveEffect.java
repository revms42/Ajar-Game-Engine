/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 16, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t4.logic
 * VerAccEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t8.logic;

import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;

import ver.ajar.age.t0.ChainableEffect;
import ver.ajar.age.t8.VerAttribute;
import ver.ajar.age.t8.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerStartMoveEffect implements ChainableEffect<VerAttributes> {
	
	private ChainableEffect<VerAttributes> child;
	private final State<VerAttributes> success;
	private final State<VerAttributes> failure;
	/**
	 * @param a
	 * @param result
	 */
	public VerStartMoveEffect(State<VerAttributes> success, State<VerAttributes> failure) {
		this.success = success;
		this.failure = failure;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Effect#perform(org.ajar.age.logic.Entity)
	 */
	@Override
	public State<VerAttributes> perform(Entity<VerAttributes> entity) {
		Number destX = entity.getAttributes().getAttribute(VerAttribute.X_TILE_DEST);
		Number destY = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_DEST);
		Number range = entity.getAttributes().getAttribute(VerAttribute.RANGE);
		
		if(destX != null && destY != null && range != null){
			int tileX = Math.abs(destX.intValue());
			int tileY = Math.abs(destY.intValue());
			int max = range.intValue();
			
			if(tileX + tileY > max){
				int xtile = entity.getAttributes().getAttribute(VerAttribute.X_TILE_POS).intValue();;
				int ytile = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_POS).intValue();;
				entity.getAttributes().setAttribute(VerAttribute.X_TILE_DEST, xtile);
				entity.getAttributes().setAttribute(VerAttribute.Y_TILE_DEST, ytile);
				
				return failure;
			}else{
				child.perform(entity);
				return success;
			}
		}else{
			return failure;
		}
	}

	public ChainableEffect<VerAttributes> addToChain(ChainableEffect<VerAttributes> child){
		if(this.child == null){
			this.child = child;
		}else{
			this.child.addToChain(child);
		}
		
		return child;
	}
	
	public boolean hasChild(){
		return child != null;
	}
	
	public ChainableEffect<VerAttributes> getChild(){
		return child;
	}
	
	public void setChild(ChainableEffect<VerAttributes> child){
		this.child = child;
	}
	
	public ChainableEffect<VerAttributes> removeLastFromChain(){
		if(this.child != null){
			if(this.child.hasChild()){
				return child.removeLastFromChain();
			}else{
				ChainableEffect<VerAttributes> orphan = this.child;
				this.child = null;
				return orphan;
			}
		}else{
			return null;
		}
	}
	
	public ChainableEffect<VerAttributes> removeFromChain(ChainableEffect<VerAttributes> effect){
		if(this.child != null){
			if(this.child == effect){
				ChainableEffect<VerAttributes> grandchild = this.child.getChild();
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
