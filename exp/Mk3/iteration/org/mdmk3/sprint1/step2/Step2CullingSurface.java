/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Oct 14, 2010 Matthew Stockbridge
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
 * org.mdmk3.sprint1.step2
 * Step5CullingSurface.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step2;

import java.awt.Dimension;

import org.mdmk3.core.Node;
import org.mdmk3.core.cull.CullingSurface;
import org.mdmk3.core.disp2d.DisplayPanel;

/**
 * @author mstockbr
 * Oct 14, 2010
 */
public class Step2CullingSurface implements CullingSurface<Step2Attributes> {

	private final DisplayPanel panel;
	
	public Step2CullingSurface(DisplayPanel panel){
		this.panel = panel;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.cull.CullingSurface#isInRange(org.mdmk2.core.node.Node)
	 */
	@Override
	public boolean isInRange(Node<Step2Attributes> node) {
		double x = node.getAttributes().getXPos();
		double y = node.getAttributes().getYPos();
		Dimension d = panel.getSize();
		return x >= 0 && y >=0 && x <= d.width && y <= d.height;
	}

	/**
	 * @return the panel
	 */
	public DisplayPanel getPanel() {
		return panel;
	}

}
