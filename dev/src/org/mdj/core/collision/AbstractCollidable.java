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
 * org.mdj.core.collision
 * AbstractCollidable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core.collision;

import org.mdj.core.Attributes;
import org.mdj.core.DefaultDecorator;
import org.mdj.core.Node;

/**
 * This class provides the default implementation of all of the {@link org.mdj.core.Decorator} methods by extending
 * <code>DefaultDecorator</code>, and adds the <code>Collidable</code> interface, making it a suitable
 * starting place for writing most <code>Collidable</code> implementations.
 * @author revms
 * @since 0.0.0.153
 */
public abstract class AbstractCollidable<A extends Attributes> extends DefaultDecorator<A> implements Collidable<A> {

	/**
	 * Constructs an AbstractCollidable using the supplied node as the node the AbstractCollidable will decorate.
	 * @param node the node the new AbstractCollidable will decorate.
	 */
	public AbstractCollidable(Node<A> node) {
		super(node);
	}

}
