package org.ajar.age.lwjgl.display;

import org.lwjgl.system.MemoryUtil;

import java.nio.*;
import java.util.List;

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
