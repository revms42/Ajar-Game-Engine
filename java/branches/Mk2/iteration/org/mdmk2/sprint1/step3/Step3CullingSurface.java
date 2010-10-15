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
 * org.mdmk2.sprint1.step2
 * Step2CullingSurface.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import org.mdmk2.core.cull.CullingSurface;
import org.mdmk2.core.disp2d.DisplayPanel;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbr
 * Oct 14, 2010
 */
public class Step3CullingSurface implements CullingSurface<Step3Attributes> {

	private final DisplayPanel panel;
	
	public Step3CullingSurface(DisplayPanel panel){
		this.panel = panel;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.cull.CullingSurface#isInRange(org.mdmk2.core.node.Node)
	 */
	@Override
	public boolean isInRange(Node<Step3Attributes> node) {
		return panel.getVisibleRect().contains(node.getAbsolutePosition().getPosition());
	}

	/**
	 * @return the panel
	 */
	public DisplayPanel getPanel() {
		return panel;
	}

}
