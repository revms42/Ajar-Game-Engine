/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 21, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t8.logic
 * VerSetRangeEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t8.logic;

import org.ajar.age.Node;
import org.ajar.age.logic.Action;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;

import ver.ajar.age.t0.ChainableEffect;
import ver.ajar.age.t8.TileMapNode;
import ver.ajar.age.t8.VerAttribute;
import ver.ajar.age.t8.VerAttributes;
import ver.ajar.age.t8.VerMapAttribute;

/**
 * @author reverend
 *
 */
public class VerSetRangeEffect extends ChainableEffect<VerAttributes> {

	private final int value;
	/**
	 * @param a
	 * @param result
	 */
	public VerSetRangeEffect(Action a, State<VerAttributes> result, int value) {
		super(a, result);
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<VerAttributes> entity) {
		setRangeTiles(entity,value);
	}

	public static void setRangeTiles(Entity<VerAttributes> entity, int value){
		int xtile = entity.getAttributes().getAttribute(VerAttribute.X_TILE_POS).intValue();
		int ytile = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_POS).intValue();
		
		for(Node<VerAttributes> sibling : entity.getParent().getChildren()){
			if(sibling instanceof TileMapNode){
				TileMapNode map = (TileMapNode)sibling;
				int range = entity.getAttributes().getAttribute(VerAttribute.RANGE).intValue();
				
				for(int x = -range; x <= range; x++){
					for(int y = -range; y <= range; y++){
						if(Math.abs(x) + Math.abs(y) <= range){
							Node<VerAttributes> tile = map.getChildTile(xtile + x, ytile + y);
							tile.getAttributes().setAttribute(VerMapAttribute.DISPLAY_MOVE,value);
						}
					}
				}
			}
		}
	}
}
