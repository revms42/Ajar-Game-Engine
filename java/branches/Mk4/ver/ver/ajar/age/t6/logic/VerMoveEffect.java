/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 16, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t4.logic
 * VerAccEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t6.logic;

import org.ajar.age.logic.AbstractEffect;
import org.ajar.age.logic.Action;
import org.ajar.age.logic.Attribute;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;

import ver.ajar.age.t6.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerMoveEffect extends AbstractEffect<VerAttributes> {

	
	private final Attribute<Number> attr;
	private final int mod;
	private final int limit;
	/**
	 * @param a
	 * @param result
	 */
	public VerMoveEffect(Action a, Attribute<Number> attr, int mod, int limit, State<VerAttributes> result) {
		super(a, result);
		this.attr = attr;
		this.mod = mod;
		this.limit = limit;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<VerAttributes> entity) {
		Number n = entity.getAttributes().getAttribute(attr);
		
		if(n != null){
			int newVal = n.intValue() + mod;
			
			if(newVal > limit){
				newVal = limit;
			}else if(newVal < 0){
				newVal = 0;
			}
			
			entity.getAttributes().setAttribute(attr, newVal);
		}
	}

}
