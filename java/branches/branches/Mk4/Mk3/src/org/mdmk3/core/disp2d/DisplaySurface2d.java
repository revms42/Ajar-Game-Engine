/*
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
 * MDMk3
 * org.mdmk3.core.disp2d
 * DisplaySurface2d.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d;

import java.awt.Graphics2D;
import java.util.List;

/**
 * DisplaySurface2d provides an interface for surfaces that will make display
 * updates.
 * @author revms
 * @since 0.0.0.153
 */
public interface DisplaySurface2d {

	/**
	 * Returns the {@link Graphics2D} display context of the buffered surface for the
	 * component used for display.
	 * @return	the underlying {@link Graphics2D} display context.
	 */
	public Graphics2D getBufferedSurface();
	
	/**
	 * Calls on this DisplaySurface2d to render the {@link java.util.List} of nodes. Typically,
	 * this will be called from an implementation of {@link org.mdmk3.core.GameLoop}.
	 * @param	nodes		the list of {@link org.mdmk3.core.Node}s to be displayed.
	 * @see	org.mdmk3.core.Node
	 * @see	org.mdmk3.core.disp2d.Displayable
	 * @see	org.mdmk3.core.GameLoop#render()
	 */
	public void drawToBuffer(List<? extends Displayable<?>> nodes);
	
	/**
	 * Called after {@link #drawToBuffer(List)} to instruct this display
	 * surface that the display update is complete and the buffer is ready to be blitted
	 * to the screen.
	 */
	public void blitToScreen();
}
