/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 26-May-10 Matthew Stockbridge
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
 * LinearAttribute.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author mstockbridge
 * 26-May-10
 */
public class LinearAttribute<A extends Number & Comparable<A>> implements Attribute<A> {
	
	public static enum LinearAttributeEvent implements AttributeEventType {
		VALUE_CHANGED,
		MIN_REACHED,
		MAX_REACHED;
	}
	
	public static class LinearAttributeType<A> implements AttributeType<A> {}
	
	protected final HashMap<AttributeEventType,Vector<AttributeListener>> listeners;
	public final LinearAttributeType<A> type;
	
	protected A min;
	protected A max;
	protected A nominal;
	protected A value;
	
	public LinearAttribute(A value, A max, A min, A nominal){
		listeners = new HashMap<AttributeEventType,Vector<AttributeListener>>();
		type = new LinearAttributeType<A>();
		
		this.min = min;
		this.max = max;
		this.nominal = nominal;
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Attribute#addAttributeListener(org.mdmk2.core.logic.AttributeListener, org.mdmk2.core.logic.AttributeEventType[])
	 */
	public AttributeEventType[] addAttributeListener(AttributeListener listener, AttributeEventType... types) {
		Vector<AttributeEventType> registered = null;
		if(types == null || types.length == 0){
			types = registeredEvents();
		}
		for(AttributeEventType type : types){
			if(isRegistered(type)){
				Vector<AttributeListener> typeListeners = listeners.get(type);
				
				if(typeListeners == null){
					typeListeners = new Vector<AttributeListener>();
					listeners.put(type, typeListeners);
				}
				
				typeListeners.add(listener);
				
				if(registered == null){
					registered = new Vector<AttributeEventType>();
				}
				
				registered.add(type);
			}
		}

		return registered == null ? new AttributeEventType[0] : registered.toArray(new AttributeEventType[registered.size()]);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Attribute#getValue()
	 */
	public A getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Attribute#registeredEvents()
	 */
	public AttributeEventType[] registeredEvents() {
		return LinearAttributeEvent.values();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Attribute#removeAttributeListener(org.mdmk2.core.logic.AttributeListener, org.mdmk2.core.logic.AttributeEventType[])
	 */
	public void removeAttributeListener(AttributeListener listener, AttributeEventType... types) {
		if(types == null || types.length == 0){
			types = registeredEvents();
		}
		
		for(AttributeEventType type : types){
			if(listeners.containsKey(type)){
				Vector<AttributeListener> typeListener = listeners.get(type);
				
				typeListener.remove(listener);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Attribute#setValue(java.lang.Object)
	 */
	public A setValue(A value) {
		if(value.compareTo(min) <= 0){
			this.value = clone(min);
		}else if(value.compareTo(max) >= 0){
			this.value = clone(max);
		}else if(!(value.compareTo(this.value) == 0)){
			this.value = value;
		}
		
		return this.value;
	}
	
	public A getMax() {
		return max;
	}
	
	public void setMax(A max) {
		if(max.compareTo(min) < 1){
			this.max = clone(min);
		}else{
			this.max = max;
		}
		
		setValue(value);
	}
	
	public A getMin() {
		return min;
	}
	
	public void setMin(A min) {
		if(min.compareTo(max) > 1){
			this.min = clone(max);
		}else{
			this.min = min;
		}
		
		setValue(value);
	}
	
	public A getNominal() {
		return nominal;
	}
	
	public void setNominal(A nominal) {
		this.nominal = nominal;
	}
	
	@SuppressWarnings("unchecked")
	private A clone(A number){
		A value = null;
		if(number instanceof Integer){
			value = (A)new Integer(number.intValue());
		}else if(number instanceof Double){
			value = (A)new Double(number.doubleValue());
		}else if(number instanceof Float){
			value = (A)new Float(number.floatValue());
		}else if(number instanceof Long){
			value = (A)new Long(number.longValue());
		}else if(number instanceof Short){
			value = (A)new Short(number.shortValue());
		}else if(number instanceof Byte){
			value = (A)new Byte(number.byteValue());
		}else{
			value = number;
		}
		return value;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Attribute#isRegistered(org.mdmk2.core.logic.AttributeEventType)
	 */
	public boolean isRegistered(AttributeEventType type) {
		AttributeEventType[] types = registeredEvents();
		return Arrays.binarySearch(types, 0, types.length, type) > 0;
	}
}
