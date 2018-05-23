package org.ajar.age.lwjgl.display;

import java.util.Arrays;
import java.util.List;

public class Point<N extends Number> {

    private final List<N> components;

    public Point(N... components) {
        this(Arrays.asList(components));
    }

    public Point(List<N> components){
        this.components = components;
    }

    public List<N> getComponents() {
        return components;
    }

    public int getDimension() {
        return components.size();
    }
}
