/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 13-Jun-10 Matthew Stockbridge
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
 * org.mdmk2.core.collision
 * DefaultSolid.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.collision;

import org.mdmk2.core.attributed.AttributedImp;
import org.mdmk2.core.attributed.AttributedNode;
import org.mdmk2.core.cull.CullingMethod;
import org.mdmk2.core.disp2d.DisplayableImp;
import org.mdmk2.core.disp2d.Sprite;
import org.mdmk2.core.logic.Action;
import org.mdmk2.core.logic.DefaultEntity;
import org.mdmk2.core.logic.Entity;
import org.mdmk2.core.logic.StatedImp;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * 13-Jun-10
 */
public class DefaultSolid<R,A extends AttributedImp> extends DefaultEntity<R,A> implements Solid<R,A> {

	private final CollidableImp<R,A> lImp;
	/**
	 * @param cImp
	 * @param dImp
	 * @param sImp
	 */
	public DefaultSolid(CullingMethod<R> cImp, A aImp, DisplayableImp dImp, StatedImp<A> sImp, CollidableImp<R,A> lImp) {
		super(cImp, aImp, dImp, sImp);
		this.lImp = lImp;
	}
	
	/**
	 * 
	 * @param node
	 * @param dImp
	 * @param sImp
	 * @param lImp
	 */
	public DefaultSolid(Node<R> node, A aImp, DisplayableImp dImp, StatedImp<A> sImp, CollidableImp<R,A> lImp){
		this(node.getCullingMethod(),aImp,dImp,sImp,lImp);
	}
	
	public DefaultSolid(AttributedNode<R,A> node, DisplayableImp dImp, StatedImp<A> sImp, CollidableImp<R,A> lImp){
		this(node,node.getAttributes(),dImp,sImp,lImp);
	}
	
	/**
	 * 
	 * @param sprite
	 * @param sImp
	 * @param lImp
	 */
	public DefaultSolid(Sprite<R,A> sprite, StatedImp<A> sImp, CollidableImp<R,A> lImp){
		this(sprite,sprite.getDisplayableImp(),sImp,lImp);
	}
	
	/**
	 * 
	 * @param entity
	 * @param lImp
	 */
	public DefaultSolid(Entity<R,A> entity, CollidableImp<R,A> lImp){
		this(entity,entity.getStatedImp(),lImp);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#getImplementation()
	 */
	public CollidableImp<R,A> getImplementation() {
		return lImp;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#collideWith(org.mdmk2.core.collision.Collidable)
	 */
	public Action<A> collideWith(Collidable<R,A> s) {
		return lImp.collideWith(s);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#needsCollisionCheck()
	 */
	public boolean needsCollisionCheck() {
		return lImp.needsCollisionCheck();
	}

}
