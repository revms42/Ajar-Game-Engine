/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Dec 15, 2010 Matthew Stockbridge
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
 * mdj
 * org.mdj.core.loader
 * ImageMapLoader.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core.loader;

import java.awt.image.BufferedImage;

import org.mdj.core.Attributes;
import org.mdj.core.Node;

/**
 * This class is an extension of <code>ImageMapLoader</code> which adds the ability to use
 * a <code>BufferedImage</code> as a map description.
 * <p>
 * Due to the format of data in a bitmap, tile data is always in integer format.
 * @author revms
 * @since 0.0.0.155
 * @see java.awt.image.BufferedImage
 */
public abstract class ImageMapLoader<A extends Attributes> extends TileMapLoader<Integer,A> {

	/**
	 * Takes a <code>BufferedImage</code> 
	 * and returns a single domain node with all of the nodes specified by the image housed within
	 * it in a 2D binary tree arrangement, by calling {@link TileMapLoader#loadFromArray(Object[], int) loadFromArray}
	 * with the integer array data obtained from calling 
	 * {@link java.awt.image.BufferedImage#getRGB(int, int, int, int, int[], int, int) getRGB} on the supplied
	 * <code>BufferedImage</code>. 
	 * @param bi a <code>BufferedImage</code> representing the map to be loaded.
	 * @return the root of the domain node binary tree representing the map.
	 * @see TileMapLoader#loadFromArray(Object[], int)
	 * @see java.awt.image.BufferedImage#getRGB(int, int, int, int, int[], int, int)
	 */
	public Node<A> loadFromImage(BufferedImage bi){
		int scansize = bi.getWidth();
		int height = bi.getHeight();
		int[] data = bi.getRGB(0, 0, scansize, height, null, 0, scansize);
		Integer[] array = new Integer[data.length];
		
		for(int i = 0; i < data.length; i++) array[i] = data[i];
		
		return loadFromArray(array, scansize);
	}
}
