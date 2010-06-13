/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 31-May-10 Matthew Stockbridge
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
 * org.mdmk2.sprint1.step3
 * Step3Obstacle.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;

import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * 31-May-10
 */
public class Step3Obstacle extends Step3Sprite<Step3Obstacle> {
	
	public Step3Obstacle(Area shape, Color color, double x, double y){
		super(shape,color,x,y);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.AbstractSprite2d#thisAsE()
	 */
	@Override
	protected Step3Obstacle thisAsE() {
		return this;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#needsUpdate()
	 */
	public org.mdmk2.core.Node.UpdateType needsUpdate() {
		return org.mdmk2.core.node.DISPLAY_ONLY;
	}

	public void updateDisplay(Graphics2D g2){
		this.getDrawTransform().setToTranslation(getX(), getY());
		super.updateDisplay(g2);
	}
}
