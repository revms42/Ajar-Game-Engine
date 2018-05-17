/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 17th, 2018 Matthew Stockbridge
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
 * org.ajar.age
 * StreamLogger.java
 */
package org.ajar.age;

import java.io.PrintStream;

/**
 * A simple implementation of the <code>LogWrapper</code> that uses a <code>PrintStream</code>
 * to print to.
 * @author revms42
 */
public class StreamLogger implements LogWrapper {

    private LogLevel level;
    private PrintStream stream;

    /**
     * Constructs a new <code>StreamLogger</code> around the Systems out stream.
     * @see #StreamLogger(PrintStream)
     */
    public StreamLogger() {
        this(System.out);
    }

    /**
     * Constructs a new <code>StreamLogger</code> around the provided <code>PrintStream</code>,
     * with a starting level of {@link org.ajar.age.LogWrapper.LogLevel#ERROR}
     * @param stream
     */
    public StreamLogger(PrintStream stream) {
        this.stream = stream;
        level = LogLevel.ERROR;
    }

    @Override
    public void log(LogLevel level, String message) {
        if(this.level.compareTo(level) <= 0){
            stream.println(level.name() + ": " + message);
        }
    }

    @Override
    public void log(LogLevel level, String message, Exception e) {
        if(this.level.compareTo(level) <= 0){
            stream.println(level.name() + ": " + message);
            e.printStackTrace(stream);
        }
    }

    @Override
    public void log(LogLevel level, Exception e) {
        if(this.level.compareTo(level) <= 0){
            stream.println(level.name() + ": Exception Occurred!");
            e.printStackTrace(stream);
        }
    }

    @Override
    public LogLevel getLogLevel() {
        return level;
    }

    @Override
    public void setLogLevel(LogLevel level) {
        this.level = level;
    }
}
