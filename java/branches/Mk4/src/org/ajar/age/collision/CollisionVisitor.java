/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 26, 2013 Matthew Stockbridge
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
 * org.ajar.age.collision
 * CollisionVisitor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */

/**
 * @author mstockbr
 */
package org.ajar.age.collision;

import org.ajar.age.Attributes;
import org.ajar.age.SimpleVisitor;
import org.ajar.age.logic.Action;
import org.ajar.age.logic.Entity;

/**
 * @author mstockbr
 *
 */
public abstract class CollisionVisitor<A extends Attributes> extends SimpleVisitor<A,Collidable<A>> {
	private final Class<? extends Entity<A>> entityClass;
	
	public CollisionVisitor(
			Class<? extends Collidable<A>> collClass, 
			Class<? extends Entity<A>> entityClass
	){
		super(collClass);
		this.entityClass = entityClass;
	}

	@Override
	public void process() {
		for(int start = 0; start < needsUpdate.size(); start++){
			for(int i = start+1; i < needsUpdate.size(); i++){
				Collidable<A> c = (Collidable<A>) needsUpdate.get(start);
				Collidable<A> d = (Collidable<A>) needsUpdate.get(i);
				
				Action a = c.collideWith(d);
				Action b = d.collideWith(c);
				
				if(a != null && c.hasCapability(entityClass)){
					Entity<A> s = c.getDecorator(entityClass);
					s.addAction(a);
				}
				if(b != null && d.hasCapability(entityClass)){
					Entity<A> s = d.getDecorator(entityClass);
					s.addAction(b);
				}
			}
		}
		needsUpdate.clear();
	}
}
