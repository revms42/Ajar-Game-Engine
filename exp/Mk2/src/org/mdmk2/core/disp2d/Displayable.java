/**
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
 * MDMk2
 * org.mdmk2.core.display
 * Displayable.java
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

import org.mdmk2.core.attributed.AttributedImp;
import org.mdmk2.core.node.NodeDecorator;

/**
 * The Displayable class provides an interface for rendering.
 * @author mstockbridge
 * May 4, 2010
 */
public interface Displayable<A extends AttributedImp> extends NodeDecorator<A> {
	
	/**
	 * Calls on this object to display itself.
	 * mstockbridge
	 * May 4, 2010
	 * @param 	g2	the {@link Graphics2D} context on which to display.
	 */
	public void updateDisplay(Graphics2D g2);
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public AffineTransform getTransform();
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public DisplayableImp<A> getDisplayableImp();
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public boolean needsDisplayUpdate();
}
