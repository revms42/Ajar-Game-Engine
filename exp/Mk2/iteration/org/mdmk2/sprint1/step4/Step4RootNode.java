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
 * Step4RootNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import org.mdmk2.core.collision.Collidable;
import org.mdmk2.core.collision.CollidableImp;
import org.mdmk2.core.logic.Action;
import org.mdmk2.core.node.DefaultNode;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbr
 * Oct 22, 2010
 */
public class Step4RootNode extends DefaultNode<Step4Attributes> implements Collidable<Step4Attributes> {

	private CollidableImp<Step4Attributes> cImp;
	
	/**
	 * @param attributes
	 */
	public Step4RootNode(Step4Attributes attributes, CollidableImp<Step4Attributes> cImp) {
		super(attributes);
		this.cImp = cImp;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.node.NodeDecorator#getNode()
	 */
	@Override
	public Node<Step4Attributes> getNode() {
		return this;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#collideWith(org.mdmk2.core.collision.Collidable)
	 */
	@Override
	public Action collideWith(Collidable<Step4Attributes> s) {
		return cImp.collideWith(s);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#getImplementation()
	 */
	@Override
	public CollidableImp<Step4Attributes> getImplementation() {
		return cImp;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#needsCollisionCheck()
	 */
	@Override
	public boolean needsCollisionCheck() {
		return true;
	}

}
