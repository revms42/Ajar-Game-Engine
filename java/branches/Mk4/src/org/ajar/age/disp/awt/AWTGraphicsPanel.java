/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 6, 2013 Matthew Stockbridge
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
 * AWTGraphicsPanel.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.disp.awt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @author reverend
 *
 */
public class AWTGraphicsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8631775032301230722L;

	/**
	 * BufferedImage used to buffer screen updates.
	 */
	protected BufferedImage buffer;
	
	/**
	 * Graphics object onto which <code>Displayable</code> objects draw.
	 */
	protected Graphics2D bufferGraphics;
	
	public AWTGraphicsPanel(){
		super();
	}

	/* (non-Javadoc)
	 * This implementation is based heavily on the one from "Killer Game Programming
	 * with Java" by Andrew Davidson. Copyright 2005 O'Reilly Media, Inc., 0-596-00730-2.
	 * @TODO This doesn't take into account any insets, etc....
	 * @see org.mdmk2.core.disp2d.DisplaySurface2d#blitToScreen()
	 */
	public void blitToScreen() {
		if(buffer != null){
			try {
				Graphics2D g2 = (Graphics2D)this.getGraphics();
				
				g2.drawImage(buffer, null, 0, 0);
				Toolkit.getDefaultToolkit().sync();
				
				g2.dispose();
			}catch(ClassCastException e){
				e.printStackTrace();
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplaySurface2d#getBufferedSurface()
	 */
	public Graphics2D getBufferedSurface() {
		if(buffer == null){
			buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		}
		if(bufferGraphics == null ){
			bufferGraphics = buffer.createGraphics();
		}
		
		return bufferGraphics;
	}
	
	/**
	 * Gets the width of the back buffer.
	 * @return	the width of the back buffer in pixels.
	 */
	public int getBufferWidth(){
		return buffer.getWidth();
	}

	/**
	 * Gets the heigth of the back buffer.
	 * @return	the height of the back buffer in pixels.
	 */
	public int getBufferHeight(){
		return buffer.getHeight();
	}
	
	/*
	 * This is here primarily to fix problems with intermittent display.
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(buffer, null, 0, 0);
	}
}
