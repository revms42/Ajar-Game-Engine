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
 * org.mdmk3.sprint1.step2
 * Step9GameLoop.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step9;

import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.DisplayPanel;
import org.mdmk3.core.disp2d.GameLoop2d;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step9GameLoop extends GameLoop2d<Step9Attributes> {
	
	private DisplayPanel panel;
	/**
	 * @param displayRoot
	 */
	public Step9GameLoop(Node<Step9Attributes> displayRoot, DisplayPanel panel) {
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
