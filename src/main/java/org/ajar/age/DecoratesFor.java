/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jan 15, 2014 Matthew Stockbridge
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
 * AGE
 * org.ajar.age
 * Decorates.java
 */
package org.ajar.age;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This class allows authors to explicitly bind sub-classes
 * to parent classes. This allows for using sub-classes of
 * classes that are used for visitation during the determination
 * of the <code>Node</code> update loop without a lot of
 * reflection calls.
 * @author revms42
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DecoratesFor {

    /**
     * The classes that this class will decorate for.
     * @return an array of explicitly defined classes
     * that this class will decorate for.
     */
    Class<? extends Decorator>[] types();
}