/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 30, 2014 Matthew Stockbridge
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
 * org.ajar.logic.loader.parser.test.type
 * DummyCondition.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser.test.type;

import org.ajar.age.logic.AbstractCondition;
import org.ajar.age.logic.Effect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;

/**
 * @author mstockbr
 *
 */
public class DummyCondition extends AbstractCondition<HashAttributes> {

	public final Effect<HashAttributes> trueEffect;
	public final Effect<HashAttributes> falseEffect;
	/**
	 * @param trueEffect
	 * @param falseEffect
	 */
	public DummyCondition(Effect<HashAttributes> trueEffect, Effect<HashAttributes> falseEffect) {
		super(trueEffect, falseEffect);
		this.trueEffect = trueEffect;
		this.falseEffect = falseEffect;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Condition#meetsCondition(org.ajar.age.logic.Entity)
	 */
	@Override
	public boolean meetsCondition(Entity<HashAttributes> entity) {
		return false;
	}

}
