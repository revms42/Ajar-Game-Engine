/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 11-Sep-10 Matthew Stockbridge
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
 * Step2CollidableImp.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import org.mdmk2.core.collision.Collidable;
import org.mdmk2.core.collision.CollidableImp;
import org.mdmk2.core.logic.Action;

/**
 * @author reverend
 * 11-Sep-10
 */
public class Step4CollidableImp implements CollidableImp<Step4Attributes> {

	private Step4Attributes a;
	protected AffineTransform trans;
	
	public Step4CollidableImp(Step4Attributes a){
		this.a = a;
		trans = AffineTransform.getTranslateInstance(0, 0);
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.CollidableImp#collideWith(org.mdmk2.core.collision.Collidable)
	 */
	public Action collideWith(Collidable<Step4Attributes> s) {
		trans = AffineTransform.getTranslateInstance(getAttributes().getXVel(), getAttributes().getYVel());
		Rectangle collSurf = s.getNode().getAttributes().getCollisionSurface().getBounds();
		
		int ret = 0;
		ret = trans.createTransformedShape(getAttributes().getHTester()).intersects(collSurf) ? ret + 1 : ret;
		ret = trans.createTransformedShape(getAttributes().getVTester()).intersects(collSurf) ? ret + 2 : ret;
		
		switch(ret){
		case 1:
			return new Step4Action(Step4ActionType.BOUNCE_H);
		case 2:
			return new Step4Action(Step4ActionType.BOUNCE_V);
		case 3:
			return new Step4Action(Step4ActionType.BOUNCE_D);
		default:
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.CollidableImp#needsCollisionCheck()
	 */
	public boolean needsCollisionCheck() {
		return true;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.Attributed#getAttributes()
	 */
	@Override
	public Step4Attributes getAttributes() {
		return a;
	}

}
