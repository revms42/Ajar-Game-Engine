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
 * Step3Mover.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import java.awt.Color;
import java.awt.geom.Area;

import org.mdmk2.core.logic.EntityState;

/**
 * @author mstockbridge
 * 31-May-10
 */
public class Step3Mover extends Step3Sprite<Step3Mover> {
	private double dx;
	private double dy;
	
	public Step3Mover(Area shape, Color color, double x, double y){
		super(shape,color,x,y);
	}
	
	public void updateStatus(){
		super.updateStatus();
		this.getTransform().setToTranslation(getX(), getY());
	}
	
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	
	@Override
	public void setState(EntityState<Step3Mover> s){
		super.setState(s);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.AbstractSprite2d#thisAsE()
	 */
	@Override
	protected Step3Mover thisAsE() {
		return this;
	}
}
