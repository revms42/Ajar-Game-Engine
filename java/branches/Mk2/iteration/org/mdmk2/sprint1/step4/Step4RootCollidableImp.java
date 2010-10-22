/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Oct 22, 2010 Matthew Stockbridge
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
 * org.mdmk2.sprint1.step4
 * Step4RootCollidableImp.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.mdmk2.core.collision.Collidable;
import org.mdmk2.core.logic.Action;
import org.mdmk2.core.logic.Stated;

/**
 * @author mstockbr
 * Oct 22, 2010
 */
public class Step4RootCollidableImp extends Step4CollidableImp {

	/**
	 * @param a
	 */
	public Step4RootCollidableImp(Step4Attributes a) {
		super(a);
	}

	public Action collideWith(Collidable<Step4Attributes> s) {
		if(s instanceof Stated){
			@SuppressWarnings("unchecked")
			Stated<Step4Attributes> ss = (Stated<Step4Attributes>)s;
			
			Step4Attributes a = s.getNode().getAttributes();
			trans = AffineTransform.getTranslateInstance(a.getXVel(), a.getYVel());
			
			Rectangle2D bounds = this.getAttributes().getCollisionSurface().getBounds2D();
			
			Rectangle2D hRect = trans.createTransformedShape(a.getHTester()).getBounds2D();
			Rectangle2D vRect = trans.createTransformedShape(a.getVTester()).getBounds2D();
			
			int ret = 0;
			ret = (bounds.outcode(hRect.getMinX(),hRect.getMinY()) + bounds.outcode(hRect.getMaxX(),hRect.getMaxY())) > 0 ?
					ret + 1 : ret;
			
			ret = (bounds.outcode(vRect.getMinX(),vRect.getMinY()) + bounds.outcode(vRect.getMaxX(),vRect.getMaxY())) > 0 ?
					ret + 2 : ret;
			
			switch(ret){
			case 1:
				ss.addAction(new Step4Action(Step4ActionType.BOUNCE_H));
				return null;
			case 2:
				ss.addAction(new Step4Action(Step4ActionType.BOUNCE_V));
				return null;
			case 3:
				ss.addAction(new Step4Action(Step4ActionType.BOUNCE_D));
				return null;
			default:
				return null;
			}
		}else{
			return null;
		}
	}
}
