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
 * Step2StatedImp.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import java.util.List;
import java.util.Vector;

import org.mdmk2.core.logic.Action;
import org.mdmk2.core.logic.State;
import org.mdmk2.core.logic.StatedImp;

/**
 * @author reverend
 * 11-Sep-10
 */
public class Step3StatedImp implements StatedImp<Step3Attributes> {

	private Step3Attributes imp;
	private Vector<Action> actions;
	private State<Step3Attributes> state;
	/**
	 * @param imp
	 */
	public Step3StatedImp(Step3Attributes imp) {
		this.imp = imp;
		actions = new Vector<Action>();
		state = new Step3GameState();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#addAction(org.mdmk2.core.logic.Action)
	 */
	public void addAction(Action action) {
		actions.add(action);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#getActions()
	 */
	public List<Action> getActions() {
		return actions;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#getState()
	 */
	public State<Step3Attributes> getState() {
		return state;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#needsStateUpdate()
	 */
	public boolean needsStateUpdate() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#setState(org.mdmk2.core.logic.State)
	 */
	public void setState(State<Step3Attributes> s) {
		this.state = s;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#updateState()
	 */
	public void updateState() {
		if(actions.size() > 0){
			for(Action a : actions){
				state = state.perform(this,a);
			}
			actions.removeAllElements();
		}else{
			state.perform(this, null);
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.Attributed#getAttributes()
	 */
	public Step3Attributes getAttributes() {
		return imp;
	}

}
