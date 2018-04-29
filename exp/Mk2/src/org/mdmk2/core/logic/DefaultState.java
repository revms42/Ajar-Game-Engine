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
 * DefaultState.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

import java.util.HashMap;
import java.util.Set;

import org.mdmk2.core.attributed.AttributedImp;

/**
 * @author reverend
 * 19-Sep-10
 */
public class DefaultState<A extends AttributedImp> implements State<A> {

	private final HashMap<Action,Effect<A>> effectMap;
	
	public DefaultState(){
		effectMap = new HashMap<Action,Effect<A>>();
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.State#perform(org.mdmk2.core.logic.StatedImp, org.mdmk2.core.logic.Action)
	 */
	public State<A> perform(StatedImp<A> subject, Action e) {
		return effectMap.get(e).perform(subject);
	}
	public Set<Action> getActions() {
		return effectMap.keySet();
	}
	public Effect<A> put(Effect<A> arg1) {
		return effectMap.put(arg1.getAction(), arg1);
	}
	
	public HashMap<Action,Effect<A>> getEffectMap() {
		return effectMap;
	}

}
