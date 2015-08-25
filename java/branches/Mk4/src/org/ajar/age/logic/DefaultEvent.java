/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jan 12, 2015 Matthew Stockbridge
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
 * DefaultEvent.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import org.ajar.age.Attributes;

/**
 * @author mstockbr
 * @since 0.0.0.207
 */
public class DefaultEvent<A extends Attributes> implements Event<A> {

	protected Action action;
	protected A attributes;
	
	public DefaultEvent(Action action, A attributes){
		this.action = action;
		this.attributes = attributes;
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Event#getAction()
	 */
	@Override
	public Action getAction() {
		return action;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Event#getAttributes()
	 */
	@Override
	public A getAttributes() {
		return attributes;
	}

}
