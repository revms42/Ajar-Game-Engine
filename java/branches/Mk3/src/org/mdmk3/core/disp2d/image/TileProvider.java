package org.mdmk3.core.disp2d.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public interface TileProvider<I> {
	/**
	 * Draws a tile, specified by the image code, directly to the g2 Graphics2D object with the transform provided.
	 * @param imageCode
	 * @param x
	 * @param y
	 * @param g2
	 */
	public void drawImage(I imageCode, Graphics2D g2, AffineTransform at, BufferedImageOp op);
	
	/**
	 * Returns the tile specified by the image code.
	 * <p>
	 * This is intended to be used, primarily, for instances where a specific image would be cached and used constantly 
	 * (such as with landscapes) because it would be faster than doing it another way.
	 * @param imageCode
	 * @return
	 */
	public BufferedImage getTile(I imageCode);
}
