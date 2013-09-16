/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 13, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t3.collision
 * VerCollidable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t4.collision;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import org.ajar.age.Node;
import org.ajar.age.collision.AbstractCollidable;
import org.ajar.age.collision.Collidable;
import org.ajar.age.logic.Action;
import org.ajar.age.logic.HashAttributes;

import ver.ajar.age.t4.VerAttribute;
import ver.ajar.age.t4.logic.VerAction;

/**
 * @author mstockbr
 *
 */
public class VerCollidable extends AbstractCollidable<HashAttributes> {

	/**
	 * @param node
	 */
	public VerCollidable(Node<HashAttributes> node) {
		super(node);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.collision.Collidable#collideWith(org.ajar.age.collision.Collidable)
	 */
	@Override
	public Action collideWith(Collidable<HashAttributes> s) {
		//TODO: Actually figure out if the two things collide.
		Number thisTeam = this.getAttributes().getAttribute(VerAttribute.TEAM);
		
		if(thisTeam != null && thisTeam.intValue() != 0){
			Number sTeam = s.getAttributes().getAttribute(VerAttribute.TEAM);
			
			if(sTeam != null && sTeam.intValue() == 0){
				return findBoundryCollision(s.getAttributes());
			}
		}
		
		return null;
	}

	private Action findBoundryCollision(HashAttributes target){
		Rectangle myRect = this.getAttributes().getAttribute(CollisionAttribute.BOUNDING_BOX);
		Rectangle theirRect = target.getAttribute(CollisionAttribute.BOUNDING_BOX);
		
		if(myRect.intersects(theirRect)){
			int oc = theirRect.outcode(new Point2D.Double(myRect.getCenterX(),myRect.getCenterY()));
			
			switch(oc){
			case Rectangle.OUT_TOP:
				return VerAction.BOUNCE_V;
			case Rectangle.OUT_RIGHT | Rectangle.OUT_TOP:
				return VerAction.BOUNCE_D;
			case Rectangle.OUT_RIGHT:
				return VerAction.BOUNCE_H;
			case Rectangle.OUT_RIGHT | Rectangle.OUT_BOTTOM:
				return VerAction.BOUNCE_D;
			case Rectangle.OUT_BOTTOM:
				return VerAction.BOUNCE_V;
			case Rectangle.OUT_BOTTOM | Rectangle.OUT_LEFT:
				return VerAction.BOUNCE_D;
			case Rectangle.OUT_LEFT:
				return VerAction.BOUNCE_H;
			case Rectangle.OUT_LEFT | Rectangle.OUT_TOP:
				return VerAction.BOUNCE_D;
			default:
				return null;
			}
		}else{
			return null;
		}
	}
}
