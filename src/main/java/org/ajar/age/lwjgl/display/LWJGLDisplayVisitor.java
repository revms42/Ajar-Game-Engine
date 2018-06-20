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
 * LWJGLDisplayVisitor.java
 */
import org.ajar.age.display.AbstractDisplayVisitor;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class LWJGLDisplayVisitor extends AbstractDisplayVisitor<LWJGLSurface, LWJGLDisplayable> {

    private LWJGLSurface surface;
    private GLShaderProgram shaderProgram;
    protected boolean initialized;

    public LWJGLDisplayVisitor(LWJGLSurface surface) {
        super(LWJGLDisplayable.class);

        this.surface = surface;
    }

    public LWJGLDisplayVisitor(LWJGLSurface.LWJGLSurfaceBuilder builder) {
        super(LWJGLDisplayable.class);

        this.surface = builder.build();
    }


    public void setShaderProgram(GLShaderProgram program) {
        this.shaderProgram = program;
    }

    protected void init() {
        surface.enableVsync();
        surface.setVisable(true);
        initialized = true;
        //TODO: At this point we may need to know which uniform(s) to use.
    }

    @Override
    public void visit(LWJGLDisplayable decorator) {
        glBindVertexArray(decorator.getMesh().getVaoId());
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES, decorator.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    protected void startProcess() {
        if(!initialized) init();
        surface.makeContextCurrent();
        surface.clearSurface();

        if(surface.isResized()) {
            glViewport(0, 0, surface.width, surface.height);
            surface.setResized(false);
        }

        shaderProgram.bind();
        shaderProgram.setUniform(surface.getProjectionMatrix());
    }

    @Override
    protected void finishProcess() {
        //TODO: These first two might have to be called after each visit.
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shaderProgram.unbind();

        //TODO: I think this is where this goes.
        surface.update();
    }

    public LWJGLSurface getSurface() {
        return surface;
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }
}
