/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 20, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t7.loader
 * VerAttributesLoader.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t7.loader;

import java.awt.Rectangle;

import ver.ajar.age.t0.TileAttributesFactory;
import ver.ajar.age.t7.VerAttribute;
import ver.ajar.age.t7.VerAttributes;
import ver.ajar.age.t7.collision.CollisionAttribute;

/**
 * @author mstockbr
 *
 */
public class VerAttributesLoader implements TileAttributesFactory<Integer,VerAttributes> {

	public VerAttributes getAttributes(int x, int y, Integer color){
		VerAttributes attrs = new VerAttributes();
		attrs.setSimpleAttributes(VerAttribute.values());
		attrs.setAttribute(VerAttribute.X_TILE_POS, x);
		attrs.setAttribute(VerAttribute.Y_TILE_POS, y);
		attrs.setAttribute(VerAttribute.X_DRAW_POS, x*VerAttributes.TILE_SIZE);
		attrs.setAttribute(VerAttribute.Y_DRAW_POS, y*VerAttributes.TILE_SIZE);
		int tileZ = color.intValue() & 0x000000FF;
		int tileY = (color.intValue() & 0x0000FF00) >> 8;
		int tileX = (color.intValue() & 0X00FF0000) >> 16;
		attrs.setAttribute(VerAttribute.IMAGE_X, tileX);
		attrs.setAttribute(VerAttribute.IMAGE_Y, tileY);
		attrs.setAttribute(VerAttribute.TEAM, tileZ);
		Rectangle r = new Rectangle(x,y,VerAttributes.TILE_SIZE,VerAttributes.TILE_SIZE);
		attrs.setAttribute(CollisionAttribute.BOUNDING_BOX, r);
		return attrs;
		
	}
}
