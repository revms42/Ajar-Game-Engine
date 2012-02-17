/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Jan 5, 2011 Matthew Stockbridge
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
 * NullTileNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.loader;

import java.util.List;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Decorator;
import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
/**
 * This class is used by the TileMapLoader to indicate that the position for the given tile is actually not a tile.
 * These "null" tiles get merged into large areas of empty space which consequently aren't tested for collisions,
 * get display updates, or get logic updates (and so slow down the game only very slightly).
 * @author revms
 * @since 0.0.0.156
 * @TODO Determine if this should be part of a utility package.
 */
public final class NullTileNode<A extends Attributes> extends DefaultNode<A> {

	/**
	 * Constructs a new <code>NullTileNode</code> from the given attributes.
	 * @param attrs the attributes to describe this node.
	 */
	public NullTileNode(A attrs) {
		super(attrs);
	}

	/**
	 * Required by <code>Node</code>.
	 * @return always <code>false</code>
	 */
	@Override
	public boolean hasChildren() {return false;}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This method will <b>not</b> add a child to this node.
	 * @param child 
	 */
	@Override
	public void addChild(Node<A> child) {}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This node accepts no children
	 * @param child 
	 * @return always <code>false</code>
	 */
	@Override
	public boolean removeChild(Node<A> child) {return false;}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This node accepts no children.
	 * @return always <code>null</code>
	 */
	@Override
	public List<Node<A>> getChildren() {return null;}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This node accepts no parent.
	 * @return always <code>null</code>
	 */
	@Override
	public Node<A> getParent() {return null;}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This node cannot have decorators.
	 * @param c
	 * @return always <code>false</code>
	 */
	@Override
	public boolean hasCapability(Class<? extends Decorator<A>> c) {return false;}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This node cannot have decorators.
	 * @param c
	 * @return always <code>null</code>
	 */
	@Override
	public <E extends Decorator<A>> E getDecorator(Class<E> c) {return null;}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This node accepts no decorators.
	 * @param decorator
	 */
	@Override
	public void addDecorator(Decorator<A> decorator) {}

	/**
	 * Required by <code>Node</code>.
	 * <p>
	 * This node accepts no decorators.
	 * @param c
	 * @return always <code>null</code>
	 */
	@Override
	public <E extends Decorator<A>> E removeDecorator(Class<E> c) {return null;}
}
