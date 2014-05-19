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
package ver.ajar.age.t9.logic.effect;

import org.ajar.age.Node;
import org.ajar.age.logic.AbstractChainableEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;

import ver.ajar.age.t9.TileMapNode;
import ver.ajar.age.t9.VerAttribute;
import ver.ajar.age.t9.VerAttributes;
import ver.ajar.age.t9.VerMapAttribute;
import ver.ajar.age.t9.VerRefDecorator;

/**
 * @author reverend
 *
 */
public class VerSetRangeEffect extends AbstractChainableEffect<VerAttributes> {

	private final int value;
	/**
	 * @param a
	 * @param result
	 */
	public VerSetRangeEffect(State<VerAttributes> result, int value) {
		super(result);
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<VerAttributes> entity) {
		//System.out.println("SetRange Previous " + entity.hashCode() + "->");
		entity = entity.getRoot().getDecorator(VerRefDecorator.class).getCurrentPlayer();
		//System.out.println("-> Next " + entity.hashCode() + " to " + value);
		int xtile = entity.getAttributes().getAttribute(VerAttribute.X_TILE_POS).intValue();
		int ytile = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_POS).intValue();
		
		for(Node<VerAttributes> sibling : entity.getParent().getChildren()){
			if(sibling.getUndecoratedNode() instanceof TileMapNode){
				TileMapNode map = (TileMapNode)sibling.getUndecoratedNode();
				int range = entity.getAttributes().getAttribute(VerAttribute.RANGE).intValue();
				
				for(int x = -range; x <= range; x++){
					if(xtile + x >= 0){
						for(int y = -range; y <= range; y++){
							if(ytile + y >=0 && Math.abs(x) + Math.abs(y) <= range){
								Node<VerAttributes> tile = map.getChildTile(xtile + x, ytile + y);
								tile.getAttributes().setAttribute(VerMapAttribute.DISPLAY_MOVE,value);
							}
						}
					}
				}
			}
		}
	}
}
