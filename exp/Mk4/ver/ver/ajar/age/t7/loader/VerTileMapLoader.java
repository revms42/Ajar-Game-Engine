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
 * ver.ajar.age.t6
 * VerTileMapLoader.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t7.loader;

import java.awt.Rectangle;

import org.ajar.age.DefaultNode;
import org.ajar.age.Node;

import ver.ajar.age.t0.AbstractTileMapLoader;
import ver.ajar.age.t7.TileMapNode;
import ver.ajar.age.t7.VerAttribute;
import ver.ajar.age.t7.VerAttributes;
import ver.ajar.age.t7.VerMapAttribute;
import ver.ajar.age.t7.collision.CollisionAttribute;
import ver.ajar.age.t7.display.VerDisplayable;

/**
 * @author mstockbr
 *
 */
public class VerTileMapLoader extends AbstractTileMapLoader<Integer,VerAttributes>{

	public VerTileMapLoader(){
		this.setTileAttributesFactory(new VerAttributesLoader());
	}
	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.TileMapLoader#createMapRoot(int, int)
	 */
	@Override
	public Node<VerAttributes> createMapRoot(int w, int h) {
		VerAttributes attrs = new VerAttributes();
		attrs.setAttribute(VerMapAttribute.WIDTH, w);
		attrs.setAttribute(VerMapAttribute.HEIGHT, h);
		attrs.setSimpleAttributes(VerAttribute.values());
		attrs.setAttribute(VerAttribute.IMAGE_X, -1);
		attrs.setAttribute(VerAttribute.IMAGE_Y, -1);
		Rectangle r = new Rectangle(0,0,w*VerAttributes.TILE_SIZE,h*VerAttributes.TILE_SIZE);
		attrs.setAttribute(CollisionAttribute.BOUNDING_BOX, r);
		return new VerDisplayable(new TileMapNode(attrs));
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.TileMapLoader#createTile(org.ajar.age.Attributes)
	 */
	@Override
	public Node<VerAttributes> createTile(VerAttributes attrs) {
		return new VerDisplayable(new DefaultNode<VerAttributes>(attrs));
	}
}
