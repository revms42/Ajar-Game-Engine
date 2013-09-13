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
package ver.ajar.age.t3.logic;

import java.awt.Rectangle;

import org.ajar.age.logic.Action;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;

import ver.ajar.age.t2.VerAttribute;
import ver.ajar.age.t3.collision.CollisionAttribute;

/**
 * @author reverend
 *
 */
public class VerDefaultEffect extends ChainableEffect<HashAttributes> {

	/**
	 * @param a
	 * @param result
	 */
	public VerDefaultEffect(Action a, State<HashAttributes> result) {
		super(a, result);
		// TODO Auto-generated constructor stub
	}

	public VerDefaultEffect(State<HashAttributes> result) {
		this(null, result);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<HashAttributes> entity) {
		Number x = entity.getAttributes().getAttribute(VerAttribute.X_POS);
		Number y = entity.getAttributes().getAttribute(VerAttribute.Y_POS);
		Number dx = entity.getAttributes().getAttribute(VerAttribute.X_VEL);
		Number dy = entity.getAttributes().getAttribute(VerAttribute.Y_VEL);
		
		boolean moved = false;
		if(x != null && dx != null){
			entity.getAttributes().setAttribute(VerAttribute.X_POS, x.intValue() + dx.intValue());
			moved = true;
		}
		if(y != null && dy != null){
			entity.getAttributes().setAttribute(VerAttribute.Y_POS, y.intValue() + dy.intValue());
			moved = true;
		}
		if(moved && x != null && y != null){
			Rectangle r = entity.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX);
			r.setLocation(x.intValue(), y.intValue());
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