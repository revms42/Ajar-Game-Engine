/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Sep 9, 2010 Matthew Stockbridge
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
 * Step2SpriteFactory.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step2;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import org.mdmk2.core.collision.AbstractSolidFactory;
import org.mdmk2.core.collision.CollidableImp;
import org.mdmk2.core.logic.StatedImp;

/**
 * @author mstockbridge
 * Sep 9, 2010
 */
public class Step2SpriteFactory extends AbstractSolidFactory<Step2Attributes> {

	public static final Step2SpriteFactory singleton;
	
	static {
		singleton = new Step2SpriteFactory();
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.AbstractSpriteFactory#createDisplayableImp()
	 */
	@Override
	public Step2DisplayImp createDisplayableImp(Step2Attributes aImp) {
		return new Step2DisplayImp(aImp);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.AbstractAttributedNodeFactory#createAttributedImp()
	 */
	@Override
	public Step2Attributes createAttributes() {
		Ellipse2D circle = new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d);
		return new Step2Attributes(circle,Color.RED);
	}

	public static Step2Boundry createBoundry(Rectangle2D line){
		return new Step2Boundry(makeCollidableImp(createBoundryAtts(line)));
	}
	
	public static Step2Attributes createBoundryAtts(Rectangle2D line) {
		return new Step2Attributes(line,null);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.AbstractSolidFactory#createCollidableImp()
	 */
	@Override
	public CollidableImp<Step2Attributes> createCollidableImp(Step2Attributes aImp) {
		return makeCollidableImp(aImp);
	}
	
	public static CollidableImp<Step2Attributes> makeCollidableImp(Step2Attributes aImp) {
		return new Step2CollidableImp(aImp);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.AbstractEntityFactory#createStatedImp()
	 */
	@Override
	public StatedImp<Step2Attributes> createStatedImp(Step2Attributes aImp) {
		return new Step2StatedImp(aImp);
	}

}
