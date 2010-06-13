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
 * org.mdmk2.sprint1.step2
 * Step2BounceState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step2;

import org.mdmk2.core.logic.State;
import org.mdmk2.core.logic.StateWrapperNode;

/**
 * @author reverend
 * 30-May-10
 */
public class Step2BounceState implements State<Step2Sprite> {
	
	private final StateWrapperNode wrapper;
	
	public Step2BounceState(StateWrapperNode wrapper){
		this.wrapper = wrapper;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.EntityState#apply(org.mdmk2.core.logic.Entity)
	 */
	public void apply(Step2Sprite e) {
		if(wrapper.allChanges().contains(Step2SpriteStates.BOUNCE_X)){
			e.setDx(-e.getDx());
		}
		if(wrapper.allChanges().contains(Step2SpriteStates.BOUNCE_Y)){
			e.setDy(-e.getDy());
		}
		wrapper.add(Step2SpriteStates.MOVE);
	}

}
