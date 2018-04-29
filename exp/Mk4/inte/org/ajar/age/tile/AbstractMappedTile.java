/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 4, 2015 Matthew Stockbridge
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
 * org.ajar.age.tile
 * DefaultMappedTile.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.tile;

import java.awt.Point;

import org.ajar.age.Attributes;
import org.ajar.age.Node;
import org.ajar.age.DefaultDecorator;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractMappedTile<A extends Attributes> extends DefaultDecorator<A> implements MappedTile<A> {

	/**
	 * @param node
	 */
	public AbstractMappedTile(Node<A> node) {
		super(node);
	}
	
	/**
	 * Creates this tile from the given node and sets it's location to the specified x and y coords.
	 * @param x
	 * @param y
	 * @param node
	 */
	public AbstractMappedTile(int x, int y, Node<A> node) {
		super(node);
		setTileXPos(x);
		setTileYPos(y);
	}
	
	/**
	 * Creates this tile from a given node and sets it's location to specified point on the tile map.
	 * @param p
	 * @param node
	 */
	public AbstractMappedTile(Point p, Node<A> node) {
		super(node);
		setTileXPos(p.x);
		setTileYPos(p.y);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MappedTile<A> o) {
		if(getTileYPos() > o.getTileYPos()){
			return 1;
		}else if(getTileYPos() < o.getTileYPos()){
			return -1;
		}else{
			if(getTileXPos() > o.getTileXPos()){
				return 1;
			}else if(getTileXPos() < o.getTileXPos()){
				return -1;
			}else{
				return 0;
			}
		}
	}

	/**
	 * Set the X position of this tile.
	 * <p>
	 * This will only have effect once finalize is called on the parent TileMap.
	 * @param x
	 * @see TileMap#finalizeMap()
	 */
	public abstract void setTileXPos(int x);
	
	/**
	 * Set the Y position of this tile.
	 * <p>
	 * This will only have effect once finalize is called on the parent TileMap.
	 * @param y
	 * @see TileMap#finalizeMap()
	 */
	public abstract void setTileYPos(int y);
}