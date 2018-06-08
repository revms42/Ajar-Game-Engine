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
import static org.lwjgl.opengl.GL20.*;

public class LWJGLShaderProgram implements GLShaderProgram {

    private final int programId;

    protected LWJGLShaderProgram(int programId) {
        this.programId = programId;
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    public class LWJGLShaderProgramBuilder {

        private String vertexShader;
        private String fragmentShader;

        private int createProgram() throws Exception {
            int programId = glCreateProgram();

            if (programId == 0) {
                throw new Exception("Could not create Shader");
            }

            return programId;
        }

        private int createVertexShader() throws Exception {
            return createShader(vertexShader, GL_VERTEX_SHADER);
        }

        private int createFragmentShader() throws Exception {
            return createShader(fragmentShader, GL_FRAGMENT_SHADER);
        }

        private int createShader(String shaderCode, int shaderType) throws Exception {
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

            int vertexShaderId = createVertexShader();
            int fragmentShaderId = createFragmentShader();

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

        public LWJGLShaderProgram build() throws Exception {
            int program = createProgram();
            link(program);
            return new LWJGLShaderProgram(program);
        }
    }
}
