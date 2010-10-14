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
 * org.mdmk2.sprint1.step1
 * Step1GameLoop.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step1;

import org.mdmk2.core.disp2d.DisplayPanel;
import org.mdmk2.core.disp2d.GameLoop2d;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step1GameLoop extends GameLoop2d<Step1Attributes> {
	
	private DisplayPanel panel;
	/**
	 * @param displayRoot
	 */
	public Step1GameLoop(Node<Step1Attributes> displayRoot, DisplayPanel panel) {
		super(displayRoot,panel);
		this.setPanel(panel);
	}
	/**
	 * @param panel the panel to set
	 */
	public void setPanel(DisplayPanel panel) {
		this.panel = panel;
	}
	/**
	 * @return the panel
	 */
	public DisplayPanel getPanel() {
		return panel;
	}
}
