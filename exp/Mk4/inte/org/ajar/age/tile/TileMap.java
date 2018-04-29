/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 4, 2015 Matthew Stockbridge
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
 * org.ajar.age.tile
 * TileMap.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.tile;

import org.ajar.age.Attributes;
import org.ajar.age.Decorator;

/**
 * @author mstockbr
 *
 */
public interface TileMap<A extends Attributes> extends Decorator<A> {

	/**
	 * Get tile at specified tile index.
	 * @param x
	 * @param y
	 * @return
	 */
	public MappedTile<A> getTile(int x, int y);
	
	/**
	 * Get tile at the arbitrary point on the screen.
	 * @param x
	 * @param y
	 * @return
	 */
	public MappedTile<A> getTileUnder(double x, double y);
	
	/**
	 * Get the number of tiles wide the map is.
	 * @return
	 */
	public int getTilesX();
	
	/**
	 * Get the number of tiles tall the map is.
	 * @return
	 */
	public int getTilesY();
	
	/**
	 * Get the number of pixels wide the map is.
	 * @return
	 */
	public int getMapWidth();
	
	/**
	 * Get the number of pixels tall the map is.
	 * @return
	 */
	public int getMapHeight();
	
	/**
	 * Get the default width of a tile.
	 * @return
	 */
	public int getTileWidth();
	
	/**
	 * Get the default height of a tile.
	 * @return
	 */
	public int getTileHeight();
	
	/**
	 * Clears the map geometry information.
	 */
	public void clearMap();
	
	/**
	 * Performs computation of the map geometry information.
	 */
	public void finalizeMap();
	
	/**
	 * Returns true if the finalization method has been called since the last update to the contents of the map.
	 * @return
	 */
	public boolean isFinalized();
}
