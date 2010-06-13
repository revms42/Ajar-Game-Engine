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
 * Step1Sprite.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.mdmk2.core.disp2d.DefaultSprite;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step1Sprite extends DefaultSprite {
	
	private final Color color;
	
	public Step1Sprite(Shape shape, Color color){
		super();
		this.color = color;
		this.setBoundingSurface(shape);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#needsUpdate()
	 */
	public org.mdmk2.core.Node.UpdateType needsUpdate() {
		return org.mdmk2.core.node.DISPLAY_ONLY;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Displayable#updateDisplay(java.awt.Graphics2D)
	 */
	public void updateDisplay(Graphics2D g2) {
		AffineTransform at = this.getDrawTransform();
		if(at == null) at = AffineTransform.getTranslateInstance(0, 0);
		
		Step1DisplayFactory.singleton.display(this, g2, at, color);
	}

}
