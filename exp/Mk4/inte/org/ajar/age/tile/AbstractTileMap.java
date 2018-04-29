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
 * AbstractTileMap.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.tile;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.ajar.age.Attributes;
import org.ajar.age.Decorator;
import org.ajar.age.DefaultDecorator;
import org.ajar.age.Node;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractTileMap<A extends Attributes> extends DefaultDecorator<A> implements TileMap<A> {

	protected final Vector<MappedTile<A>> tileMap;
	private boolean finalized;
	
	private int mapWidth;
	private int mapHeight;
	
	/**
	 * @param node
	 */
	public AbstractTileMap(Node<A> node) {
		super(node);
		tileMap = new Vector<MappedTile<A>>();
		deFinalize();
	}
	
	/**
	 * Creates this tile map with the list of tiles, and specified width. This assumes that there will be a
	 * number of tiles evenly divisible by the supplied width. The behavior if this condition is not met is 
	 * indeterminate, but a call to <code>finalizeMap</code> will correct aberrant behavior should it occur.
	 * @param node
	 * @param tiles
	 * @param width
	 * @see #finalizeMap()
	 */
	public <T extends MappedTile<A>> AbstractTileMap(Node<A> node, List<T> tiles, int width){
		this(node);
		tileMap.addAll(tiles);
		mapWidth = width;
		mapHeight = tiles.size()/width;
		finalized = true;
	}
	
	private void deFinalize(){
		mapWidth = -1;
		mapHeight = -1;
		finalized = false;
	}

	/**
	 * Adds the node <code>child</code> to the end of the list of this node's children.
	 * <p>
	 * If this node is annotated with:
	 * <p>
	 * <code>@DecoratesFor(types = {MappedTile.class})</code>
	 * <p>
	 * Then the child will be added as a mapped tile instead of just as a child node.
	 * @param child the node that this node will be parent to.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addChild(Node<A> child) {
		if(child.hasCapability((Class<? extends Decorator<A>>) MappedTile.class)){
			tileMap.add(child.getDecorator(MappedTile.class));
			if(finalized) deFinalize();
		}
		super.addChild(child);
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#getTile(int, int)
	 */
	@Override
	public MappedTile<A> getTile(int x, int y) {
		return tileMap.get(y*getTilesX() + x);
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#getTileUnder(double, double)
	 */
	@Override
	public MappedTile<A> getTileUnder(double x, double y) {
		return getTile((new Double(x/getTileWidth())).intValue(),(new Double(y/getTileHeight())).intValue());
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#getTilesX()
	 */
	@Override
	public int getTilesX() {
		return mapWidth;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#getTilesY()
	 */
	@Override
	public int getTilesY() {
		return mapHeight;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#getMapWidth()
	 */
	@Override
	public int getMapWidth() {
		return getTilesX() * getTileWidth();
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#getMapHeight()
	 */
	@Override
	public int getMapHeight() {
		return getTilesY() * getTileHeight();
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#clearMap()
	 */
	@Override
	public void clearMap() {
		this.getChildren().clear();
		tileMap.clear();
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#isFinalized()
	 */
	@Override
	public boolean isFinalized() {
		return finalized;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.tile.TileMap#finalizeMap()
	 */
	@Override
	public void finalizeMap() {
		mapWidth = 0;
		mapHeight = 0;
		for(MappedTile<A> tile : tileMap){
			if(tile.getTileXPos() > mapWidth) mapWidth = tile.getTileXPos();
			if(tile.getTileYPos() > mapHeight) mapHeight = tile.getTileYPos();
		}
		mapWidth++;
		mapHeight++;
		
		Collections.sort(tileMap);
		
		for(int y = 0; y < mapHeight; y++){
			if(getTile(0,y).getTileYPos() != y){
				for(int i = y; i < getTile(0,y).getTileYPos(); i++){
					for(int j = 0; j < mapWidth; j++){
						addChild(makeNullTile(j, i));
					}
				}
				Collections.sort(tileMap);
			}
			for(int x = 0; x < mapWidth; x++){
				if(getTile(x,y).getTileYPos() != y || getTile(x,y).getTileXPos() != x){
					for(int i = y; i < getTile(x,y).getTileYPos(); i++){
						for(int j = 0; j < getTile(x,y).getTileXPos(); j++){
							addChild(makeNullTile(j, i));
						}
					}
					Collections.sort(tileMap);
				}
			}
		}
		finalized = true;
	}

	/**
	 * Called to produce a tile for a section of the tile map that has no provided tile data.
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract MappedTile<A> makeNullTile(int x, int y);
}
