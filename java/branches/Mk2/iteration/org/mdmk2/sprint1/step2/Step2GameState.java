/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 11-Sep-10 Matthew Stockbridge
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
 * Step2GameState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step2;

import org.mdmk2.core.logic.Action;
import org.mdmk2.core.logic.State;
import org.mdmk2.core.logic.StatedImp;

/**
 * @author reverend
 * 11-Sep-10
 */
public class Step2GameState implements State<Step2Attributes> {

	public State<Step2Attributes> perform(StatedImp<Step2Attributes> sImp, Action e) {
		Step2Attributes subject = sImp.getAttributes();
		if(e != null && e instanceof Step2BounceAction){
			Step2BounceAction a = (Step2BounceAction)e;
			
			switch(a.type){
			case BOUNCE_V:
				subject.setYVel(-subject.getYVel());
				break;
			default:
				subject.setXVel(-subject.getXVel());
			}
		}
		
		subject.setPosition(subject.getXPos()+subject.getXVel(), subject.getYPos()+subject.getYVel());
		
		return this;
	}
}
