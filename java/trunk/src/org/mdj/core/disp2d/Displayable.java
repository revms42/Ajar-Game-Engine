/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) May 4, 2010 Matthew Stockbridge
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
 * org.mdj.core.disp2d
 * Displayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core.disp2d;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.mdj.core.Attributes;
import org.mdj.core.Decorator;

/**
 * This interface defines classes used to display <code>Node</code> objects on a screen.
 * @see org.mdj.core.GameLoop#render()
 * @see org.mdj.core.GameLoop#getDisplayableClass()
 * @author revms
 * @since 0.0.0.153
 */
public interface Displayable<A extends Attributes> extends Decorator<A> {
	
	/**
	 * Calls on this object to display the node that it's decorating.
	 * @param 	g2	the {@link java.awt.Graphics2D} context on which to display.
	 */
	public void updateDisplay(Graphics2D g2);
	
	/**
	 * Gets the {@link java.awt.geom.AffineTransform} for displaying the node this <code>Displayable</code> object decorates.
	 * @return	{@link java.awt.geom.AffineTransform} representing where to draw this
	 * <code>Displabable</code>. 
	 */
	public AffineTransform getTransform();
	
	/**
	 * Returns <code>true</code> if the node this Displayable decorates needs to be updated.
	 * @return	<code>true</code> if this <code>Displayable</code> needs to be displayed.
	 */
	public boolean needsDisplayUpdate();
}
