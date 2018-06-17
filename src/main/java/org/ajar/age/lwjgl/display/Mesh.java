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
import java.util.ArrayList;
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

    // Note: not sure if these need to be final....
    private final int vaoId;
    private final int vboId;

    private final int idxVboId;

    private final int colorsVboId;

    private final int vertexCount;

    /*
     * There is a really big caveat here: This is all dependent on getting the right shaders set up.
     * Right now there are all sorts of assumptions being made about how things are organized and laid out,
     * which may be what we want, or we may not... but before some testing is completed it's a little hard to tell.
     * <p>
     * There is a very good case to be made here that you should be allowing N lists of data and that you should
     * allow people to associate them directly to a shader. It remains to be seen if that is the way we'll take this,
     * but until we know we can use more than position, index, color, and texture for meshes it's really just
     * hypothetical.
     */
    protected <N extends Number> Mesh(boolean dynamic, List<Point<N>> positions, List<Integer> indices, List<Integer> colors) {
        if(positions == null || positions.isEmpty()) throw new IllegalArgumentException("Null values are not excepted");
        if(indices == null || indices.isEmpty()) throw new IllegalArgumentException("Null values are not excepted");

        vaoId = glGenVertexArrays();

        glBindVertexArray(vaoId);
        vboId = bufferPositionArrayData(positions, dynamic);
        glBindVertexArray(0);

        int mode = dynamic? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;

        // You don't have to use indices if you don't want... TODO: It will mean we have to make a different draw call.
        if(indices != null && indices.size() > 0) {
            vertexCount = indices.size();
            idxVboId = bufferIndicesArrayData(indices, mode);
            //TODO: Note: draw using glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        }else{
            vertexCount = positions.size();
            idxVboId = 0;
            //TODO: Note: draw using glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
        }

        // You don't have to use colors if you don't want...
        if(colors != null && colors.size() > 0) {
            colorsVboId = bufferColorsArrayData(colors);
        }else{
            colorsVboId = 0;
        }
    }

    /**
     * Uses glBufferData to load a set of points into memory.
     * @param positions
     * @param dynamic
     * @param <N>
     * @return
     */
    public static <N extends Number> int bufferPositionArrayData(List<Point<N>> positions, boolean dynamic) {
        return Mesh.bufferPositionArrayData(
                positions.stream()
                        .flatMap(
                                p -> p.getComponents().stream()
                        ).collect(Collectors.toList()),
                positions.get(0).getDimension(),
                dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW
        );
    }

    /**
     * Uses glBufferData to load a set of positions into memory.
     * @param positions
     * @param pointsPerVert
     * @param mode
     * @param <N>
     * @return
     */
    private static <N extends Number> int bufferPositionArrayData(List<N> positions, int pointsPerVert, int mode) {
        Class<? extends Number> c = positions.get(0).getClass();

        if(c == Long.class) throw new IllegalArgumentException("Long values not supported in LWJGL Meshes");

        int vboId;
        Buffer verticesBuffer = null;
        try {
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
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }

        return vboId;
    }

    /**
     * Use glBufferData to load a series of vertex indices for a mesh into memory.
     * @param indices
     * @return
     */
    public static int bufferIndicesArrayData(List<Integer> indices, int mode) {
        int idxVboId;
        Buffer indicesBuffer = null;
        try {
            idxVboId = glGenBuffers();

            indicesBuffer = GLGeometry.bufferInt(indices);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) indicesBuffer, GL_STATIC_DRAW);
        } finally {
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
            // Not explicity shown, but probably needed.
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        return idxVboId;
    }

    /**
     * Use glBufferData to load a series of vertex colors (Int) for a mesh into memory.
     * @param indices
     * @return
     */
    public static int bufferColorsArrayData(List<Integer> indices) {
        int colorsId;
        Buffer indicesBuffer = null;
        try {
            colorsId = glGenBuffers();

            indicesBuffer = GLGeometry.bufferInt(indices);
            glBindBuffer(GL_ARRAY_BUFFER, colorsId);
            glBufferData(GL_ARRAY_BUFFER, (IntBuffer) indicesBuffer, GL_STATIC_DRAW);
        } finally {
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
            // Not explicity shown, but probably needed.
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        return colorsId;
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
        glDeleteBuffers(idxVboId);
        glDeleteBuffers(colorsVboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public class MeshBuilder<N extends Number> {
        private final List<Point<N>> vertices;
        private final List<Integer> indices;
        private final List<Integer> colors;
        private boolean isDynamic;

        public MeshBuilder() {
            vertices = new ArrayList<>();
            indices = new ArrayList<>();
            colors = new ArrayList<>();
        }

        public MeshBuilder<N> addVerticies(List<Point<N>> points) {
            vertices.addAll(points);
            return this;
        }

        public MeshBuilder<N> addVerticies(Point<N>... points) {
            return addVerticies(Arrays.asList(points));
        }

        public MeshBuilder<N> addIndicies(List<Integer> points) {
            indices.addAll(points);
            return this;
        }

        public MeshBuilder<N> addIndicies(Integer... points) {
            return addIndicies(Arrays.asList(points));
        }

        public MeshBuilder<N> addColors(List<Integer> c) {
            colors.addAll(c);
            return this;
        }

        public MeshBuilder<N> addColors(Integer... c) {
            return addColors(Arrays.asList(c));
        }

        public MeshBuilder setDynamic(boolean dynamic) {
            this.isDynamic = dynamic;
            return this;
        }

        public Mesh build() {
            return new Mesh(isDynamic, vertices, indices, colors);
        }
    }
}
