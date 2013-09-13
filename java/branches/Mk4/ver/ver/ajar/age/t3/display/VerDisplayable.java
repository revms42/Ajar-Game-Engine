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
 * VerDisplayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t3.display;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.ajar.age.Node;
import org.ajar.age.disp.awt.AbstractAWTDisplayable;
import org.ajar.age.logic.HashAttributes;

import ver.ajar.age.t3.VerAttribute;
import ver.ajar.age.t3.collision.CollisionAttribute;

/**
 * @author reverend
 *
 */
public class VerDisplayable extends AbstractAWTDisplayable<HashAttributes> {

	private final static String imageLoc = "/home/reverend/.minecraft/debug.stitched_terrain.png";
	
	private static BufferedImage staticImage;
	
	//Note: In more advanced versions we could either preload all of this or have a hashmap.
	private BufferedImage previousImage;
	private int previousImageX;
	private int previousImageY;
	
	static {
		try {
			staticImage = ImageIO.read(new File(imageLoc));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//private final Rectangle rect;
	//private boolean initialized = false;
	/**
	 * @param node
	 */
	public VerDisplayable(Node<HashAttributes> node) {
		super(node);
		//rect = new Rectangle();
		/*if(node.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX) != null){
			setPosition(node);
			setSize(node);
			initialized = true;
		}*/
	}
/*
	private void setPosition(Node<HashAttributes> node){
		int x = node.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).x;
		int y = node.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).y;
		
		rect.setLocation(x, y);
	}
	
	private void setSize(Node<HashAttributes> node){
		int w = node.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).width;
		int h = node.getAttributes().getAttribute(VerAttribute.HEIGHT).intValue();
		
		rect.setSize(w, h);
	}*/
	
	/* (non-Javadoc)
	 * @see org.ajar.age.disp.Displayable#onScreen(java.lang.Object)
	 */
	@Override
	public boolean onScreen(JComponent screen) {
		/*if(!initialized && node.getAttributes().getAttribute(VerAttribute.X_POS) != null){
			setPosition(node);
			setSize(node);
			initialized = true;
		}
		if(initialized){
			setPosition(this.node);
			return screen.getBounds().intersects(rect);
		}else{
			return false;
		}*/
		return node.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).intersects(screen.getBounds());
	}

	public BufferedImage drawImage(){
		Number ix = this.getAttributes().getAttribute(VerAttribute.IMAGE_X);
		Number iy = this.getAttributes().getAttribute(VerAttribute.IMAGE_Y);
		int iw = this.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).width;
		int ih = this.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).height;
		
		if(ix != null && iy != null){
			if(ix.intValue() != previousImageX || iy.intValue() != previousImageY){
				previousImage = staticImage.getSubimage(
						ix.intValue() * iw, 
						iy.intValue() * ih, 
						iw, 
						ih
				);
				previousImageX = ix.intValue();
				previousImageY = iy.intValue();
			}
			
			return previousImage;
		}else{
			return null;
		}
	}
}
