/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 13, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t3.logic
 * VerBounceEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t4.logic;

import org.ajar.age.logic.AbstractEffect;
import org.ajar.age.logic.Attribute;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;

/**
 * @author mstockbr
 *
 */
public class VerBounceEffect extends AbstractEffect<HashAttributes> {

	private final Attribute<Number>[] attributes;
	/**
	 * @param a
	 * @param result
	 */
	public VerBounceEffect(State<HashAttributes> result, Attribute<Number>... attributes) {
		super(result);
		this.attributes = attributes;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<HashAttributes> entity) {
		for(Attribute<Number> attribute : attributes){
			Number n = entity.getAttributes().getAttribute(attribute);
			
			if(n != null && n.intValue() != 0){
				entity.getAttributes().setAttribute(attribute, -n.intValue());
			}
		}
	}

}
