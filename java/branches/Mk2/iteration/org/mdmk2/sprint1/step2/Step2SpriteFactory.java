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
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import org.mdmk2.core.collision.AbstractSolidFactory;
import org.mdmk2.core.collision.CollidableImp;
import org.mdmk2.core.collision.DefaultSolid;
import org.mdmk2.core.collision.Solid;
import org.mdmk2.core.cull.CullingMethod;
import org.mdmk2.core.disp2d.DisplayableImp;
import org.mdmk2.core.logic.StatedImp;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * Sep 9, 2010
 */
public class Step2SpriteFactory extends AbstractSolidFactory<Rectangle,Step2Attributes> {

	public static final Step2SpriteFactory singleton;
	private int serving = -1;
	
	static {
		singleton = new Step2SpriteFactory();
	}
	

	private Step2SolidImp currentImp;
	
	private static class Step1CullingMethod implements CullingMethod<Rectangle> {

		private final static Step1CullingMethod singleton;
		
		static {
			singleton = new Step1CullingMethod();
		}
		
		private Step1CullingMethod(){};
		
		/* (non-Javadoc)
		 * @see org.mdmk2.core.cull.CullingMethod#isInRange(java.lang.Object, org.mdmk2.core.node.Node)
		 */
		@Override
		public boolean isInRange(Rectangle range, Node<Rectangle> node) {
			return range.contains(node.getAbsolutePosition().getPosition());
		}
		
	}
	
	public Solid<Rectangle,Step2Attributes> createSolid(){
		CullingMethod<Rectangle> cm = createCullingMethod();
		Step2Attributes am = createAttributedImp();
		Step2SolidImp di = createDisplayableImp();
		StatedImp<Step2Attributes> si = createStatedImp();
		DefaultSolid<Rectangle,Step2Attributes> solid = new DefaultSolid<Rectangle,Step2Attributes>(cm,am,di,si,di);
		return solid;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.AbstractSpriteFactory#createDisplayableImp()
	 */
	@Override
	public Step2SolidImp createDisplayableImp() {
		Ellipse2D circle = new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d);
		Line2D hor = new Line2D.Double(0.0d,10.0d,20.0d,10.0d);
		Line2D ver = new Line2D.Double(10.0d,0.0d,10.0d,20.0d);
		return new Step2SolidImp(Color.RED,circle,hor,ver);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.attributed.AbstractAttributedNodeFactory#createAttributedImp()
	 */
	@Override
	public Step2Attributes createAttributedImp() {
		return new Step2Attributes();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.AbstactNodeFactory#createCullingMethod()
	 */
	@Override
	public CullingMethod<Rectangle> createCullingMethod() {
		return Step1CullingMethod.singleton;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.AbstractSolidFactory#createCollidableImp()
	 */
	@Override
	public CollidableImp<Rectangle,Step2Attributes> createCollidableImp() {

		return null;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.AbstractEntityFactory#createStatedImp()
	 */
	@Override
	public StatedImp<Step2Attributes> createStatedImp() {
		// TODO Auto-generated method stub
		return null;
	}

}
