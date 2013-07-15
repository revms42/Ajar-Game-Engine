/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 27, 2013 Matthew Stockbridge
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
 * AGE
 * org.ajar.age
 * AbstractVisitor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */

/**
 * @author mstockbr
 */
package org.ajar.age;

import java.util.Vector;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractVisitor<A extends Attributes,D extends Decorator<A>> implements Visitor<A>{

	private final Class<? extends D> decoratorClass;
	protected final Vector<D> needsUpdate;
	
	public AbstractVisitor(Class<? extends D> decoratorClass){
		this.decoratorClass = decoratorClass;
		needsUpdate = new Vector<D>();
	}
	/* (non-Javadoc)
	 * @see org.ajar.age.Visitor#isInView(org.ajar.age.Node)
	 */
	@Override
	public boolean isInView(Node<A> node) {
		if(node.hasCapability(decoratorClass)){
			return isInView(node.getDecorator(decoratorClass));
		}
		return false;
	}

	protected abstract boolean isInView(D decorator);
	
	/* (non-Javadoc)
	 * @see org.ajar.age.Visitor#visit(org.ajar.age.Node)
	 */
	@Override
	public void visit(Node<A> node) {
		if(node.hasCapability(decoratorClass)){
			D disp = node.getDecorator(decoratorClass);
			if(disp.needsUpdate() && isInView(disp)){
				needsUpdate.add(disp);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.Visitor#process()
	 */
	@Override
	public void process() {
		startProcess();
		for(D disp : needsUpdate){
			update(disp);
		}
		needsUpdate.clear();
		finishProcess();
	}

	public abstract void update(D decorator);
	
	protected abstract void startProcess();
	
	protected abstract void finishProcess();
}
