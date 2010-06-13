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
 * org.mdmk2.sprint1.step3
 * Step3Sprite.java
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
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import org.mdmk2.core.disp2d.AbstractSprite2d;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * 15-May-10
 */
public abstract class Step3Sprite<E extends Step3Sprite<E>> extends AbstractSprite2d<Area,E> {
	
	private final Color color;
	private double x;
	private double y;
	protected final double maxX;
	protected final double maxY;
	
	public Step3Sprite(Area shape, Color color, double maxX, double maxY){
		super(shape);
		this.color = color;
		x = 50;
		y = 0;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#needsUpdate()
	 */
	public org.mdmk2.core.Node.UpdateType needsUpdate() {
		return org.mdmk2.core.node.DISPLAY_AND_STATUS;
	}

	public double getX() {
		return x;
	}
	public double setX(double x) {
		this.x = x;
		return x;
	}
	public double getY() {
		return y;
	}
	public double setY(double y) {
		this.y = y;
		return y;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Displayable#updateDisplay(java.awt.Graphics2D)
	 */
	public void updateDisplay(Graphics2D g2) {
		AffineTransform at = getDrawTransform();
		if(at == null) at = AffineTransform.getTranslateInstance(0, 0);
		
		Step3DisplayFactory.singleton.display(this, g2, at, color);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#isInRange(java.lang.Object)
	 */
	public boolean isInRange(Rectangle range) {
		return this.getCollisionBounds().intersects(range);
	}
	
	public Area getCollisionBounds() {
		return super.getCollisionBounds().createTransformedArea(this.getDrawTransform());
	}
}
