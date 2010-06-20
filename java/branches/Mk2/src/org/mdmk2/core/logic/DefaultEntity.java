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
 * org.mdmk2.core.logic
 * DefaultEntity.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

import java.util.List;

import org.mdmk2.core.cull.CullingMethod;
import org.mdmk2.core.disp2d.DefaultSprite;
import org.mdmk2.core.disp2d.DisplayableImp;
import org.mdmk2.core.disp2d.Sprite;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * 13-Jun-10
 */
public class DefaultEntity<R,A extends Attributed> extends DefaultSprite<R> implements Entity<R,A> {
	
	private final StatedImp<A> sImp;
	
	/**
	 * 
	 * @param cImp
	 * @param dImp
	 * @param sImp
	 */
	public DefaultEntity(CullingMethod<R> cImp, DisplayableImp dImp, StatedImp<A> sImp){
		super(cImp,dImp);
		this.sImp = sImp;
	}
	
	/**
	 * 
	 * @param node
	 * @param dImp
	 * @param sImp
	 */
	public DefaultEntity(Node<R> node, DisplayableImp dImp, StatedImp<A> sImp){
		this(node.getCullingMethod(),dImp,sImp);
	}
	
	/**
	 * 
	 * @param sprite
	 * @param sImp
	 */
	public DefaultEntity(Sprite<R> sprite, StatedImp<A> sImp){
		this(sprite,sprite.getDisplayableImp(),sImp);
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#addAction(org.mdmk2.core.logic.Action)
	 */
	public void addAction(Action<A> action) {
		sImp.addAction(action);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#getActions()
	 */
	public List<Action<A>> getActions() {
		return sImp.getActions();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#getImplementation()
	 */
	public StatedImp<A> getStatedImp() {
		return sImp;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#getState()
	 */
	public State<A> getState() {
		return sImp.getState();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#setState(org.mdmk2.core.logic.State)
	 */
	public void setState(State<A> s) {
		sImp.setState(s);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#updateState()
	 */
	public void updateState() {
		sImp.updateState();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Stated#needsStateUpdate()
	 */
	public boolean needsStateUpdate() {
		return sImp.needsStateUpdate();
	}

}
