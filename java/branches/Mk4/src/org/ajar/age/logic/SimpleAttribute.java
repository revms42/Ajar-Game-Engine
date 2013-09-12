/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 8, 2013 Matthew Stockbridge
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
 * SimpleAttribute.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

/**
 * SimpleAttribute is an interface for an Attribute that isn't expected to require
 * special initialization. As such, it defines a default value using {@link #getDefaultValue()}.
 * This value is assigned to the Attribute when it is added to an Attributes object.
 * 
 * @author reverend
 * @see org.ajar.age.logic.HashAttributes#setAttribute(SimpleAttribute)
 *
 */
public interface SimpleAttribute<V> extends Attribute<V> {

	/**
	 * Gets the default value of this Attribute, used to initialize it when it is added to an
	 * Attributes object.
	 * @return	<code>V</code>	the default value of this Attribute.
	 */
	public V getDefaultValue();
}
