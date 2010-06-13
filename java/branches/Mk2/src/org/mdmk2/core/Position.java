/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 13-Jun-10 Matthew Stockbridge
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
 * org.mdmk2.core
 * Postion.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * @author mstockbridge
 * 13-Jun-10
 */
public class Position {

	private final static double F = 1.5d;
	private final static long CARMACK = 0x5f3759df;
	
	private double x;
	private double y;
	private double z;
	
	/**
	 * 
	 *
	 */
	public Position(){
		x = 0;
		y = 0;
		z = 0;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Position(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public double getZ() {
		return z;
	}

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param z
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y){
		setX(x);
		setY(y);
	}
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param p
	 */
	public void setPosition(Point p){
		setPosition(p.x, p.y);
	}
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public Point2D getPosition(){
		return new Point2D.Double(x,y);
	}
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param p
	 * @return
	 */
	public Position sum(Position p){
		return new Position(p.getX() + getX(), p.getY() + getY(), p.getZ() + getZ());
	}
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param p
	 * @return
	 */
	public double distance(Position p){
		double xSquare = (p.getX() - getX()) * (p.getX() - getX());
		double ySquare = (p.getY() - getY()) * (p.getY() + getY());
		
		double dSquare = xSquare + ySquare;
		
		if(p.getZ() == getZ()){
			return carmackRoot(dSquare);
		}else{
			double zSquare = (p.getZ() - getZ()) * (p.getZ() - getZ());
			return carmackRoot(dSquare + zSquare);
		}		
	}
	
	public Position clone(){
		return new Position(getX(),getY(),getZ());
	}
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param square
	 * @return
	 */
	public static double carmackRoot(double square){
		long i = Double.doubleToRawLongBits(square);
		double x = square * 0.5d;
		double y = square;
		
		i = CARMACK - (i >> 1);
		
		y = Double.longBitsToDouble(i);
		
	    y  = y * ( F - ( x * y * y ) );
	    y  = y * ( F - ( x * y * y ) );

	    return square * y;
	}
}
