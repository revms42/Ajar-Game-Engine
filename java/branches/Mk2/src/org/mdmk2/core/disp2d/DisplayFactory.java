/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 15-May-10 Matthew Stockbridge
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
 * MDMk2
 * org.mdmk2.core.disp2d
 * DisplayFactory.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.disp2d;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * The DisplayFactory interface serves as a template for singleton classes that 
 * <code>Displayable</code> objects can use to display themselves. These DisplayFactories
 * are intended to be keyed to the classes they're meant to display (to ensure that the
 * factories have access to the information they intend to).
 * @author mstockbridge
 * 15-May-10
 * @param	<O>		the type of the display operations.
 * @param	<D>		the type of class that can be displayed by this DisplayFactory.
 */
public interface DisplayFactory<O,D extends Displayable> {
	
	/**
	 * Displays a displayable of type <code>D</code>.
	 * mstockbridge
	 * 15-May-10
	 * @param	displayable	the thing you want to display.
	 * @param 	g2			the graphics context that you want to display on.
	 * @param 	offset		where you want to display it.
	 * @param 	ops			any misc. operations that need to be performed (can be null).
	 */
	public void display(D displayable, Graphics2D g2, AffineTransform offset, O... ops);
}
