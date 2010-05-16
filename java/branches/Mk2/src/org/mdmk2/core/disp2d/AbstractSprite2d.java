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
 * org.mdmk2.core.disp2d
 * Sprite2d.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.disp2d;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.mdmk2.core.logic.Actor;
import org.mdmk2.core.logic.Attribute;
import org.mdmk2.core.logic.AttributeEvent;
import org.mdmk2.core.logic.AttributeEventType;
import org.mdmk2.core.logic.StateChart;

/**
 * @author mstockbridge
 * 16-May-10
 */
public abstract class AbstractSprite2d extends AbstractDisplayable implements Actor {

	protected final HashMap<Class<?>,Attribute<?>> stats;
	protected final Vector<AttributeEventType> types;
	protected StateChart chart;
	
	public AbstractSprite2d(){
		stats = new HashMap<Class<?>,Attribute<?>>();
		types = new Vector<AttributeEventType>();
		
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Actor#getValue(java.lang.Class)
	 */
	public <V, A extends Attribute<V>> V getValue(Class<A> type) {
		if(stats.containsKey(type)) return ((Attribute<V>)stats).getValue();
		return null;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Actor#setStateChart(org.mdmk2.core.logic.StateChart)
	 */
	public void setStateChart(StateChart s) {
		chart = s;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Actor#setValue(java.lang.Class, java.lang.Object)
	 */
	public <V, A extends Attribute<V>> V setValue(Class<A> type, V value) {
		if(stats.containsKey(type)) return ((Attribute<V>)stats).setValue(value);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.AttributeListener#attributeChanged(org.mdmk2.core.logic.AttributeEvent)
	 */
	public <V> void attributeChanged(AttributeEvent<V> event) {
		if(chart != null) chart.attributeChanged(event);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.AttributeListener#getTypes()
	 */
	public List<AttributeEventType> getTypes() {
		return types;
	}

}
