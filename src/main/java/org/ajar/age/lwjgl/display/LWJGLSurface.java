package org.ajar.age.lwjgl.display;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LWJGLSurface {

    private long window;

    protected LWJGLSurface(long window) {
        this.window = window;
    }

    public void setVisable(boolean visible){
        if(glfwGetCurrentContext() != window) makeContextCurrent();
        // Make the window visible
        glfwShowWindow(window);
    }

    public void makeContextCurrent() {
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
    }

    public void enableVsync() {
        if(glfwGetCurrentContext() != window) makeContextCurrent();
        // Enable v-sync
        glfwSwapInterval(1);
    }

    public void clearSurface() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color TODO: Make this configurable.
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        //TODO: Figure out which of these need to be done last.
        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    public void pushFrame() {
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
    }

    public void dispose() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static class LWJGLSurfaceBuilder {

        private boolean windowHints = true;
        private Map<Integer, Integer> glfwWindowHints = new HashMap<>();
        private int windowWidth = 300;
        private int windowHeight = 300;
        private String windowTitle = "LWJGL Ajar Game";
        private long monitor = NULL;
        private long share = NULL;
        private GLFWKeyCallbackI keyCallback;

        public LWJGLSurfaceBuilder() {
            addGlfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
            addGlfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        }

        public LWJGLSurfaceBuilder defaultWindowHintsOff() {
            windowHints = false;
            return this;
        }

        public LWJGLSurfaceBuilder defaultWindowHintsOn() {
            windowHints = true;
            return this;
        }

        public LWJGLSurfaceBuilder addGlfwWindowHint(Integer code, Integer value) {
            glfwWindowHints.put(code, value);
            return this;
        }

        public LWJGLSurfaceBuilder windowWidth(int width){
            this.windowWidth = width;
            return this;
        }

        public LWJGLSurfaceBuilder windowHeight(int height){
            this.windowHeight = height;
            return this;
        }

        public LWJGLSurfaceBuilder windowTitle(String title){
            this.windowTitle = title;
            return this;
        }

        public LWJGLSurfaceBuilder monitor(long monitor){
            this.monitor = monitor;
            return this;
        }

        public LWJGLSurfaceBuilder share(long share){
            this.share = share;
            return this;
        }

        public LWJGLSurfaceBuilder keyCallback(GLFWKeyCallbackI callback){
            this.keyCallback = callback;
            return this;
        }

        public LWJGLSurface build() {
            // Setup an error callback. The default implementation
            // will print the error message in System.err.
            GLFWErrorCallback.createPrint(System.err).set();

            // Initialize GLFW. Most GLFW functions will not work before doing this.
            if ( !glfwInit() )
                throw new IllegalStateException("Unable to initialize GLFW");

            // Configure GLFW
            if(windowHints) glfwDefaultWindowHints();
            for(Integer code : glfwWindowHints.keySet()){
                glfwWindowHint(code, glfwWindowHints.get(code));
            }

            // Create the window
            long window = glfwCreateWindow(windowWidth, windowHeight, windowTitle, monitor, share);
            if ( window == NULL )
                throw new RuntimeException("Failed to create the GLFW window");

            // Setup a key callback. It will be called every time a key is pressed, repeated or released.
            if(keyCallback == null){
                glfwSetKeyCallback(window, (wind, key, scancode, action, mods) -> {
                    if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                        glfwSetWindowShouldClose(wind, true); // We will detect this in the rendering loop
                });
            }else{
                glfwSetKeyCallback(window,keyCallback);
            }

            return new LWJGLSurface(window);
        }
    }
}
