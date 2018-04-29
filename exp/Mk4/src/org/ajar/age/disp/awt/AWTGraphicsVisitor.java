/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 26, 2013 Matthew Stockbridge
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
 * org.ajar.age.disp.awt
 * AWTGraphicsVisitor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */

/**
 * @author mstockbr
 */
package org.ajar.age.disp.awt;

import java.awt.Color;
import java.awt.Graphics2D;

import org.ajar.age.AbstractVisitor;
import org.ajar.age.Attributes;

/**
 * @author mstockbr
 *
 */

public abstract class AWTGraphicsVisitor<A extends Attributes> extends AbstractVisitor<A,AWTDisplayable<A>> {
	
	private AWTGraphicsPanel surface;
	
	public AWTGraphicsVisitor(AWTGraphicsPanel surface, Class<? extends AWTDisplayable<A>> dispClass){
		super(dispClass);
		this.surface = surface;
	}
	
	protected boolean isInView(AWTDisplayable<A> node) {
		return node.onScreen(surface);
	}
	
	public AWTGraphicsPanel getPanel(){
		return surface;
	}
	
	public void setPanel(AWTGraphicsPanel panel){
		this.surface = panel;
	}
	
	public Graphics2D getSurface(){
		return surface.getBufferedSurface();
	}
	
	public abstract void update(AWTDisplayable<A> disp);
	
	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#startProcess()
	 */
	@Override
	protected void startProcess() {
		Graphics2D bufferGraphics = surface.getBufferedSurface();
		bufferGraphics.setColor(Color.BLUE);
		bufferGraphics.setBackground(Color.BLACK);
		bufferGraphics.clearRect(0, 0, surface.getBufferWidth(), surface.getBufferHeight());
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#finishProcess()
	 */
	@Override
	protected void finishProcess() {
		surface.blitToScreen();
	}

}
