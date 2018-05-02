package org.ajar.age.display;
/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 26, 2013 Matthew Stockbridge
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
 * org.ajar.age.display
 * Displayable.java
 */

import org.ajar.age.Decorator;

/**
 * Classes that implement this interface indicate that they are capable of being displayed
 * by a display loop (actual implementation of which can vary).
 * @param <S> the type of the culling surface used to determine whether this object is in the
 *           field of view.
 * @see DisplayVisitor
 * @author revms42
 */
public interface Displayable<S> extends Decorator {

    /**
     * Determines whether this object is in view by providing it with the culling surface and
     * asking it if it is in view.
     * @param cullingSurface the surface to compare to.
     * @return <code>true</code> if the object is in view.
     */
    public boolean isInView(S cullingSurface);
}
