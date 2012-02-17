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
 * org.mdmk3.core.loader
 * Converter.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.loader;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;
/**
 * This interface defines classes that can convert a value of type <code>D</code> into a game node for use with map loaders.
 * @author revms
 * @since 0.0.0.153
 * @TODO Determine if this should be part of a utility package.
 */
public interface Converter<D,A extends Attributes> {
	/**
	 * Returns a node created from the specified value.
	 * @param value the value to turn into a node.
	 * @return a node representing the specified value.
	 */
	public Node<A> toNode(D value);
}
