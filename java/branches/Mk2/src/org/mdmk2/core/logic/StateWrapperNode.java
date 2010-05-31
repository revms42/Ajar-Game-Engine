/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 30-May-10 Matthew Stockbridge
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
 * StateWrapperNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

import java.util.List;
import java.util.Vector;

import org.mdmk2.core.AbstractNode;
import org.mdmk2.core.Node;

/**
 * @author mstockbridge
 * 30-May-10
 */
public class StateWrapperNode<R,E extends Entity & Node<R> & Stated<E>> extends AbstractNode<R> implements Entity {
	
	private StateMatrix<E> matrix;
	private E entity;
	private final Vector<StateChange> changes;
	
	public StateWrapperNode(){
		super();
		changes = new Vector<StateChange>();
	}
	
	public StateWrapperNode(StateMatrix<E> matrix, E entity){
		this();
		this.entity = entity;
		this.matrix = matrix;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#isInRange(java.lang.Object)
	 */
	public boolean isInRange(R range) {
		return entity.isInRange(range);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#needsUpdate()
	 */
	public org.mdmk2.core.Node.UpdateType needsUpdate() {
		UpdateType type = UpdateType.STATUS_ONLY;
		UpdateType wrapped = entity.needsUpdate();
		if(wrapped == UpdateType.DISPLAY_ONLY || wrapped == UpdateType.DISPLAY_AND_STATUS){
			type = UpdateType.DISPLAY_AND_STATUS;
		}
		return type;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#updateStatus()
	 */
	public void updateStatus() {
		while(!changes.isEmpty()){
			matrix.performUpdate(entity, changes.firstElement());
			entity.updateStatus();
			changes.remove(0);
		}
	}

	public boolean add(StateChange arg0) {
		return changes.add(arg0);
	}

	public boolean remove(Object arg0) {
		return changes.remove(arg0);
	}

	public List<StateChange> allChanges(){
		return changes;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public StateMatrix<E> getMatrix() {
		return matrix;
	}

	public void setMatrix(StateMatrix<E> matrix) {
		this.matrix = matrix;
	}
}
