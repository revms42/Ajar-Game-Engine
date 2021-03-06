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
 * org.mdmk2.sprint1.step2
 * Step2DisplayImp.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.mdmk2.core.disp2d.DisplayableImp;

/**
 * @author mstockbridge
 * Sep 10, 2010
 */
public class Step3DisplayImp implements DisplayableImp<Step3Attributes> {

	private Step3Attributes a;
	
	public Step3DisplayImp(Step3Attributes a){
		this.a = a;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplayableImp#updateDisplay(java.awt.Graphics2D)
	 */
	@Override
	public void updateDisplay(Graphics2D g2) {
		Step3DisplayFactory.singleton.display(this, g2, getTransform(), a.getColor());
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplayableImp#getTransform()
	 */
	@Override
	public AffineTransform getTransform() {
		return a.getTransform();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplayableImp#needsDisplayUpdate()
	 */
	@Override
	public boolean needsDisplayUpdate() {
		return true;
	}
	
	public Shape getShape(){
		return a.getShape();
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.Attributed#getAttributes()
	 */
	public Step3Attributes getAttributes() {
		return a;
	}
}
