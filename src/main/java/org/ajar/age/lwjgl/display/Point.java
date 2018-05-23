/*
 * This file is part of Ajar Game Engine
 * Copyright (C) May 23rd, 2018 Matthew Stockbridge
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
 * org.ajar.age.lwjgl.display
 * Point.java
 */
package org.ajar.age.lwjgl.display;

import java.util.Arrays;
import java.util.List;

/**
 * The <code>Point</code> class is a generic data class for an <code>N</code> dimensional point in space. Since
 * LWJGL will let you specify two, three, or four dimensional points, and those points can be virtually any primitive
 * type, this class lets points be specified in a way that will help them be loaded into memory without being tied
 * specifically to one primitive type or dimension.
 * @param <D> the primitive type this point is represented in.
 */
public class Point<D extends Number> {

    private final List<D> components;

    /**
     * Creates an <code>N</code> dimensional point where <code>N</code> is the length of <code>components</code>.
     * @param components
     */
    public Point(D... components) {
        this(Arrays.asList(components));
    }

    /**
     * Creates an <code>N</code> dimensional point where <code>N</code> is the size of the list <code>components</code>.
     * @param components
     */
    public Point(List<D> components){
        this.components = components;
    }

    /**
     * Returns the list of dimensional components.
     * @return the positional values of this point in all dimensions.
     */
    public List<D> getComponents() {
        return components;
    }

    /**
     * The number of dimensions this point is represented in.
     * @return the size of the dimensional components collection.
     */
    public int getDimension() {
        return components.size();
    }
}
