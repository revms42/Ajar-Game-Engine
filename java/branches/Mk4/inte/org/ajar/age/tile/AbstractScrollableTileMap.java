/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 5, 2015 Matthew Stockbridge
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
 * org.ajar.age.tile.scroll
 * AbstractScrollableTileMap.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.tile;

import java.awt.Rectangle;
import java.util.List;

import org.ajar.age.Attributes;
import org.ajar.age.Node;
import org.ajar.age.disp.scroll.ScrollTarget;
import org.ajar.age.disp.scroll.Scrollable;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractScrollableTileMap<A extends Attributes> extends AbstractTileMap<A> implements Scrollable<A> {

	protected int scrollX;
	protected int scrollY;
	protected Rectangle scrolledBounds;
	protected ScrollTarget<A> scrollTarget;
	
	/**
	 * @param node
	 */
	public AbstractScrollableTileMap(Node<A> node) {
		super(node);
	}

	/**
	 * @param node
	 * @param tiles
	 * @param width
	 */
	public <T extends MappedTile<A>> AbstractScrollableTileMap(Node<A> node, List<T> tiles, int width) {
		super(node, tiles, width);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#getScrollX()
	 */
	@Override
	public int getScrollX() {
		return scrollX;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#setScrollX(int)
	 */
	@Override
	public void setScrollX(int scrollX) {
		this.scrollX = scrollX;
		if(scrolledBounds != null){
			scrolledBounds.x = scrollX;
		}
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#getScrollY()
	 */
	@Override
	public int getScrollY() {
		return scrollY;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#setScrollY(int)
	 */
	@Override
	public void setScrollY(int scrollY) {
		this.scrollY = scrollY;
		if(scrolledBounds != null){
			scrolledBounds.y = scrollY;
		}
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#getScrolledBounds()
	 */
	@Override
	public Rectangle getScrolledBounds() {
		return scrolledBounds;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#setScrolledBounds(java.awt.Rectangle)
	 */
	@Override
	public void setScrolledBounds(Rectangle r) {
		scrolledBounds = r;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#getScrollTarget()
	 */
	@Override
	public ScrollTarget<A> getScrollTarget(){
		return scrollTarget;
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#setScrollTarget(org.ajar.age.disp.scroll.ScrollTarget)
	 */
	@Override
	public void setScrollTarget(ScrollTarget<A> target){
		this.scrollTarget = target;
		if(!this.getChildren().contains(target)){
			addChild(target);
		}
	}
	

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#getTotalWidth()
	 */
	@Override
	public int getTotalWidth() {
		return this.getTilesX() * this.getTileWidth();
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.disp.scroll.Scrollable#getTotalHeight()
	 */
	@Override
	public int getTotalHeight() {
		return this.getTilesY() * this.getTileHeight();
	}
}
