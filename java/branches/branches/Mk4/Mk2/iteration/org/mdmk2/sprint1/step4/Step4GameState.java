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
package org.mdmk2.sprint1.step4;

import org.mdmk2.core.logic.Action;
import org.mdmk2.core.logic.State;
import org.mdmk2.core.logic.StatedImp;

/**
 * @author reverend
 * 11-Sep-10
 */
public enum Step4GameState implements State<Step4Attributes> {

	RICOCHET_V{
		public State<Step4Attributes> getNextState(StatedImp<Step4Attributes> sImp, Step4Action e){
			Step4Attributes subject = sImp.getAttributes();
			switch(e.type){
			case BOUNCE_D:
				subject.setYVel(-subject.getYVel());
				subject.setXVel(-subject.getXVel());
				
				decrimentX(subject);
				
				decrimentY(subject);
				return RICOCHET_D;
			case BOUNCE_H:
				subject.setXVel(-subject.getXVel());
				
				decrimentX(subject);
				return RICOCHET_D;
			case KEY_UP:
				if(subject.getYVel() < 0){
					subject.setYVel(subject.getYVel() - 1);
				}
				return RICOCHET_V;
			case KEY_DOWN:
				if(subject.getYVel() > 0){
					subject.setYVel(subject.getYVel() + 1);
				}
				return RICOCHET_V;
			case KEY_LEFT:
				subject.setXVel(subject.getXVel() - 1);
				return RICOCHET_V;
			case KEY_RIGHT:
				subject.setXVel(subject.getXVel() + 1);
				return RICOCHET_V;
			default:
				return RICOCHET_V;
			}
		}
	},
	RICOCHET_H{
		public State<Step4Attributes> getNextState(StatedImp<Step4Attributes> sImp, Step4Action e){
			Step4Attributes subject = sImp.getAttributes();
			switch(e.type){
			case BOUNCE_D:
				subject.setYVel(-subject.getYVel());
				subject.setXVel(-subject.getXVel());
				
				decrimentX(subject);
				
				decrimentY(subject);
				return RICOCHET_D;
			case BOUNCE_V:
				subject.setYVel(-subject.getYVel());
				
				decrimentY(subject);
				return RICOCHET_D;
			case KEY_UP:
				subject.setYVel(subject.getYVel() - 1);
				return RICOCHET_H;
			case KEY_DOWN:
				subject.setYVel(subject.getYVel() + 1);
				return RICOCHET_H;
			case KEY_LEFT:
				if(subject.getXVel() < 0){
					subject.setXVel(subject.getXVel() - 1);
				}
				return RICOCHET_H;
			case KEY_RIGHT:
				if(subject.getXVel() > 0){
					subject.setXVel(subject.getXVel() + 1);
				}
				return RICOCHET_H;
			default:
				return RICOCHET_H;
			}
		}
	},
	RICOCHET_D{
		public State<Step4Attributes> getNextState(StatedImp<Step4Attributes> sImp, Step4Action e){
			return RICOCHET_D;
		}
	},
	NORMAL{
		public State<Step4Attributes> getNextState(StatedImp<Step4Attributes> sImp, Step4Action e){
			Step4Attributes subject = sImp.getAttributes();
			switch(e.type){
			case BOUNCE_D:
				subject.setYVel(-subject.getYVel());
				subject.setXVel(-subject.getXVel());
				
				decrimentX(subject);
				
				decrimentY(subject);
				return RICOCHET_D;
			case BOUNCE_V:
				subject.setYVel(-subject.getYVel());
				
				decrimentY(subject);
				return RICOCHET_V;
			case BOUNCE_H:
				subject.setXVel(-subject.getXVel());
				
				decrimentX(subject);
				return RICOCHET_H;
			case KEY_UP:
				subject.setYVel(subject.getYVel() - 1);
				return NORMAL;
			case KEY_DOWN:
				subject.setYVel(subject.getYVel() + 1);
				return NORMAL;
			case KEY_LEFT:
				subject.setXVel(subject.getXVel() - 1);
				return NORMAL;
			case KEY_RIGHT:
				subject.setXVel(subject.getXVel() + 1);
				return NORMAL;
			default:
				return NORMAL;
			}
		}
	};
	
	public State<Step4Attributes> perform(StatedImp<Step4Attributes> sImp, Action e) {
		Step4Attributes subject = sImp.getAttributes();
		if(e != null || e instanceof Step4Action){
			Step4Action a = (Step4Action)e;
			State<Step4Attributes> nextState = getNextState(sImp,a);
			
			double newX = subject.getXPos() + a.xBump;
			double newY = subject.getYPos() + a.yBump;
			
			subject.setPosition(newX, newY);
			
			if(sImp.getActions().indexOf(e) == (sImp.getActions().size() - 1)){
				subject.setPosition(subject.getXPos()+subject.getXVel(), subject.getYPos()+subject.getYVel());
				return NORMAL;
			}else{
				subject.setPosition(subject.getXPos()+subject.getXVel(), subject.getYPos()+subject.getYVel());
				return nextState;
			}
		}else{
			subject.setPosition(subject.getXPos()+subject.getXVel(), subject.getYPos()+subject.getYVel());
			return this;
		}
	}

	/**
	 * mstockbr
	 * Oct 21, 2010
	 * @param subject
	 */
	protected void decrimentY(Step4Attributes subject) {
		if(subject.getYVel() > 0){
			subject.setYVel(subject.getYVel() - 1);
		}else if(subject.getYVel() < 0){
			subject.setYVel(subject.getYVel() + 1);
		}
	}

	/**
	 * mstockbr
	 * Oct 21, 2010
	 * @param subject
	 */
	protected void decrimentX(Step4Attributes subject) {
		if(subject.getXVel() > 0){
			subject.setXVel(subject.getXVel() - 1);
		}else if(subject.getXVel() < 0){
			subject.setXVel(subject.getXVel() + 1);
		}
	}
	
	protected abstract State<Step4Attributes> getNextState(StatedImp<Step4Attributes> sImp, Step4Action e);
}
