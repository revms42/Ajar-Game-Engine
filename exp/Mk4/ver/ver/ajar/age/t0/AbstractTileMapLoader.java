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
 * AbstractTileMapLoader.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t0;

import org.ajar.age.Attributes;
import org.ajar.age.Node;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractTileMapLoader<O, A extends Attributes> implements TileMapLoader<O, A> {

	protected TileAttributesFactory<O,A> factory;
	
	/* (non-Javadoc)
	 * @see ver.ajar.age.t7.loader.TileMapLoader#loadTileMap(int, O[])
	 */
	@Override
	public Node<A> loadTileMap(int scanSize, O[] values) {
		Node<A> root = createMapRoot(scanSize,(values.length/scanSize));
		for(int y = 0; y < (values.length/scanSize); y++){
			for(int x = 0; x < scanSize; x++){
				O value = values[(y*scanSize)+x];
				root.addChild( 
						createTile(getTileAttributesFactory().getAttributes(x, y, value))
				);
			}
		}
		return root;
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t7.loader.TileMapLoader#setTileAttributesFactory(ver.ajar.age.t7.loader.TileAttributesFactory)
	 */
	@Override
	public void setTileAttributesFactory(TileAttributesFactory<O, A> factory) {
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t7.loader.TileMapLoader#getTileAttributesFactory()
	 */
	@Override
	public TileAttributesFactory<O, A> getTileAttributesFactory() {
		return factory;
	}

}
