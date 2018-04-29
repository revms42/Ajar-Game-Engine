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
package ver.ajar.age.t4.logic;

import org.ajar.age.Node;
import org.ajar.age.logic.DefaultEntity;
import org.ajar.age.logic.DefaultState;
import org.ajar.age.logic.HashAttributes;

import ver.ajar.age.t4.VerAttribute;

/**
 * @author reverend
 *
 */
public class VerEntity extends DefaultEntity<HashAttributes> {

	/**
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	public VerEntity(Node<HashAttributes> node) {
		super(node);
		
		DefaultState<HashAttributes> state = new DefaultState<HashAttributes>();
		state.put(null,new VerDefaultEffect(state));
		state.put(VerAction.BOUNCE_V,new VerBounceEffect(state,VerAttribute.Y_VEL));
		state.put(VerAction.BOUNCE_H,new VerBounceEffect(state,VerAttribute.X_VEL));
		state.put(VerAction.BOUNCE_D,new VerBounceEffect(state,VerAttribute.X_VEL,VerAttribute.Y_VEL));
		
		state.put(VerAction.ACC_X_NEG,new VerAccEffect(VerAttribute.X_VEL,-1,-10,state));
		state.put(VerAction.ACC_X_POS,new VerAccEffect(VerAttribute.X_VEL,1,10,state));
		state.put(VerAction.ACC_Y_NEG,new VerAccEffect(VerAttribute.Y_VEL,-1,-10,state));
		state.put(VerAction.ACC_Y_POS,new VerAccEffect(VerAttribute.Y_VEL,1,10,state));
		
		this.setState(state);
	}
}
