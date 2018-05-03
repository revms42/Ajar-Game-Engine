/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 19-Sep-10 Matthew Stockbridge
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
 * org.mdmk2.core.logic
 * AbstractEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

import org.mdmk2.core.attributed.AttributedImp;

/**
 * @author reverend
 * 19-Sep-10
 */
public abstract class AbstractEffect<A extends AttributedImp> implements Effect<A> {

	private final Action a;
	private final State<A> result;
	
	public AbstractEffect(Action a, State<A> result){
		this.a = a;
		this.result = result;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#getAction()
	 */
	public Action getAction() {
		return a;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#getResultState()
	 */
	public State<A> getResultState() {
		return result;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#perform(org.mdmk2.core.logic.StatedImp)
	 */
	public State<A> perform(StatedImp<A> state) {
		doAction(state);
		return result;
	}
	
	protected abstract void doAction(StatedImp<A> state);

}