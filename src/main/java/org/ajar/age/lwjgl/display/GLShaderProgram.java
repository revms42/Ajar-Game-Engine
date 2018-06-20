package org.ajar.age.lwjgl.display;
/*
 * This file is part of Ajar Game Engine
 * Copyright (C) June 7th, 2018 Matthew Stockbridge
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
 * GLShaderProgram.java
 */

import org.joml.Matrix4f;

/**
 * A GLShaderProgram provides a convenient wrapper for a GL Shader Program, as well as convenience methods for dealing
 * with binding and unbinding.
 */
public interface GLShaderProgram {

    /**
     * This method needs to be called before a render pass.
     */
    void bind();

    /**
     * This method needs to be called after a render pass.
     */
    void unbind();

    /**
     * This method needs to be called to free the program from memory.
     */
    void cleanup();

    /**
     * This method is called to create a "uniform", e.g. a projection matrix.
     * <p>
     * TODO: Not super happy with this method. I don't know if I want to have separate create and set methods....
     * @param uniformName
     * @throws Exception
     */
    void createUniform(String uniformName) throws Exception;

    /**
     * Sets a previously selected uniform to a matrix value.
     * <p>
     * TODO: Not super happy with this method either, but I need to see how it's used before I change them.
     * @param value
     */
    void setUniform(Matrix4f value);

    /**
     * Get the uniform used as the projection matrix.
     * @return
     */
    void selectUniform(String uniformName) throws Exception;
}
