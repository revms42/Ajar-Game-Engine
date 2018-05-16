package org.ajar.age.lwjgl.display;

import org.ajar.age.DefaultDecorator;
import org.ajar.age.Node;
import org.ajar.age.display.Displayable;

public class LWJGLDisplayable extends DefaultDecorator implements Displayable<LWJGLSurface> {
    /**
     * Constructs a new <code>DefaultDecorator</code> that is decorating the supplied <code>Node</code>.
     *
     * @param    node    the node that this decorator will decorate.
     */
    public LWJGLDisplayable(Node node) {
        super(node);
    }

    @Override
    public boolean isInView(LWJGLSurface cullingSurface) {
        return false;
    }
}
