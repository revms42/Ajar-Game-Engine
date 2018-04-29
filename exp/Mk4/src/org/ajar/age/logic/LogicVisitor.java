/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 27, 2013 Matthew Stockbridge
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
 * org.ajar.age.logic
 * LogicVisitor.java
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
package org.ajar.age.logic;

import org.ajar.age.AbstractVisitor;
import org.ajar.age.Attributes;

/**
 * @author mstockbr
 *
 */
public abstract class LogicVisitor<A extends Attributes> extends AbstractVisitor<A,Entity<A>> {
	
	public LogicVisitor(Class<? extends Entity<A>> entityClass){
		super(entityClass);
	}
	
	public void update(Entity<A> entity){
		entity.updateState();
	}

}
