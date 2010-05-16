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
 * org.mdmk2.core.disp2d
 * DisplayPanel.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.disp2d;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

/**
 * DisplayPanel is meant as a simple implementation of the {@link DisplaySurface} interface,
 * with methods tailored toward speedy rendering.
 * @author mstockbridge
 * 15-May-10
 */
public class DisplayPanel extends JPanel implements DisplaySurface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected BufferedImage buffer;
	protected Graphics2D bufferGraphics;
	
	public DisplayPanel(){
		super();
		
	}

	/* (non-Javadoc)
	 * This implementation is based heavily on the one from "Killer Game Programming
	 * with Java" by Andrew Davidson. Copyright 2005 O'Reilly Media, Inc., 0-596-00730-2.
	 * TODO: This doesn't take into account any insets, etc....
	 * @see org.mdmk2.core.disp2d.DisplaySurface#blitToScreen()
	 */
	public void blitToScreen() {
		if(buffer != null){
			try {
				Graphics2D g2 = (Graphics2D)this.getGraphics();
				
				//bufferGraphics.finalize();
				//bufferGraphics.dispose();
				g2.drawImage(buffer, null, 0, 0);
				Toolkit.getDefaultToolkit().sync();
				//bufferGraphics = buffer.createGraphics();
				
				g2.dispose();
			}catch(ClassCastException e){
				e.printStackTrace();
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplaySurface#drawToBuffer(java.util.List)
	 */
	public void drawToBuffer(List<? extends Displayable> nodes) {
		Graphics2D bg = getBufferedSurface();
		bg.clearRect(0, 0, getBufferWidth(), getBufferHeight());
		for(Displayable node : nodes){
			node.display(bg);
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.DisplaySurface#getBufferedSurface()
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
	 * mstockbridge
	 * 15-May-10
	 * @return	the width of the back buffer.
	 */
	public int getBufferWidth(){
		return buffer.getWidth();
	}

	/**
	 * Gets the heigth of the back buffer.
	 * mstockbridge
	 * 15-May-10
	 * @return	the height of the back buffer.
	 */
	public int getBufferHeight(){
		return buffer.getHeight();
	}
}
