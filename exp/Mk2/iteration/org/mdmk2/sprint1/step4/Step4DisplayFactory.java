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
 * org.mdmk2.sprint1.step2
 * Step2DisplayFactory.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//import org.mdmk2.core.disp2d.DisplayFactory;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step4DisplayFactory {
	
	public static final Step4DisplayFactory singleton;
	
	static {
		singleton = new Step4DisplayFactory();
	}

	private Step4DisplayFactory(){};
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplayFactory#display(org.mdmk2.core.disp2d.Displayable, java.awt.Graphics2D, java.awt.geom.AffineTransform, O[])
	 */
	public void display(Step4DisplayImp displayable, Graphics2D g2, AffineTransform offset, Color... ops) {
		Color foreground = g2.getColor();
		if(ops != null && ops.length > 0) g2.setColor(ops[0]);
		g2.fill(offset.createTransformedShape(displayable.getShape()));
		g2.setColor(foreground);
	}

}
