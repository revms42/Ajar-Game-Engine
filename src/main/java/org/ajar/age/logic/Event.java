/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jan 12, 2015 Matthew Stockbridge
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
 * org.ajar.age.logic
 * Event.java
 */
package org.ajar.age.logic;

/**
 * An Event object represents a single action being performed on an <code>Entity</code>.
 * These Events can originate from user or AI requests for an action to be performed,
 * or they can be a result of some passive interaction with the environment. Either way
 * they are processed for the Entities to which they belong on every update cycle.
 * @author revms42
 * @since 0.0.0.207
 */
public interface Event {

    /**
     * Gets the action that this event represents.
     * @return the associated <code>Action</code>.
     */
    public Action getAction();

    /**
     * Gets the <code>Entity</code> that initiated this event (if there was one).
     * @return the initiating <code>Entity</code>.
     */
    public Entity getInitiator();
}
