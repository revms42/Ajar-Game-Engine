/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 5, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t1
 * VerDisplayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t1;

import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JComponent;

import org.ajar.age.Node;
import org.ajar.age.disp.awt.AbstractAWTDisplayable;

/**
 * @author reverend
 *
 */
public class VerDisplayable extends AbstractAWTDisplayable<VerAttributes> {

	/**
	 * @param node
	 */
	public VerDisplayable(Node<VerAttributes> node) {
		super(node);
		node.getAttributes().setDisplay(true);
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.Displayable#onScreen(java.lang.Object)
	 */
	@Override
	public boolean onScreen(JComponent screen) {
		return this.getAttributes().isDisplay();
	}

	public Shape drawShape(){
		return new Rectangle(this.getAttributes().xPos(),this.getAttributes().yPos(),10,10);
	}
}
