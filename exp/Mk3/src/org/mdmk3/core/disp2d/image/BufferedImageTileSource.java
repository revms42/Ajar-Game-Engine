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
 * BufferedImageTileSource.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d.image;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 * This class is a complete implementation of <code>TileSource</code> backed by a single <code>BufferedImage</code>.
 * <p>
 * To facilitate flexibility it introduces one abstract method {@link #tileIndexForCode(Object)}, which returns an 
 * x and y index for a tile in this <code>TileSource</code> for the given image index.
 * @author revms
 * @since 0.0.0.157
 */
public abstract class BufferedImageTileSource<I> implements TileSource<I> {
	private final BufferedImage image;
	private final Dimension tileSize;
	
	/**
	 * Constructs a new <code>BufferedImageTileSource</code> using the specified <code>BufferedImage</code> as a source 
	 * image, and the specified <code>Dimension</code> as the size of a single tile.
	 * @param image the image to use as a the source of the image tiles.
	 * @param tileSize the size of a single image tile.
	 */
	public BufferedImageTileSource(BufferedImage image, Dimension tileSize){
		this.image = image;
		this.tileSize = tileSize;
	}
	
	@Override
	public void drawImage(I imageCode, Graphics2D g2, AffineTransform at, BufferedImageOp op) {
		if(op != null){
			g2.drawRenderedImage(op.filter(getTile(imageCode),null), at);
		}else{
			g2.drawRenderedImage(getTile(imageCode), at);
		}
		
	}

	@Override
	public BufferedImage getTile(I imageCode) {
		Point p = tileIndexForCode(imageCode);
		return image.getSubimage(p.x*tileWidth(), p.y*tileHeight(), tileWidth(), tileHeight());
	}
	
	/**
	 * Generates an x and y index of a tile on this <code>TileSource</code> for the given image code.
	 * @param imageCode the image code to find a tile for
	 * @return the location of that tile in this <code>TileSource</code>
	 */
	protected abstract Point tileIndexForCode(I imageCode);

	@Override
	public int tileWidth() {
		return tileSize.width;
	}

	@Override
	public int tileHeight() {
		return tileSize.height;
	}

	@Override
	public int numXTiles() {
		return image.getWidth() / tileSize.width;
	}

	@Override
	public int numYTiles() {
		return image.getHeight() / tileSize.height;
	}

}
