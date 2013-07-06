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
 * CachingImageTileSource.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d.image;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * This class extends <code>BufferedImageTileSource</code> by providing caching functionality.
 * <p>
 * In addition to <code>BufferedImageTileSource</code>'s functionality, this implementation 
 * maintains a cache of recently requested image tiles for rapid display. This cuts down on the
 * time that it usually takes to look up an image tile that display it on the screen by simply 
 * reusing the tiles that get asked for the most out of the cache.
 * @author revms
 * @since 0.0.0.157
 */
public abstract class CachingImageTileSource<I> extends BufferedImageTileSource<I> {

	private final Vector<I> indicies;
	private final Vector<BufferedImage> images;
	private final int cacheSize;
	
	/**
	 * Constructs a new <code>CachingImageTileSource</code> using the specified <code>BufferedImage</code> as a source 
	 * image, the specified <code>Dimension</code> as the size of a single tile, and creating a cache of the specified size.
	 * @param image the image to use as a the source of the image tiles.
	 * @param tileSize the size of a single image tile.
	 * @param cacheSize the number of spots in the cache.
	 */
	public CachingImageTileSource(BufferedImage image, Dimension tileSize, int cacheSize) {
		super(image, tileSize);
		this.cacheSize = cacheSize;
		indicies = new Vector<I>(cacheSize);
		images = new Vector<BufferedImage>(cacheSize);
	}

	@Override
	public BufferedImage getTile(I imageCode) {
		BufferedImage bi = null;
		int i = indicies.indexOf(imageCode);
		
		if(i > 0){
			bi = images.get(i);
			
			if(bi != null){
				indicies.remove(i);
				images.remove(i);
			}else{
				return null;
			}
		}else{
			bi = super.getTile(imageCode);
			
			if(bi != null){
				if(indicies.size() > cacheSize){
					indicies.remove(cacheSize - 1);
					images.remove(cacheSize - 1); 
				}
			}else{
				return null;
			}
		}
		
		indicies.insertElementAt(imageCode, 0);
		images.insertElementAt(bi, 0);
		
		return bi;
	}
}
