/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Dec 1, 2010 Matthew Stockbridge
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
 * mdj
 * org.mdj.core
 * DefaultDecorator.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core;

import java.util.List;

/**
 * DefaultDecorator is a general-purpose implementation of {@link Decorator}:
 * It provides the expected delegation functionality of the <code>Decorator</code>
 * interface without adding any additional functionality of its own.
 * @see Node
 * @see Decorator
 * @author revms
 * @since 0.0.0.153
 */
public class DefaultDecorator<A extends Attributes> implements Decorator<A> {
	
	/**
	 * The <code>Node</code> beind decorated.
	 */
	protected final Node<A> node;

	/**
	 * Constructs a new <code>DefaultDecorator</code> that is decorating the supplied <code>Node</code>.
	 * @param	node	the node that this decorator will decorate.
	 */
	public DefaultDecorator(Node<A> node){
		this.node = node;
		node.addDecorator(this);
	}
	
	@Override
	public boolean hasChildren() {
		return node.hasChildren();
	}

	@Override
	public void addChild(Node<A> child) {
		node.addChild(child);
	}
	
	@Override
	public boolean removeChild(Node<A> child) {
		return node.removeChild(child);
	}
	
	@Override
	public List<Node<A>> getChildren() {
		return node.getChildren();
	}
	
	@Override
	public Node<A> getParent() {
		return node.getParent();
	}
	
	@Override
	public A getAttributes() {
		return node.getAttributes();
	}
	
	@Override
	public boolean hasCapability(Class<? extends Decorator<A>> c) {
		return node.hasCapability(c);
	}
	
	@Override
	public <D extends Decorator<A>> D getDecorator(Class<D> c) {
		return node.getDecorator(c);
	}
	
	@Override
	public void addDecorator(Decorator<A> decorator) {
		node.addDecorator(decorator);
	}
	
	@Override
	public <D extends Decorator<A>> D removeDecorator(Class<D> c) {
		return node.removeDecorator(c);
	}

	@Override
	public Node<A> getUndecoratedNode() {
		return node.getUndecoratedNode();
	}
}
