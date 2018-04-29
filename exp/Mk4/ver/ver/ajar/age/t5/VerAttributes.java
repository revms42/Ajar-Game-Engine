/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 19, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t5
 * VerAttributes.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t5;

import java.awt.Rectangle;

import org.ajar.age.logic.Attribute;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.SimpleAttribute;

import ver.ajar.age.t5.collision.CollisionAttribute;

/**
 * @author mstockbr
 *
 */
public class VerAttributes extends HashAttributes {
	
	public static int coordinateToTile(int coordinate){
		return (int)Math.floor(coordinate/16.0d);
	}
	
	public static int tileToCoordinate(int tile){
		return tile*16;
	}
	
	@SuppressWarnings("unchecked")
	public <V> V getAttribute(Attribute<V> attribute){
		if(		attribute instanceof CollisionAttribute && 
				super.attributeMap.containsKey(VerAttribute.X_TILE_POS)
		){
			Rectangle r = super.getAttribute((CollisionAttribute)attribute);
			int xpos = super.getAttribute(VerAttribute.X_TILE_POS).intValue();
			int xoff = super.getAttribute(VerAttribute.X_MOVE_OFFSET).intValue();
			int ypos = super.getAttribute(VerAttribute.Y_TILE_POS).intValue();
			int yoff = super.getAttribute(VerAttribute.Y_MOVE_OFFSET).intValue();
			r.setLocation(xpos+xoff, ypos+yoff);
			return (V)r;
		}else{
			return (V)attributeMap.get(attribute);
		}
	}
	
	public <V> void setAttribute(Attribute<V> attribute, V value){
		attributeMap.put((Attribute<?>) attribute, value);
	}
	
	public <V> void setAttribute(SimpleAttribute<V> attribute){
		attributeMap.put((Attribute<?>) attribute, attribute.getDefaultValue());
	}
	
	public <V> void setSimpleAttributes(SimpleAttribute<V>... attributes){
		for(SimpleAttribute<V> attr : attributes){
			setAttribute(attr);
		}
	}
}
