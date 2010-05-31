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
 * Step2Matrix.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step2;

import org.mdmk2.core.logic.EntityState;
import org.mdmk2.core.logic.StateMatrix;
import org.mdmk2.core.logic.StateWrapperNode;

/**
 * @author mstockbridge
 * 30-May-10
 */
public class Step2Matrix extends StateMatrix<Step2Sprite> {

	private static Step2MovingState move;
	
	public Step2Matrix(StateWrapperNode wrapper){
		super();
		move = new Step2MovingState(wrapper);
		Step2BounceState bounce = new Step2BounceState(wrapper);
		
		Step2MoveMap mmap = new Step2MoveMap(move);
		mmap.setStateChange(Step2SpriteStates.MOVE, move);
		mmap.setStateChange(Step2SpriteStates.BOUNCE_X, bounce);
		mmap.setStateChange(Step2SpriteStates.BOUNCE_Y, bounce);
		
		Step2BounceMap bmap = new Step2BounceMap(bounce);
		bmap.setStateChange(Step2SpriteStates.MOVE, move);
		bmap.setStateChange(Step2SpriteStates.BOUNCE_X, move);
		bmap.setStateChange(Step2SpriteStates.BOUNCE_Y, move);
		
		this.put(mmap);
		this.put(bmap);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StateMatrix#defaultState()
	 */
	@Override
	public EntityState<Step2Sprite> defaultState() {
		return move;
	}
}
