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
 * GLGeometry.java
 */
package org.ajar.age.lwjgl.display;

import org.lwjgl.system.MemoryUtil;

import java.nio.*;
import java.util.List;

/**
 * This interface provides some static methods for turning lists of numbers into properly buffered data for use in
 * lwjgl display classes.
 * @see Mesh
 * @author revms42
 */
public interface GLGeometry {

    static <N extends Number> DoubleBuffer bufferDouble(List<N> n) {
        DoubleBuffer buffer = MemoryUtil.memAllocDouble(n.size());
        n.stream().forEachOrdered(num -> buffer.put(num.doubleValue()));
        buffer.flip();
        return buffer;
    }

    static <N extends Number> FloatBuffer bufferFloat(List<N> n) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(n.size());
        n.stream().forEachOrdered(num -> buffer.put(num.floatValue()));
        buffer.flip();
        return buffer;
    }

    static <N extends Number> LongBuffer bufferLong(List<N> n) {
        LongBuffer buffer = MemoryUtil.memAllocLong(n.size());
        n.stream().forEachOrdered(num -> buffer.put(num.longValue()));
        buffer.flip();
        return buffer;
    }

    static <N extends Number> IntBuffer bufferInt(List<N> n) {
        IntBuffer buffer = MemoryUtil.memAllocInt(n.size());
        n.stream().forEachOrdered(num -> buffer.put(num.intValue()));
        buffer.flip();
        return buffer;
    }

    static <N extends Number> ShortBuffer bufferShort(List<N> n) {
        ShortBuffer buffer = MemoryUtil.memAllocShort(n.size());
        n.stream().forEachOrdered(num -> buffer.put(num.shortValue()));
        buffer.flip();
        return buffer;
    }

    static <N extends Number> ByteBuffer bufferByte(List<N> n) {
        ByteBuffer buffer = MemoryUtil.memAlloc(n.size());
        n.stream().forEachOrdered(num -> buffer.put(num.byteValue()));
        buffer.flip();
        return buffer;
    }
}
