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
 * org.mdmk3.sprint1.step1
 * Step1Attributes.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step1;

import java.awt.Color;
import java.awt.Shape;

import org.mdmk3.core.Attributes;

/**
 * @author mstockbridge
 * Sep 9, 2010
 */
public class Step1Attributes implements Attributes {

	private final Shape shape;
	private final Color c;
	
	public Step1Attributes(Shape shape, Color c){
		this.shape = shape;
		this.c = c;
	}

	public Shape getShape() {
		return shape;
	}

	public Color getC() {
		return c;
	}
	
	@Override
	public Step1Attributes clone() {
		return new Step1Attributes(shape,c);
	}
}
