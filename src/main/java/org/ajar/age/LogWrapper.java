/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 16th, 2018 Matthew Stockbridge
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
 * LogWrapper.java
 */
package org.ajar.age;

/**
 * A basic interface to allow wrapping of native loggers (e.g. Android or Java) within the game engine, without having
 * to specify specific classes bound to the game engine.
 * @author revms42
 */
public interface LogWrapper {

    /**
     * The logging levels of the LogWrapper.
     */
    public enum LogLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        FAILURE
    }

    public void log(LogLevel level, String message);

    public void log(LogLevel level, String message, Exception e);

    public void log(LogLevel level, Exception e);

    public LogLevel getLogLevel();

    public void setLogLevel(LogLevel level);
}
