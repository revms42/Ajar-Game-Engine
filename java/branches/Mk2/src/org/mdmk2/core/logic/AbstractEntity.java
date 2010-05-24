/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 23-May-10 Matthew Stockbridge
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
 * AbstractActor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * @author mstockbridge
 * 23-May-10
 */
public class AbstractEntity implements Entity {
	protected HashMap<AttributeEventType,Vector<AttributeListener>> listeners;
	protected HashMap<AttributeType<?>,Attribute<?>> attributes;
	protected Vector<AttributeEventType> registered;
	protected boolean needsReRegister;
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#getValue(org.mdmk2.core.logic.AttributeType)
	 */
	@SuppressWarnings("unchecked")
	public <V> V getValue(AttributeType<V> type) {
		return (V)attributes.get(type).getValue();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#setValue(org.mdmk2.core.logic.AttributeType, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <V> V setValue(AttributeType<V> type, V value) {
		return ((Attribute<V>)attributes.get(type)).setValue(value);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.AttributeListener#attributeChanged(org.mdmk2.core.logic.AttributeEventType, org.mdmk2.core.logic.Attribute, org.mdmk2.core.logic.Actor)
	 */
	public void attributeChanged(AttributeEventType event, Attribute<?> attr, Entity actor) {
		if(listeners == null) return;
		for(AttributeListener listener : listeners.get(event)){
			listener.attributeChanged(event, attr, this);
		}
	}
	
	/**
	 * Adds the specified listener to the specified attribute to listen for events of the types given.
	 * <p>
	 * If the types are <code>null</code> then the listener is registered for all the events specified
	 * by the attribute.
	 * <p>
	 * If the attribute is <code>null</code> then all attributes are registered for the the specified
	 * events.
	 * <p>
	 * If both the attribute and types are <code>null</code> then all the attributes are registered for 
	 * all events.
	 * mstockbridge
	 * 23-May-10
	 * @param	listener	the listener to be registered.
	 * @param	attribute	the attribute to listen to.
	 * @param	types		the events to report on.
	 * @return	<code>true</code> if the listener was registered.
	 */
	public boolean addAttributeListener(AttributeListener listener, Attribute<?> attribute, AttributeEventType... types) {
		if(listener == null || !attributes.values().contains(attribute)) return false;
		
		boolean registered = false;
		if(attribute != null){
			if(types != null && types.length != 0){
				//For this attribute register the specified types.
				registered = registered || addToListeners(listener,attribute.addAttributeListener(this, types));
			}else{
				//For this attribute register for all events.
				registered = registered || addToListeners(listener,attribute.addAttributeListener(this, attribute.registeredEvents()));
			}
		}else{
			// No attribute, so register all across all attributes.
			for(Attribute<?> attr : attributes.values()){
				if(types != null && types.length != 0){
					//The types aren't null, register only for those types.
					registered = registered || addToListeners(listener,attr.addAttributeListener(this, types));
				}else{
					//The types are null register for all events.
					registered = registered || addToListeners(listener,attr.addAttributeListener(this, attr.registeredEvents()));
				}
			}
		}
		
		return registered;
	}
	
	private boolean addToListeners(AttributeListener listener, AttributeEventType... types){
		if(types == null || types.length == 0) return false;
		if(listeners == null) listeners = new HashMap<AttributeEventType,Vector<AttributeListener>>();
		
		for(AttributeEventType type : types){
			Vector<AttributeListener> register = listeners.get(type);
			
			if(register == null){
				register = new Vector<AttributeListener>();
				listeners.put(type, register);
			}
			
			register.add(listener);
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.ChangeObserver#registeredEvents()
	 */
	public List<AttributeEventType> registeredEvents() {
		if(registered == null) registered = new Vector<AttributeEventType>();
		
		if(needsReRegister){
			registered.removeAllElements();
			for(Attribute<?> attribute : attributes.values()){
				for(AttributeEventType event : attribute.registeredEvents()){
					if(!registered.contains(event)) registered.add(event);
				}
			}
			needsReRegister = false;
		}
		
		return registered;
	}

	/**
	 * Removes the specified AttributeListener from the given Attribute for the specified AttributeEventTypes.
	 * <p>
	 * If the types parameter is <code>null</code> then the listener will be unregistered for all event types.
	 * <p>
	 * If the attribute parameter is <code>null</code> then all attributes will be unregistered for events
	 * of the given types.
	 * <p>
	 * If both the attribute parameter and types parameter are <code>null</code> then the specified listener
	 * will be unregistered for all events on all attributes.
	 * mstockbridge
	 * 23-May-10
	 * @param	listener	the listener to be unregistered.
	 * @param	attribute	the attribute for which the listener will be unregistered.
	 * @param 	types		the types of events that will listener will stop recieving.
	 */
	public void removeAttributeListener(AttributeListener listener, Attribute<?> attribute, AttributeEventType... types) {
		if(listeners == null) return;
		
		if(types != null && types.length != 0){
			for(AttributeEventType type : types){
				Vector<AttributeListener> register = listeners.get(type);
				
				if(register != null){
					register.remove(listener);
					
					if(attribute != null){
						attribute.removeAttributeListener(this, type);
					}else{
						removeAllListenersOfType(type);
					}
				}
			}
		}else{
			if(attribute != null){
				attribute.removeAttributeListener(this, types);
			}else{
				for(Vector<AttributeListener> register : listeners.values()){
					if(register != null){
						register.remove(listener);
					}
				}
			}
		}
	}
	
	private void removeAllListenersOfType(AttributeEventType type){
		for(Attribute<?> a : attributes.values()){
			a.removeAttributeListener(this, type);
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#addAttribute(org.mdmk2.core.logic.AttributeType, org.mdmk2.core.logic.Attribute)
	 */
	public <V> void addAttribute(AttributeType<V> type, Attribute<V> attribute) {
		attributes.put(type, attribute);
		needsReRegister = true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#getAttribute(org.mdmk2.core.logic.AttributeType)
	 */
	@SuppressWarnings("unchecked")
	public <V> Attribute<V> getAttribute(AttributeType<V> type) {
		return (Attribute<V>)attributes.get(type);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#getAttributes()
	 */
	public Collection<Attribute<?>> getAttributes() {
		return attributes.values();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Entity#removeAttribute(org.mdmk2.core.logic.AttributeType)
	 */
	@SuppressWarnings("unchecked")
	public <V> Attribute<V> removeAttribute(AttributeType<V> type) {
		Attribute<V> attr = (Attribute<V>)attributes.remove(type);
		needsReRegister = true;
		return attr;
	}

}
