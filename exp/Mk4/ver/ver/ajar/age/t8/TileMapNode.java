/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 20, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t7
 * TileMapNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t8;

import org.ajar.age.DefaultNode;
import org.ajar.age.Node;

/**
 * @author mstockbr
 *
 */
public class TileMapNode extends DefaultNode<VerAttributes> {

	/**
	 * @param attrs
	 */
	public TileMapNode(VerAttributes attrs) {
		super(attrs);
	}

	private int getXScanSize(){
		return this.getAttributes().getAttribute(VerMapAttribute.WIDTH).intValue();
	}
	
	private int pointToIndex(int x, int y){
		return (y * getXScanSize()) + x;
	}
	
	public void addChildTile(int x, int y, Node<VerAttributes> child){
		int index = pointToIndex(x,y);
		while(index >= this.getChildren().size()){
			this.getChildren().add(null);
		}
		this.getChildren().set(index, child);
	}
	
	public Node<VerAttributes> getChildTile(int x, int y){
		return this.getChildren().get(pointToIndex(x,y));
	}
}
