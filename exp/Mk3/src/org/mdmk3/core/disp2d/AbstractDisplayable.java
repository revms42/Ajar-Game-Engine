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
 * MDMk3
 * org.mdmk3.core.disp2d
 * AbstractDisplayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.DefaultDecorator;
import org.mdmk3.core.Node;

/**
 * This class provides the default implementation of all of the {@link org.mdmk3.core.Decorator} methods by extending
 * <code>DefaultDecorator</code>, and adds the <code>Displayable</code> interface, making it a suitable
 * starting place for writing most <code>Displayable</code> implementations.
 * @author revms
 * @since 0.0.0.153
 */
public abstract class AbstractDisplayable<A extends Attributes> extends DefaultDecorator<A> implements Displayable<A> {

	/**
	 * Constructs an AbstractDisplayable using the supplied node as the node the AbstractDisplayable will decorate.
	 * @param node the node the new AbstractDisplayable will decorate.
	 */
	public AbstractDisplayable(Node<A> node) {
		super(node);
	}

}
