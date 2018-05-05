package org.ajar.age.resource;
/*
 * This file is part of Ajar Game Engine
 * Copyright (C) May 4, 2018 Matthew Stockbridge
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
 * org.ajar.age.resource
 * ResourceSource.java
 */
import org.ajar.age.Decorator;

import java.io.InputStream;

/**
 * Decorators need a way to get the resources that they use into the game. ResourceSource decorators are that way.
 * ResourceSources load resources from an input stream, then retrieve them on demand for the specified node.
 * @param <T> The type of resource this ResourceSource loads.
 * @param <D> The type of decorator this ResourceSource loads resources for.
 * @see Decorator
 * @author revms42
 */
public interface ResourceSource<T, D extends Decorator> {

    /**
     * Load resources from the given resource stream.
     * @param stream the stream to load resources from.
     */
    public void load(InputStream stream);

    /**
     * Retrieves a resource that matches the internal state of the given decorator.
     * @param node the <code>Decorator</code> to retrieve the resource for.
     * @return the resource for the given <code>Decorator</code>.
     */
    public T retrieve(D node);
}
