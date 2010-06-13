/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 30-May-10 Matthew Stockbridge
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
 * MDMk2
 * org.mdmk2.sprint1.step3
 * Step3BounceState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import org.mdmk2.core.logic.State;
import org.mdmk2.core.logic.StateWrapperNode;

/**
 * @author mstockbridge
 * 30-May-10
 */
public class Step3BounceState implements State<Step3Mover> {
	
	private final StateWrapperNode wrapper;
	
	public Step3BounceState(StateWrapperNode wrapper){
		this.wrapper = wrapper;
	}
	/* (non-Javadoc)
	 * @see org.mdmk3.core.logic.EntityState#apply(org.mdmk3.core.logic.Entity)
	 */
	public void apply(Step3Mover e) {
		if(wrapper.allChanges().contains(Step3SpriteStates.BOUNCE_X)){
			e.setDx(-e.getDx());
		}
		if(wrapper.allChanges().contains(Step3SpriteStates.BOUNCE_Y)){
			e.setDy(-e.getDy());
		}
		wrapper.add(Step3SpriteStates.MOVE);
	}

}
