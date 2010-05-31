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
 * Step3MovingState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import org.mdmk2.core.logic.EntityState;
import org.mdmk2.core.logic.StateWrapperNode;

/**
 * @author mstockbridge
 * 30-May-10
 */
public class Step3MovingState implements EntityState<Step3Mover> {
	
	private final StateWrapperNode wrapper;
	
	public Step3MovingState(StateWrapperNode wrapper){
		this.wrapper = wrapper;
	}
	/* (non-Javadoc)
	 * @see org.mdmk3.core.logic.EntityState#apply(org.mdmk3.core.logic.Entity)
	 */
	public void apply(Step3Mover e) {
		double x = e.setX(e.getX() + e.getDx());
		double y = e.setY(e.getY() + e.getDy());
		
		if(x >= e.maxX || x <= 0){
			wrapper.add(Step3SpriteStates.BOUNCE_X);
		}
		if(y >= e.maxY || y <= 0){
			wrapper.add(Step3SpriteStates.BOUNCE_Y);
		}
	}

}
