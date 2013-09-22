/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 8, 2013 Matthew Stockbridge
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
 * AGE
 * ver.ajar.age.t2
 * VerEntity.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t8.logic;

import org.ajar.age.Node;
import org.ajar.age.logic.DefaultEntity;
import org.ajar.age.logic.DefaultState;

import ver.ajar.age.t8.VerAttribute;
import ver.ajar.age.t8.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerEntity extends DefaultEntity<VerAttributes> {

	/**
	 * @param node
	 */
	public VerEntity(Node<VerAttributes> node) {
		super(node);
		
		//Base state
		DefaultState<VerAttributes> baseState = new DefaultState<VerAttributes>();
		baseState.put(null, new VerDefaultEffect(baseState));
		
		//Wait state.
		VerState waitState = new VerState(baseState);
		
		//End Move State.
		VerState endState = new VerState(baseState);
		VerSetRangeEffect setRange = new VerSetRangeEffect(waitState,1);
		setRange.addToChain(new VerDefaultEffect(waitState));
		endState.put(null, setRange);
		
		//Move state.
		VerState moveState = new VerState(baseState);
		VerCheckArrivedEffect arrivedCheck = new VerCheckArrivedEffect(endState,moveState);
		moveState.put(null, new VerDefaultEffect(moveState));
		
		//Start Move State.
		VerState startState = new VerState(baseState);
		VerStartMoveEffect startMove = new VerStartMoveEffect(moveState,waitState);
		startMove.addToChain(new VerSetRangeEffect(moveState,0));
		startState.put(VerAction.START_MOVE,startMove);
		

		
		this.setState(waitState);
	}
}
