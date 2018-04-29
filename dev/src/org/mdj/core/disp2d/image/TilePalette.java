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
 * mdj
 * org.mdj.core.disp2d.image
 * TilePalette.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core.disp2d.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is aggregates a number of different <code>TileProviders</code> into a larger source of image tiles.
 * <p>
 * The utility of this class comes from the ability to use one source of images for large sections of a game (e.g. for
 * all enemies, or all terrain, or even for all images in the game).
 * @author revms
 * @since 0.0.0.157
 */
public abstract class TilePalette<I> implements TileProvider<I> {

	private final HashMap<I,TileProvider<I>> palette;
	
	/**
	 * Constructs a new empty <code>TilePalette</code>.
	 */
	public TilePalette(){
		palette = new HashMap<I,TileProvider<I>>();
	}
	
	@Override
	public void drawImage(I imageCode, Graphics2D g2, AffineTransform at, BufferedImageOp op) {
		getSourceFromCode(imageCode).drawImage(imageCode, g2, at, op);
	}

	@Override
	public BufferedImage getTile(I imageCode) {
		return getSourceFromCode(imageCode).getTile(imageCode);
	}
	
	/**
	 * Uses the specified image code to determine which tile source to use.
	 * @param imageCode the image code of the image to be displayed.
	 * @return an image code representing the tile source of the requested image.
	 */
	public abstract I getSourceCodeFromImageCode(I imageCode);
	
	/**
	 * Gets the <code>TileProvider</code> for the image specified by the provided image code
	 * by calling <code>getSourceCodeFromImageCode</code> and using the return to retrieve it
	 * from the map of <code>TileProvider</code>s.
	 * @param imageCode the image code of the image to be displayed.
	 * @return the <code>TileProvider</code> that contains the image specified by the image code.
	 */
	public TileProvider<I> getSourceFromCode(I imageCode) {
		return palette.get(getSourceCodeFromImageCode(imageCode));
	}

	/**
	 * Determines if this <code>TilePalette</code> contains the appropriate <code>TileProvier</code> to
	 * display the image specified by the given image code.
	 * @param key the image code of the requested image.
	 * @return <code>true</code> if the necessary <code>TileProvider</code> is present.
	 */
	public boolean canDraw(I key) {
		return palette.containsKey(getSourceCodeFromImageCode(key));
	}

	/**
	 * Determines if this <code>TilePalette</code> contains the specified <code>TileSource</code>.
	 * @param value the <code>TileSource</code> in question.
	 * @return <code>true</code> if the specified <code>TileSource</code> is present.
	 */
	public boolean containsSource(TileSource<I> value) {
		return palette.containsValue(value);
	}

	/**
	 * Returns <code>true</code> if this <code>TilePalette</code> contains no <code>TileProvider</code>s.
	 * @return <code>true</code> if this <code>TilePalette</code> is empty.
	 */
	public boolean isEmpty() {
		return palette.isEmpty();
	}

	/**
	 * Returns a {@link java.util.Set} view of the keys contained in this <code>TilePalette</code>.
	 * @return a set view of the keys contained in this <code>TilePalette</code>.
	 */
	public Set<I> keySet() {
		return palette.keySet();
	}

	/**
	 * Adds the specified <code>TileSource</code> to this <code>TilePalette</code> and associates it with
	 * the source key provided.
	 * @param key a key representing the <code>TileSource</code>.
	 * @param value the <code>TileSource</code> being added.
	 * @return the <code>TileSource</code> previously associated with the given key, or <code>null</code> if there was
	 * no mapping.
	 */
	public TileProvider<I> addSource(I key, TileSource<I> value) {
		return palette.put(key, value);
	}

	/**
	 * Adds all the provided <code>TileSource</code>s to this <code>TilePalette</code> and associates them with their 
	 * mapped source keys.
	 * @param m the map of sources and keys to add to this <code>TilePalette</code>.
	 */
	public void addSourceMap(Map<? extends I, ? extends TileSource<I>> m) {
		palette.putAll(m);
	}

	/**
	 * Removes the <code>TileSource</code> specified by the given key and returns it.
	 * @param key the key of the <code>TileSource</code> to remove.
	 * @return the requested <code>TileSource</code>.
	 */
	public TileProvider<I> removeSource(I key) {
		return palette.remove(getSourceCodeFromImageCode(key));
	}

	/**
	 * Returns the number of <code>TileSource</code>s in this <code>TilePalette</code>.
	 * @return the size of this <code>TilePalette</code>.
	 */
	public int size() {
		return palette.size();
	}
	
	/**
	 * Returns a {@link java.util.Collection} of the <code>TileSource</code>s in this <code>TilePalette</code>.
	 * @return a collection of this <code>TilePalette</code>'s <code>TileSources</code>.
	 */
	public Collection<TileProvider<I>> sources() {
		return palette.values();
	}
}
