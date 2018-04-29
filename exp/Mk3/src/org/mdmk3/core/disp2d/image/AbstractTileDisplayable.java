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
 * AbstractTileDisplayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImageOp;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.AbstractDisplayable;

/**
 * This class extends <code>AbstractDisplayable</code> by backing it with a <code>TileProvider</code>.
 * @author revms
 * @since 0.0.0.157
 * @param <I> class of the image codes
 */
public abstract class AbstractTileDisplayable<I,A extends Attributes> extends AbstractDisplayable<A> {

	protected TileProvider<I> provider;
	
	/**
	 * Constructs an AbstractTileDisplayable using the supplied node as the node the AbstractTileDisplayable will decorate.
	 * @param node the node the new AbstractTileDisplayable will decorate.
	 */
	public AbstractTileDisplayable(Node<A> node) {
		super(node);
	}

	@Override
	public void updateDisplay(Graphics2D g2) {
		provider.drawImage(getImageCode(), g2, getTransform(), getFilter());
	}
	
	/**
	 * Gets the image code representing the current image that should be used to update the decorated node.
	 * @return an image code representing the image to display.
	 */
	public abstract I getImageCode();
	
	/**
	 * Gets any <code>BufferedImageOp</code> that should be used when displaying the decorated node's image.
	 * @return a <code>BufferedImageOp</code> to be applied, or <code>null</code>.
	 */
	public abstract BufferedImageOp getFilter();

	/**
	 * Gets the <code>TileProvider</code> currently being used by this decorator to display the node it's decorating.
	 * @return this <code>Displayable</code>'s <code>TileProvider</code>.
	 */
	public TileProvider<I> getProvider() {
		return provider;
	}

	/**
	 * Sets the <code>TileProvider</code> for this <code>Displayable</code> to the provided <code>TileProvider</code>.
	 * @param provider the new <code>TileProvider</code> for this <code>Displayable</code>.
	 */
	public void setProvider(TileProvider<I> provider) {
		this.provider = provider;
	}
}
