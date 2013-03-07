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
 * org.mdj.core.cull
 * CullingSurface.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdj.core.cull;

import org.mdj.core.Attributes;
import org.mdj.core.Node;

/**
 * This interface defines a class that will be used for culling nodes that are outside the target range
 * for game updates.
 * @see org.mdj.core.GameLoop#getCullingSurface()
 * @author revms
 * @since 0.0.0.153
 */
public interface CullingSurface<A extends Attributes> {

	/**
	 * Determines whether the provided node is in range to receive a game update.
	 * @param node the node being checked.
	 * @return <code>true</code> if the provided node is in range to receive an update.
	 */
	public boolean isInRange(Node<A> node);
}
