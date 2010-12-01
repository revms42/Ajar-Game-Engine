/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 30-May-10 Matthew Stockbridge
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
 * MDMk2
 * org.mdmk2.core.disp2d
 * GameLoop2d.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.disp2d;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.GameLoop;
import org.mdmk3.core.Node;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.logic.Action;
import org.mdmk3.core.logic.Entity;

/**
 * @author mstockbridge
 * 30-May-10
 */
public abstract class GameLoop2d<A extends Attributes> extends GameLoop<A> {
	
	protected DisplaySurface2d surface;
	/**
	 * @param displayRoot
	 */
	public GameLoop2d(Node<A> displayRoot, DisplaySurface2d surface) {
		super(displayRoot);
		this.surface = surface;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#logic()
	 */
	@Override
	public void logic() {
		for(Entity<?> e : needsStatusUpdate){
			e.updateState();
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#render()
	 */
	@Override
	public void render() {
		surface.drawToBuffer(needsDisplayUpdate);
		surface.blitToScreen();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#collision()
	 */
	@Override
	public void collision() {
		for(int start = 0; start < needsCollisionCheck.size(); start++){
			for(int i = start+1; i < needsCollisionCheck.size(); i++){
				Collidable<A> c = (Collidable<A>) needsCollisionCheck.get(start);
				Collidable<A> d = (Collidable<A>) needsCollisionCheck.get(i);
				
				Action a = c.collideWith(d);
				Action b = d.collideWith(c);
				
				if(a != null && c.hasCapability(getEntityClass())){
					Entity<A> s = c.getDecorator(getEntityClass());
					s.addAction(a);
				}
				if(b != null && d.hasCapability(getEntityClass())){
					Entity<A> s = d.getDecorator(getEntityClass());
					s.addAction(b);
				}
			}
		}
	}
}
