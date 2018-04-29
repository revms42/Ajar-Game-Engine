/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 24, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t9
 * VerRefDecorator.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t9;

import org.ajar.age.DefaultDecorator;
import org.ajar.age.Node;
import org.ajar.age.logic.Entity;

import ver.ajar.age.t9.logic.VerEntity;

/**
 * @author mstockbr
 *
 */
public class VerRefDecorator extends DefaultDecorator<VerAttributes> {

	/**
	 * @param node
	 */
	public VerRefDecorator(Node<VerAttributes> node) {
		super(node);
	}

	public Entity<VerAttributes> getCurrentPlayer(){
		int team = getAttributes().getAttribute(VerMapAttribute.CURRENT_PLAYER).intValue();
		
		for(Node<VerAttributes> sibling : getChildren()){
			Number playerTeam = sibling.getAttributes().getAttribute(VerAttribute.TEAM);
			
			if(playerTeam != null && playerTeam.intValue() == team){
				return sibling.getDecorator(VerEntity.class);
			}
		}
		
		return null;
	}
	
	public void nextPlayer(){
		int team = getAttributes().getAttribute(VerMapAttribute.CURRENT_PLAYER).intValue();
		
		if(team == 1){
			getAttributes().setAttribute(VerMapAttribute.CURRENT_PLAYER,2);
		}else{
			getAttributes().setAttribute(VerMapAttribute.CURRENT_PLAYER,1);
		}
	}
}
