/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Sep 10, 2010 Matthew Stockbridge
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
 * Step1DisplayImp.java
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

import org.mdmk2.core.disp2d.DisplayableImp;

/**
 * @author mstockbridge
 * Sep 10, 2010
 */
public class Step1DisplayImp implements DisplayableImp {

	private final Shape s;
	private final Color c;
	private AffineTransform at;
	
	public Step1DisplayImp(Shape s, Color c){
		this.s = s;
		this.c = c;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplayableImp#updateDisplay(java.awt.Graphics2D)
	 */
	@Override
	public void updateDisplay(Graphics2D g2) {
		Step1DisplayFactory.singleton.display(this, g2, getTransform(), c);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplayableImp#getTransform()
	 */
	@Override
	public AffineTransform getTransform() {
		if(at == null) at = AffineTransform.getTranslateInstance(0, 0);
		return at;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplayableImp#needsDisplayUpdate()
	 */
	@Override
	public boolean needsDisplayUpdate() {
		return true;
	}
	
	public Shape getShape(){
		return s;
	}
}
