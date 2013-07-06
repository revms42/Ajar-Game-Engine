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
 * TileProvider.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 * Classes that implement this interface are providers of 2D images; drawing a requested image (defined by the
 * image code) at the specified location on a <code>Graphics2D</code> object.
 * @author revms
 * @since 0.0.0.157
 * @param <I> class of the image codes
 */
public interface TileProvider<I> {
	/**
	 * Draws a tile, specified by the image code, directly to the g2 Graphics2D object with the transform provided.
	 * @param imageCode a code specifying which image should be displayed.
	 * @param g2 the <code>Graphics2D</code> on which to display the image.
	 * @param at the <code>AffineTransform</code> indicating where the image should be placed.
	 * @param op an optional <code>BufferedImageOp</code> to perform on the image when it is being displayed.
	 */
	public void drawImage(I imageCode, Graphics2D g2, AffineTransform at, BufferedImageOp op);
	
	/**
	 * Returns the tile specified by the image code.
	 * <p>
	 * This is intended to be used, primarily, for instances where a specific image would be cached and used constantly 
	 * (such as with landscapes) because it would be faster than doing it another way.
	 * @param imageCode a code specifying which image should be displayed.
	 * @return the image specified by the code.
	 */
	public BufferedImage getTile(I imageCode);
}
