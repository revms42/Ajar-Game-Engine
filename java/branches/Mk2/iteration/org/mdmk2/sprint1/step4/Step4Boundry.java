/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 14-Sep-10 Matthew Stockbridge
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
 * Step2Boundry.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import java.awt.Rectangle;

import org.mdmk2.core.collision.Collidable;
import org.mdmk2.core.collision.CollidableImp;
import org.mdmk2.core.logic.Action;
import org.mdmk2.core.node.DefaultNode;
import org.mdmk2.core.node.Node;

/**
 * @author reverend
 * 14-Sep-10
 */
public class Step4Boundry extends DefaultNode<Step4Attributes> implements Collidable<Step4Attributes> {
	
	private final CollidableImp<Step4Attributes> atts;
	
	public Step4Boundry(Step4Attributes a, CollidableImp<Step4Attributes> atts){
		super(a);
		this.atts = atts;
	}

	public boolean isInRange(Rectangle range){
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#collideWith(org.mdmk2.core.collision.Collidable)
	 */
	public Action collideWith(Collidable<Step4Attributes> s) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#getImplementation()
	 */
	public CollidableImp<Step4Attributes> getImplementation() {
		return atts;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#needsCollisionCheck()
	 */
	public boolean needsCollisionCheck() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.NodeDecorator#getNode()
	 */
	@Override
	public Node<Step4Attributes> getNode() {
		return this;
	}

}
