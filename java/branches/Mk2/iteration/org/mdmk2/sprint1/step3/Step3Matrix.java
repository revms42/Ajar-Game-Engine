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
 * Step3Matrix.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import org.mdmk2.core.logic.EntityState;
import org.mdmk2.core.logic.StateMatrix;
import org.mdmk2.core.logic.StateWrapperNode;

/**
 * @author mstockbridge
 * 30-May-10
 */
public class Step3Matrix extends StateMatrix<Step3Mover> {

	private static Step3MovingState move;
	
	public Step3Matrix(StateWrapperNode wrapper){
		super();
		move = new Step3MovingState(wrapper);
		Step3BounceState bounce = new Step3BounceState(wrapper);
		
		Step3MoveMap mmap = new Step3MoveMap(move);
		mmap.setStateChange(Step3SpriteStates.MOVE, move);
		mmap.setStateChange(Step3SpriteStates.BOUNCE_X, bounce);
		mmap.setStateChange(Step3SpriteStates.BOUNCE_Y, bounce);
		
		Step3BounceMap bmap = new Step3BounceMap(bounce);
		bmap.setStateChange(Step3SpriteStates.MOVE, move);
		bmap.setStateChange(Step3SpriteStates.BOUNCE_X, move);
		bmap.setStateChange(Step3SpriteStates.BOUNCE_Y, move);
		
		this.put(mmap);
		this.put(bmap);
	}

	/* (non-Javadoc)
	 * @see org.mdmk3.core.logic.StateMatrix#defaultState()
	 */
	@Override
	public EntityState<Step3Mover> defaultState() {
		return move;
	}
}
