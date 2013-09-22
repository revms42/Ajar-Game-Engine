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
package ver.ajar.age.t8.display;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.ajar.age.Node;
import org.ajar.age.disp.awt.AbstractAWTDisplayable;

import ver.ajar.age.t8.VerAttribute;
import ver.ajar.age.t8.VerAttributes;
import ver.ajar.age.t8.VerMapAttribute;
import ver.ajar.age.t8.collision.CollisionAttribute;

/**
 * @author reverend
 *
 */
public class VerDisplayable extends AbstractAWTDisplayable<VerAttributes> {

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
	
	/**
	 * @param node
	 */
	public VerDisplayable(Node<VerAttributes> node) {
		super(node);
	}
	
	@Override
	public boolean onScreen(JComponent screen) {
		return node.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).intersects(screen.getBounds());
	}

	public BufferedImage drawImage(){
		int team = this.getAttributes().getAttribute(VerAttribute.TEAM).intValue();
		int iw = this.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).width;
		int ih = this.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX).height; 
		Number ix = this.getAttributes().getAttribute(VerAttribute.IMAGE_X);
		Number iy = this.getAttributes().getAttribute(VerAttribute.IMAGE_Y);
		
		if(team == 0){
			Number inRange = this.getAttributes().getAttribute(VerMapAttribute.DISPLAY_MOVE);
			
			if(inRange != null && inRange.intValue() != 0){
				ix = 0;
				iy = 5;
			}
		}
		
		if(ix != null && iy != null && ix.intValue() > 0 && iy.intValue() > 0){
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
