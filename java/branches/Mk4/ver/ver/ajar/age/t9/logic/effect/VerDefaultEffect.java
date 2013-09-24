/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 11, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t2.logic
 * VerDefaultEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t9.logic.effect;

import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;

import ver.ajar.age.t0.AbstractChainableEffect;
import ver.ajar.age.t9.VerAttribute;
import ver.ajar.age.t9.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerDefaultEffect extends AbstractChainableEffect<VerAttributes> {

	
	private final static int step = 5;
	/**
	 * @param a
	 * @param result
	 */
	public VerDefaultEffect(State<VerAttributes> result) {
		super(result);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<VerAttributes> entity) {
		int xdest = entity.getAttributes().getAttribute(VerAttribute.X_TILE_DEST).intValue();
		int xtile = entity.getAttributes().getAttribute(VerAttribute.X_TILE_POS).intValue();
		int ydest = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_DEST).intValue();
		int ytile = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_POS).intValue();
		
		if(xdest != xtile){
			//System.out.println("Moving... " + entity.hashCode());
			int xoffset = entity.getAttributes().getAttribute(VerAttribute.X_MOVE_OFFSET).intValue();
			
			if(xoffset >= (16 - step) || xoffset <= -(16 - step)){
				entity.getAttributes().setAttribute(VerAttribute.X_MOVE_OFFSET, 0);
				
				if(xoffset > 0){
					entity.getAttributes().setAttribute(VerAttribute.X_TILE_POS, xtile+1);
					entity.getAttributes().setAttribute(
							VerAttribute.X_DRAW_POS, 
							VerAttributes.tileToCoordinate(xtile+1)
					);
				}else{
					entity.getAttributes().setAttribute(VerAttribute.X_TILE_POS, xtile-1);
					entity.getAttributes().setAttribute(
							VerAttribute.X_DRAW_POS, 
							VerAttributes.tileToCoordinate(xtile-1)
					);
				}
			}else{
				if(xdest > xtile){
					entity.getAttributes().setAttribute(VerAttribute.X_MOVE_OFFSET, xoffset+step);
					entity.getAttributes().setAttribute(
							VerAttribute.X_DRAW_POS, 
							VerAttributes.tileToCoordinate(xtile) + xoffset + step
					);
				}else{
					entity.getAttributes().setAttribute(VerAttribute.X_MOVE_OFFSET, xoffset-step);
					entity.getAttributes().setAttribute(
							VerAttribute.X_DRAW_POS, 
							VerAttributes.tileToCoordinate(xtile) + xoffset - step
					);
				}
			}
		}else if(ydest != ytile){
			//System.out.println("Moving... " + entity.hashCode());
			int yoffset = entity.getAttributes().getAttribute(VerAttribute.Y_MOVE_OFFSET).intValue();
			
			if(yoffset >= (16 - step) || yoffset <= -(16 - step)){
				entity.getAttributes().setAttribute(VerAttribute.Y_MOVE_OFFSET, 0);
				
				if(yoffset > 0){
					entity.getAttributes().setAttribute(VerAttribute.Y_TILE_POS, ytile+1);
					entity.getAttributes().setAttribute(
							VerAttribute.Y_DRAW_POS, 
							VerAttributes.tileToCoordinate(ytile+1)
					);
				}else{
					entity.getAttributes().setAttribute(VerAttribute.Y_TILE_POS, ytile-1);
					entity.getAttributes().setAttribute(
							VerAttribute.Y_DRAW_POS, 
							VerAttributes.tileToCoordinate(ytile-1)
					);
				}
			}else{
				if(ydest > ytile){
					entity.getAttributes().setAttribute(VerAttribute.Y_MOVE_OFFSET, yoffset+step);
					entity.getAttributes().setAttribute(
							VerAttribute.Y_DRAW_POS, 
							VerAttributes.tileToCoordinate(ytile) + yoffset + step
					);
				}else{
					entity.getAttributes().setAttribute(VerAttribute.Y_MOVE_OFFSET, yoffset-step);
					entity.getAttributes().setAttribute(
							VerAttribute.Y_DRAW_POS, 
							VerAttributes.tileToCoordinate(ytile) + yoffset - step
					);
				}
			}
		}
	}
}
