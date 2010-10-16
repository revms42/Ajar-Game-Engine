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
package org.mdmk2.sprint1.step3;

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
public class Step3Boundry extends DefaultNode<Step3Attributes> implements Collidable<Step3Attributes> {
	
	private final CollidableImp<Step3Attributes> atts;
	
	public Step3Boundry(CollidableImp<Step3Attributes> atts){
		super(null);
		this.atts = atts;
	}

	public boolean isInRange(Rectangle range){
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#collideWith(org.mdmk2.core.collision.Collidable)
	 */
	public Action collideWith(Collidable<Step3Attributes> s) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#getImplementation()
	 */
	public CollidableImp<Step3Attributes> getImplementation() {
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
	public Node<Step3Attributes> getNode() {
		return this;
	}

}
