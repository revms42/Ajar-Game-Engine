/*
 * This file is part of Ajar Game Engine
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
 * age
 * org.ajar.age.logic
 * AbstractEntity.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import java.util.List;
import java.util.Vector;

import org.ajar.age.Attributes;
import org.ajar.age.DefaultDecorator;
import org.ajar.age.Node;

/**
 * TODO: Rewrite.
 * A partial implementation of <code>Entity</code> that provides data access methods for <code>Controllers</code>.
 * @author revms
 * @since 0.0.0.153
 */
public abstract class AbstractEntity<A extends Attributes> extends DefaultDecorator<A> implements Entity<A> {

	private final Vector<Controller<A>> controllers;
	
	/**
	 * Constructs a new <code>AbstractEntity</code> that decorates the provided node.
	 * @param node the node this <code>Entity</code> will decorate.
	 */
	public AbstractEntity(Node<A> node) {
		super(node);
		this.controllers = new Vector<Controller<A>>();
	}
	
	public List<Controller<A>> getControllers(){
		return controllers;
	}
	
	public void addController(Controller<A> controller){
		controllers.add(controller);
	}
	
	public boolean removeController(Controller<A> controller){
		return controllers.remove(controller);
	}
}
