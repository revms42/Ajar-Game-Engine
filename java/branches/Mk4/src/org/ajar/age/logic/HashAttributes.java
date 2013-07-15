/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 6, 2013 Matthew Stockbridge
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
 * HashAttributes.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import java.util.HashMap;

import org.ajar.age.Attributes;

/**
 * @author reverend
 *
 */
public class HashAttributes implements Attributes {

	protected final HashMap<Attribute<?>,Object> attributeMap;
	
	public HashAttributes(){
		attributeMap = new HashMap<Attribute<?>,Object>();
	}
	
	@SuppressWarnings("unchecked")
	public <V> V getAttribute(Attribute<V> attribute){
		return (V)attributeMap.get(attribute);
	}
	
	public <V> void setAttribute(Attribute<V> attribute, V value){
		attributeMap.put((Attribute<?>) attribute, value);
	}
	
	public HashAttributes clone(){
		HashAttributes attrs = new HashAttributes();
		
		for(Attribute<?> attr : attributeMap.keySet()){
			setCloneAttribute(attr,attrs);
		}
		
		return attrs;
	}
	
	@SuppressWarnings("unchecked")
	private <V> V copyValue(Attribute<V> attr){
		return attr.copy((V)attributeMap.get(attr));
	}
	
	private <V> void setCloneAttribute(Attribute<V> attr, HashAttributes attrs){
		attrs.setAttribute(attr, copyValue(attr));
	}
	
	
}
