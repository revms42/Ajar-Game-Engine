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
 * ver.ajar.age.t3.collision
 * CollisionAttribute.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t9.collision;

import java.awt.Rectangle;

import org.ajar.age.logic.SimpleAttribute;

/**
 * @author mstockbr
 *
 */
public enum CollisionAttribute implements SimpleAttribute<Rectangle> {

	BOUNDING_BOX(new Rectangle(0,0,16,16));
	
	private final Rectangle defaultRect;
	
	private CollisionAttribute(Rectangle defaultRect){
		this.defaultRect = defaultRect;
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Attribute#copy(java.lang.Object)
	 */
	@Override
	public Rectangle copy(Rectangle value) {
		return (Rectangle)value.clone();
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.SimpleAttribute#getDefaultValue()
	 */
	@Override
	public Rectangle getDefaultValue() {
		return (Rectangle)defaultRect.clone();
	}
}
