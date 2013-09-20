/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 23, 2013 Matthew Stockbridge
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
 * AGE
 * ver.ajar.age.t2
 * VerDisplayVisitor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t7.display;

import java.awt.image.BufferedImage;

import org.ajar.age.disp.awt.AWTDisplayable;
import org.ajar.age.disp.awt.AWTGraphicsPanel;
import org.ajar.age.disp.awt.AWTGraphicsVisitor;

import ver.ajar.age.t7.VerAttribute;
import ver.ajar.age.t7.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerDisplayVisitor extends AWTGraphicsVisitor<VerAttributes> {

	/**
	 * @param surface
	 * @param dispClass
	 */
	public VerDisplayVisitor(AWTGraphicsPanel comp) {
		super(comp,VerDisplayable.class);
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.awt.AWTGraphicsVisitor#update(org.ajar.age.disp.awt.AWTDisplayable)
	 */
	@Override
	public void update(AWTDisplayable<VerAttributes> disp) {
		VerDisplayable vd = disp.getDecorator(VerDisplayable.class);
		BufferedImage bi = vd.drawImage();
		
		if(bi != null){
			int x = vd.getAttributes().getAttribute(VerAttribute.X_DRAW_POS).intValue();
			int y = vd.getAttributes().getAttribute(VerAttribute.Y_DRAW_POS).intValue();
			this.getSurface().drawImage(bi, null, x, y);
		}
	}

}
