/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Jan 25, 2011 Matthew Stockbridge
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
 * MDMk3
 * org.mdmk3.core.disp2d.image
 * TileSource.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d.image;

/**
 * This interface operates under the assumption that the underlying implementation
 * will be using a large image made up of uniformly sized image "tiles" as a display source.
 * <p>
 * It extends <code>TileProvider</code> by providing methods to query the size of the tiles as well as how many are present
 * in the x and y directions.
 * @author revms
 * @since 0.0.0.157
 */
public interface TileSource<I> extends TileProvider<I>{
	
	/**
	 * The width of a tile on this TileSource.
	 * @return the width of a single tile in pixels.
	 */
	public int tileWidth();
	
	/**
	 * The height of a tile on this TileSource.
	 * @return the height of a single tile in pixels.
	 */
	public int tileHeight();
	
	/**
	 * The number of tiles in the X direction for this TileSource.
	 * @return the number of tiles present in the x direction.
	 */
	public int numXTiles();
	
	/**
	 * The number of tiles in the Y direction for this TileSource.
	 * @return the number of tiles present in the y direction.
	 */
	public int numYTiles();
}
