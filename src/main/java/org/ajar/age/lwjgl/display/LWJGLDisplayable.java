/*
 * This file is part of Ajar Game Engine
 * Copyright (C) May 18th, 2018 Matthew Stockbridge
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
 * LWJGLDisplayable.java
 */
package org.ajar.age.lwjgl.display;

import org.ajar.age.DefaultDecorator;
import org.ajar.age.Node;
import org.ajar.age.display.Displayable;

/**
 * LWJGLDisplayable is a <code>Displayable</code> implementation for the LWJGL library, using OpenGL. It contains the
 * necessary data to define an object that can be drawn in an OpenGL context.
 * @see Displayable
 * @see DefaultDecorator
 * @author revms42
 */
public class LWJGLDisplayable extends DefaultDecorator implements Displayable<LWJGLSurface> {

    private Mesh mesh;

    public LWJGLDisplayable(Node node) {
        super(node);
    }

    @Override
    public boolean isInView(LWJGLSurface cullingSurface) {
        return false;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return this.mesh;
    }
}
