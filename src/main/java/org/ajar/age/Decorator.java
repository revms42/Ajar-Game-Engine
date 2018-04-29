/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Dec 1, 2010 Matthew Stockbridge
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
 * age
 * org.ajar.age
 * Decorator.java
 */
package org.ajar.age;
/**
 * The Decorator interface provides the ability to create methods that
 * can be called against {@link Node} objects extending their properties
 * and providing new methods, or making properties and methods more accessible
 * to {@link Visitor}s.
 * <p>
 * For example, a collision decorator might pull values of a <code>
 * Node</code>'s position and size to create a bounding box that could
 * be used for collision detection.
 * <p>
 * Decorators should generally only delegate their inheritated 
 * <code>Node</code> methods to the actual instance of a <code>
 * Node</code> object that they're decorating.
 * @see Node
 * @see Visitor
 * @see DefaultDecorator
 * @author revms42
 * @since 0.0.0.153
 */
public interface Decorator extends Node {

    /**
     * Queries this Decorator to determine if it needs to be updated.
     * @return <code>true</code> if this <code>Node</code> should be
     * updated.
     */
    public boolean needsUpdate();
}