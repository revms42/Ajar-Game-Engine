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
 * LWJGLSurface.java
 */
import org.joml.Matrix4f;
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

    int width;
    int height;
    private boolean resized;
    private Matrix4f projectionMatrix;

    protected LWJGLSurface(long window, Matrix4f projectionMatrix) {
        this.window = window;
        this.projectionMatrix = projectionMatrix;
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
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void update() {
        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    public void dispose() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    void setResized(boolean value) {
        this.resized = value;
    }

    boolean isResized() {
        return resized;
    }

    public static Matrix4f createProjectionMatrix(float fov, float aspectRatio, float nearLimit, float farLimit) {
        return new Matrix4f().perspective(fov, aspectRatio, nearLimit, farLimit);
    }

    public static class LWJGLSurfaceBuilder {

        // Sensible default values;
        private static final float FOV = (float) Math.toRadians(60.0f);
        private static final float Z_NEAR = 0.01f;
        private static final float Z_FAR = 1000.f;

        private boolean windowHints = true;
        private Map<Integer, Integer> glfwWindowHints = new HashMap<>();
        private int windowWidth = 300;
        private int windowHeight = 300;
        private String windowTitle = "LWJGL Ajar Game";
        private long monitor = NULL;
        private long share = NULL;
        private GLFWKeyCallbackI keyCallback;
        private float nearField = Z_NEAR;
        private float farField = Z_FAR;
        private float fov = FOV;

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

        public LWJGLSurfaceBuilder nearViewLimit(float near){
            this.nearField = near;
            return this;
        }

        public LWJGLSurfaceBuilder farViewLimit(float far){
            this.farField = far;
            return this;
        }

        public LWJGLSurfaceBuilder fieldOfView(float fov){
            this.fov = (float) Math.toRadians(fov);
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

            float aspectRatio = 16.0f/9.0f;

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

                // Get the actual aspect ratio.
                aspectRatio = ((float) pWidth.get(0))/((float) pHeight.get(0));
            } // the stack frame is popped automatically

            Matrix4f projectionMatrix = createProjectionMatrix(fov, aspectRatio, nearField, farField);

            // This line is critical for LWJGL's interoperation with GLFW's
            // OpenGL context, or any context that is managed externally.
            // LWJGL detects the context that is current in the current thread,
            // creates the GLCapabilities instance and makes the OpenGL
            // bindings available for use.
            GL.createCapabilities();

            final LWJGLSurface surface = new LWJGLSurface(window, projectionMatrix);

            // Setup resize callback
            glfwSetFramebufferSizeCallback(window, (nWindow, width, height) -> {
                surface.width = width;
                surface.height = height;
                surface.setResized(true);
            });

            //TODO: Make this configurable at some point.
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

            return surface;
        }
    }
}
