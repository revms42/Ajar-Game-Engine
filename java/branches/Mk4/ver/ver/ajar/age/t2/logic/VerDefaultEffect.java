/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 11, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t2.logic
 * VerDefaultEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t2.logic;

import org.ajar.age.logic.AbstractEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;

import ver.ajar.age.t2.VerAttribute;

/**
 * @author reverend
 *
 */
public class VerDefaultEffect extends AbstractEffect<HashAttributes> {

	/**
	 * @param a
	 * @param result
	 */
	public VerDefaultEffect(State<HashAttributes> result) {
		super(result);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<HashAttributes> entity, HashAttributes attributes) {
		Number x = entity.getAttributes().getAttribute(VerAttribute.X_POS);
		Number y = entity.getAttributes().getAttribute(VerAttribute.Y_POS);
		Number dx = entity.getAttributes().getAttribute(VerAttribute.X_VEL);
		Number dy = entity.getAttributes().getAttribute(VerAttribute.Y_VEL);
		
		if(x != null && dx != null){
			entity.getAttributes().setAttribute(VerAttribute.X_POS, x.intValue() + dx.intValue());
		}
		if(y != null && dy != null){
			entity.getAttributes().setAttribute(VerAttribute.Y_POS, y.intValue() + dy.intValue());
		}
		
		Number i = entity.getAttributes().getAttribute(VerAttribute.IMAGE_X);
		
		if(i != null){
			if(i.intValue() == 15){
				entity.getAttributes().setAttribute(VerAttribute.IMAGE_X, 0);
			}else{
				entity.getAttributes().setAttribute(VerAttribute.IMAGE_X, i.intValue() + 1);
			}
		}
	}

}
