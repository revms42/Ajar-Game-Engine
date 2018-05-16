package org.ajar.age.lwjgl.display;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LWJGLDisplayTest {

    private LWJGLDisplayVisitor visitor;

    @Before
    public void setup() {
        LWJGLSurface.LWJGLSurfaceBuilder builder = new LWJGLSurface.LWJGLSurfaceBuilder();
        visitor = new LWJGLDisplayVisitor(builder);
    }

    @Test
    public void testDefault() throws InterruptedException {
        visitor.init();
        visitor.startProcess();
        visitor.finishProcess();
        Thread.sleep(2000);
    }

    @After
    public void teardown() {
        visitor.getSurface().dispose();
    }
}
