/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 23, 2013 Matthew Stockbridge
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
 * VerAttribute.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t3;

import org.ajar.age.logic.SimpleAttribute;

/**
 * @author reverend
 *
 */
public enum VerAttribute implements SimpleAttribute<Number> {
	X_VEL(0),
	Y_VEL(0),
	IMAGE_X(11),
	IMAGE_Y(14),
	TEAM(0);
	
	private final Number def;
	
	private VerAttribute(Number n){
		def = n;
	}
	
	@Override
	public Number copy(Number value) {
		int v = value.intValue();
		return new Integer(v);
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.SimpleAttribute#getDefaultValue()
	 */
	@Override
	public Number getDefaultValue() {
		int v = def.intValue();
		return new Integer(v);
	}
}
