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
package org.mdmk2.sprint1.step2;

import java.util.List;
import java.util.Vector;

import org.mdmk2.core.logic.Action;
import org.mdmk2.core.logic.State;
import org.mdmk2.core.logic.StatedImp;

/**
 * @author reverend
 * 11-Sep-10
 */
public class Step2StatedImp implements StatedImp<Step2Attributes> {

	private Step2Attributes imp;
	private Vector<Action> actions;
	private State<Step2Attributes> state;
	/**
	 * @param imp
	 */
	public Step2StatedImp(Step2Attributes imp) {
		this.imp = imp;
		actions = new Vector<Action>();
		state = new Step2GameState();
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
	public State<Step2Attributes> getState() {
		return state;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#needsStateUpdate()
	 */
	public boolean needsStateUpdate() {
		return !actions.isEmpty();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#setState(org.mdmk2.core.logic.State)
	 */
	public void setState(State<Step2Attributes> s) {
		this.state = s;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#updateState()
	 */
	public void updateState() {
		for(Action a : actions){
			state = state.perform(this,a);
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.Attributed#getAttributes()
	 */
	public Step2Attributes getAttributes() {
		return imp;
	}

}
