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
package org.mdmk2.sprint1.step2;

import java.awt.Rectangle;
import java.util.Vector;

import org.mdmk2.core.GameLoop;
import org.mdmk2.core.Node;
import org.mdmk2.core.disp2d.AbstractDisplayable;
import org.mdmk2.core.disp2d.DisplayPanel;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step2GameLoop extends GameLoop<Rectangle> {

	private Vector<AbstractDisplayable> forDraw;
	private final DisplayPanel panel;
	/**
	 * @param displayRoot
	 */
	public Step2GameLoop(Node<Rectangle> displayRoot, DisplayPanel panel) {
		super(displayRoot);
		this.panel = panel;
		forDraw = new Vector<AbstractDisplayable>();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#getRange()
	 */
	@Override
	public Rectangle getRange() {
		return panel.getVisibleRect();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#logic()
	 */
	@Override
	public void logic() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#render()
	 */
	@Override
	public void render() {
		forDraw.removeAllElements();
		for(Node<Rectangle> r : this.needsUpdate){
			if(r instanceof AbstractDisplayable){
				forDraw.add((AbstractDisplayable)r);
			}
		}
		panel.drawToBuffer(forDraw);
		panel.blitToScreen();
	}

}
