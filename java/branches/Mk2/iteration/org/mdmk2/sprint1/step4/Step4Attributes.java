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
 * org.mdmk2.sprint1.step2
 * Step2Attributes.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import org.mdmk2.core.attributed.AttributedImp;

/**
 * @author mstockbridge
 * Sep 9, 2010
 */
public class Step4Attributes implements AttributedImp {

	private int xVel;
	private int yVel;
	private final Shape shape;
	private final Line2D[] testers;
	private final Color color;
	private AffineTransform at;
	
	public Step4Attributes(Shape shape, Color color){
		this.shape = shape;
		this.color = color;
		
		int width = shape.getBounds().width;
		int height = shape.getBounds().height;
		
		testers = new Line2D[]{
			new Line2D.Double(0,height/2,width,height/2),
			new Line2D.Double(width/2,0,width/2,height)
		};
		
		xVel = 2;
		yVel = 2;
		at = AffineTransform.getTranslateInstance(0.0d, 0.0d);
	}

	public int getXVel() {
		return xVel;
	}

	public int getYVel() {
		return yVel;
	}

	public void setXVel(int xVel) {
		if(xVel > 10) xVel = 10;
		this.xVel = xVel;
	}

	public void setYVel(int yVel) {
		if(yVel > 10) yVel = 10;
		this.yVel = yVel;
	}

	public Color getColor() {
		return color;
	}

	public Shape getShape() {
		return AffineTransform.getTranslateInstance(1, 1).createTransformedShape(shape);
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
	
	public Shape getHTester(){
		return at.createTransformedShape(testers[0]);
	}
	
	public Shape getVTester(){
		return at.createTransformedShape(testers[1]);
	}
	
	public Shape getCollisionSurface() {
		return at.createTransformedShape(shape.getBounds());
	}
}
