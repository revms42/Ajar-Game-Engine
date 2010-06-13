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
 * DisplaySurface.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.disp2d;

import java.awt.Graphics2D;
import java.util.List;

import org.mdmk2.core.node.Node;

/**
 * DisplaySurface provides an interface for surfaces that will make display
 * updates.
 * @author mstockbridge
 * 15-May-10
 */
public interface DisplaySurface {

	/**
	 * Returns the {@link Graphics2D} display context of the buffered surface for the
	 * component used for display.
	 * mstockbridge
	 * 15-May-10
	 * @return	the underlying {@link Graphics2D} display context.
	 */
	public Graphics2D getBufferedSurface();
	
	/**
	 * Calls on this DisplaySurface to render the {@link List} of nodes. Typically,
	 * this will be called from an implementation of {@link GameLoop}.
	 * mstockbridge
	 * 15-May-10
	 * @param	nodes		the list of {@link Node}s to be displayed.
	 * @see	{@link Node}
	 * @see	{@link Displayable}
	 * @see	{@link GameLoop.render(Node)}
	 */
	public void drawToBuffer(List<? extends Displayable> nodes);
	
	/**
	 * Called after {@link DisplaySurface.drawToBuffer(List)} to instruct this display
	 * surface that the display update is complete and the buffer is ready to be blitted
	 * to the screen.
	 * mstockbridge
	 * 15-May-10
	 */
	public void blitToScreen();
}
