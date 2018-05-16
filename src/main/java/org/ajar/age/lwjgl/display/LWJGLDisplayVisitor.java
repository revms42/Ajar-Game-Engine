package org.ajar.age.lwjgl.display;

import org.ajar.age.display.AbstractDisplayVisitor;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class LWJGLDisplayVisitor extends AbstractDisplayVisitor<LWJGLSurface, LWJGLDisplayable> {

    private LWJGLSurface surface;
    protected boolean initialized;

    public LWJGLDisplayVisitor(LWJGLSurface surface) {
        super(LWJGLDisplayable.class);

        this.surface = surface;
    }

    public LWJGLDisplayVisitor(LWJGLSurface.LWJGLSurfaceBuilder builder) {
        super(LWJGLDisplayable.class);

        this.surface = builder.build();
    }

    protected void init() {
        surface.enableVsync();
        surface.setVisable(true);
        initialized = true;
    }

    @Override
    public void visit(LWJGLDisplayable decorator) {

    }

    @Override
    protected void startProcess() {
        if(!initialized) init();
        surface.makeContextCurrent();
        surface.pushFrame();
        surface.clearSurface();
    }

    @Override
    protected void finishProcess() {

    }

    public LWJGLSurface getSurface() {
        return surface;
    }
}
