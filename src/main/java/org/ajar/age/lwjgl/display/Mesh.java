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
 * Mesh.java
 */
package org.ajar.age.lwjgl.display;

import java.nio.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

/**
 * The Mesh class provides a way of loading geometry for displayable objects into the framework and encapsulates the
 * methods needed by lwjgl to load in that data and have it bound in a way that makes it easy to display.
 * @see LWJGLDisplayable
 * @author revms42
 */
public class Mesh implements GLGeometry {

    private int vaoId;

    private int vboId;

    private int vertexCount;

    public <N extends Number> Mesh(boolean dynamic, Point<N>... positions) {
        this(dynamic, Arrays.asList(positions));
    }

    public <N extends Number> Mesh(boolean dynamic, List<Point<N>> positions) {
        this(
            dynamic,
            positions.stream()
                .flatMap(
                    p -> p.getComponents().stream()
                ).collect(Collectors.toList()),
            positions.get(0).getDimension()
        );
    }

    public <N extends Number> Mesh(boolean dynamic, int pointsPerVert, N... positions) {
        this(dynamic, Arrays.asList(positions), pointsPerVert);
    }

    private <N extends Number> Mesh(boolean dynamic, List<N> positions, int pointsPerVert) {
        if(positions == null || positions.isEmpty()) throw new IllegalArgumentException("Null values are not excepted");

        Class<? extends Number> c = positions.get(0).getClass();

        if(c == Long.class) throw new IllegalArgumentException("Long values not supported in LWJGL Meshes");

        vertexCount = positions.size() / pointsPerVert;
        int mode = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;

        Buffer verticesBuffer = null;
        try {
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);

            if(c == Double.class) {
                verticesBuffer = GLGeometry.bufferDouble(positions);
                glBufferData(GL_ARRAY_BUFFER, (DoubleBuffer) verticesBuffer, mode);
                glVertexAttribPointer(0, pointsPerVert, GL_DOUBLE, false, 0, 0);
            } else if(c == Float.class) {
                verticesBuffer = GLGeometry.bufferFloat(positions);
                glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) verticesBuffer, mode);
                glVertexAttribPointer(0, pointsPerVert, GL_FLOAT, false, 0, 0);
            } else if(c == Integer.class) {
                verticesBuffer = GLGeometry.bufferInt(positions);
                glBufferData(GL_ARRAY_BUFFER, (IntBuffer) verticesBuffer, mode);
                glVertexAttribPointer(0, pointsPerVert, GL_INT, false, 0, 0);
            } else if(c == Short.class) {
                verticesBuffer = GLGeometry.bufferShort(positions);
                glBufferData(GL_ARRAY_BUFFER, (ShortBuffer) verticesBuffer, mode);
                glVertexAttribPointer(0, pointsPerVert, GL_SHORT, false, 0, 0);
            } else if(c == Byte.class) {
                verticesBuffer = GLGeometry.bufferByte(positions);
                glBufferData(GL_ARRAY_BUFFER, (ByteBuffer) verticesBuffer, mode);
                glVertexAttribPointer(0, pointsPerVert, GL_BYTE, false, 0, 0);
            }


            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindVertexArray(0);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
