/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 16-May-10 Matthew Stockbridge
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
 * org.mdmk2.core.logic
 * DefaultStatechart.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

import java.util.HashMap;

/**
 * @author mstockbridge
 * 16-May-10
 */
public class DefaultStateChart implements StateChart {
	private final HashMap<Class<? extends AttributeEvent<?>>, StateChange> map;
	
	public DefaultStateChart(){
		map = new HashMap<Class<? extends AttributeEvent<?>>, StateChange>();
	}
	
	public <A> void addStateChange(StateChange<A> s){
		map.put(s.targetType(), s);
	}
	
	public <A> void removeStateChange(StateChange<A> s){
		map.remove(s.targetType());
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Statechart#attributeChanged(org.mdmk2.core.logic.AttributeEvent)
	 */
	@SuppressWarnings("unchecked")
	public <V> void attributeChanged(AttributeEvent<V> event) {
		if(event != null){
			((StateChange<V>)map.get(event.getClass())).changeState(event);
		}
	}

}
