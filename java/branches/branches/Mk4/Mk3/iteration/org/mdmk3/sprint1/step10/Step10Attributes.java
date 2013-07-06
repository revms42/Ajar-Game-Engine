/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Sep 9, 2010 Matthew Stockbridge
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
 * Step10Attributes.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step10;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Attributes;

/**
 * @author mstockbridge
 * Sep 9, 2010
 */
public class Step10Attributes implements Attributes {

	private Step10ObjectType type;

	private Integer currentFrame;
	
	private int xVel;
	private int yVel;
	private Shape shape;
	private Rectangle2D bounds;
	private boolean isBlue;

	private AffineTransform at;
	
	public Step10Attributes(Shape shape, Step10ObjectType type){
		this.type = type;
		this.shape = shape;

		at = AffineTransform.getTranslateInstance(0.0d, 0.0d);
		isBlue = false;
	}

	public int getXVel() {
		return xVel;
	}

	public int getYVel() {
		return yVel;
	}

	public void setXVel(int xVel) {
		if(xVel > 2){
			xVel = 2;
		}else if(xVel < -2){
			xVel = -2;
		}
		this.xVel = xVel;
	}

	public void setYVel(int yVel) {
		if(yVel > 2){
			yVel = 2;
		}else if(yVel < -2){
			yVel = -2;
		}
		this.yVel = yVel;
	}

	public Shape getShape() {
		return AffineTransform.getTranslateInstance(0, 0).createTransformedShape(shape);
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public double getXPos(){
		return at.getTranslateX();
	}
	
	public double getYPos(){
		return at.getTranslateY();
	}
	
	public void setPosition(double x, double y){
		at.setToTranslation(x, y);
	}
	
	public AffineTransform getTransform(){
		return at;
	}
	
	public Shape getCollisionSurface() {
		return at.createTransformedShape(getShape());
	}
	
	public Step10ObjectType getType() {
		return type;
	}
	
	public void setType(Step10ObjectType type) {
		this.type = type;
	}
	
	/**
	 * @param currentFrame the currentFrame to set
	 */
	public void setCurrentFrame(Integer currentFrame) {
		this.currentFrame = currentFrame;
	}

	/**
	 * @return the currentFrame
	 */
	public Integer getCurrentFrame() {
		return currentFrame;
	}

	public Rectangle2D getBounds() {
		if(bounds == null){
			return getShape().getBounds();
		}else{
			return bounds;
		}
	}

	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
	}

	public boolean isBlue() {
		return isBlue;
	}

	public void setBlue(boolean isBlue) {
		this.isBlue = isBlue;
	}

	@Override
	public Step10Attributes clone() {
		Step10Attributes clone = new Step10Attributes(shape,type);
		clone.setCurrentFrame(currentFrame);
		clone.setBlue(isBlue());
		return clone;
	}
}
