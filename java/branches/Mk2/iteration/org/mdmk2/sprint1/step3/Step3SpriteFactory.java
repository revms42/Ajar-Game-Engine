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
package org.mdmk2.sprint1.step3;

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
public class Step3SpriteFactory extends AbstractSolidFactory<Step3Attributes> {

	public static final Step3SpriteFactory singleton;
	
	static {
		singleton = new Step3SpriteFactory();
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.AbstractSpriteFactory#createDisplayableImp()
	 */
	@Override
	public Step3DisplayImp createDisplayableImp(Step3Attributes aImp) {
		return makeDisplayableImp(aImp);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.AbstractAttributedNodeFactory#createAttributedImp()
	 */
	@Override
	public Step3Attributes createAttributes() {
		Ellipse2D circle = new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d);
		return new Step3Attributes(circle,Color.RED);
	}

	public static Step3Boundry createBoundry(Rectangle2D line){
		return new Step3Boundry(makeCollidableImp(createBoundryAtts(line)));
	}
	
	public static Step3Attributes createBoundryAtts(Rectangle2D line) {
		return new Step3Attributes(line,null);
	}

	public static Step3Box createBox(Rectangle2D box){
		Step3Attributes atts = createBoxAtts(box);
		return new Step3Box(atts,makeDisplayableImp(atts),makeCollidableImp(atts));
	}
	
	public static Step3Attributes createBoxAtts(Rectangle2D box){
		return new Step3Attributes(box,Color.BLUE);
	}
	
	public static Step3DisplayImp makeDisplayableImp(Step3Attributes aImp){
		return new Step3DisplayImp(aImp);
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.AbstractSolidFactory#createCollidableImp()
	 */
	@Override
	public CollidableImp<Step3Attributes> createCollidableImp(Step3Attributes aImp) {
		return makeCollidableImp(aImp);
	}
	
	public static CollidableImp<Step3Attributes> makeCollidableImp(Step3Attributes aImp) {
		return new Step3CollidableImp(aImp);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.AbstractEntityFactory#createStatedImp()
	 */
	@Override
	public StatedImp<Step3Attributes> createStatedImp(Step3Attributes aImp) {
		return new Step3StatedImp(aImp);
	}

}
