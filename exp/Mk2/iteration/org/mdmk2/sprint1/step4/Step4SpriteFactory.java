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
package org.mdmk2.sprint1.step4;

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
public class Step4SpriteFactory extends AbstractSolidFactory<Step4Attributes> {

	public static final Step4SpriteFactory singleton;
	
	static {
		singleton = new Step4SpriteFactory();
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.AbstractSpriteFactory#createDisplayableImp()
	 */
	@Override
	public Step4DisplayImp createDisplayableImp(Step4Attributes aImp) {
		return makeDisplayableImp(aImp);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.AbstractAttributedNodeFactory#createAttributedImp()
	 */
	@Override
	public Step4Attributes createAttributes() {
		Ellipse2D circle = new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d);
		return new Step4Attributes(circle,Color.RED);
	}

	public static Step4RootNode createRootNode(Rectangle2D area){
		Step4Attributes atts = createBoxAtts(area);
		return new Step4RootNode(atts,new Step4RootCollidableImp(atts));
	}

	public static Step4Box createBox(Rectangle2D box){
		Step4Attributes atts = createBoxAtts(box);
		return new Step4Box(atts,makeDisplayableImp(atts),makeCollidableImp(atts));
	}
	
	public static Step4Attributes createBoxAtts(Rectangle2D box){
		return new Step4Attributes(box,Color.BLUE);
	}
	
	public static Step4DisplayImp makeDisplayableImp(Step4Attributes aImp){
		return new Step4DisplayImp(aImp);
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.AbstractSolidFactory#createCollidableImp()
	 */
	@Override
	public CollidableImp<Step4Attributes> createCollidableImp(Step4Attributes aImp) {
		return makeCollidableImp(aImp);
	}
	
	public static CollidableImp<Step4Attributes> makeCollidableImp(Step4Attributes aImp) {
		return new Step4CollidableImp(aImp);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.AbstractEntityFactory#createStatedImp()
	 */
	@Override
	public StatedImp<Step4Attributes> createStatedImp(Step4Attributes aImp) {
		return new Step4StatedImp(aImp);
	}

}
