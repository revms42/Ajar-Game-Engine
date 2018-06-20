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
 * LWJGLShaderProgram.java
 */
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class LWJGLShaderProgram implements GLShaderProgram {

    private String selectedUniform;

    private final int programId;
    private final Map<String, Integer> uniforms;

    protected LWJGLShaderProgram(int programId) {
        this.programId = programId;
        this.uniforms = new HashMap<>();
    }

    @Override
    public void bind() {
        glUseProgram(programId);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }

    @Override
    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    @Override
    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);

        if(selectedUniform == null) selectedUniform = uniformName;
    }

    @Override
    public void setUniform(Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(selectedUniform), false, fb);
        }
    }

    @Override
    public void selectUniform(String selectedUniform) throws Exception {
        if(!uniforms.containsKey(selectedUniform)) {
            createUniform(selectedUniform);
        }
        this.selectedUniform = selectedUniform;
    }

    public static class LWJGLShaderProgramBuilder {

        private String vertexShader;
        private String fragmentShader;
        private String projMatrixShader;

        private int createProgram() throws Exception {
            int programId = glCreateProgram();

            if (programId == 0) {
                throw new Exception("Could not create Shader");
            }

            return programId;
        }

        private int createVertexShader(int programId) throws Exception {
            return createShader(vertexShader, GL_VERTEX_SHADER, programId);
        }

        private int createFragmentShader(int programId) throws Exception {
            return createShader(fragmentShader, GL_FRAGMENT_SHADER, programId);
        }

        private int createShader(String shaderCode, int shaderType, int programId) throws Exception {
            int shaderId = glCreateShader(shaderType);
            if (shaderId == 0) {
                throw new Exception("Error creating shader. Type: " + shaderType);
            }

            glShaderSource(shaderId, shaderCode);
            glCompileShader(shaderId);

            if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
                throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
            }

            glAttachShader(programId, shaderId);

            return shaderId;
        }


        private void link(int programId) throws Exception {
            glLinkProgram(programId);
            if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
                throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
            }

            int vertexShaderId = createVertexShader(programId);
            int fragmentShaderId = createFragmentShader(programId);

            if (vertexShaderId != 0) {
                glDetachShader(programId, vertexShaderId);
            }
            if (fragmentShaderId != 0) {
                glDetachShader(programId, fragmentShaderId);
            }

            glValidateProgram(programId);
            if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
                System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
            }
        }

        public LWJGLShaderProgramBuilder setVertexShader(String vertexShader) {
            this.vertexShader = vertexShader;
            return this;
        }

        public LWJGLShaderProgramBuilder setFragmentShader(String fragmentShader) {
            this.fragmentShader = fragmentShader;
            return this;
        }

        public LWJGLShaderProgramBuilder setProjectionMatrixShader(String projMatrixShader) {
            this.projMatrixShader = projMatrixShader;
            return this;
        }

        public LWJGLShaderProgram build() throws Exception {
            int program = createProgram();
            link(program);
            LWJGLShaderProgram shader = new LWJGLShaderProgram(program);

            if(projMatrixShader != null) {
                shader.createUniform(projMatrixShader);
            }

            return shader;
        }
    }
}
