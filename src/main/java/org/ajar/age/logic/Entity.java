package org.ajar.age.logic;

/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Dec 1, 2010 Matthew Stockbridge
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
 * org.ajar.age.logic
 * Entity.java
 */

import org.ajar.age.Decorator;

import java.util.Queue;

/**
 * Classes that implement this interface indicate that they will participate in logic updates within the game loop.
 * <p>
 * All <code>Entities</code> have (or are assumed to have) two separate related parts. The first is a <code>State</code>
 * , which is a representation of the current state of the entity based on previous actions taken, which contains mappings
 * from the state the entity is currently into other states based on actions that the entity can take.
 * <p>
 * The second is a list of <code>Events</code>, which represent actions taken or performed on this entity during the
 * current update period.
 * @author revms42
 * @since 0.0.0.153
 */
public interface Entity extends Decorator {

    /**
     * Returns this <code>Entity</code>'s current state.
     * @return the current state of this <code>Entity</code>.
     */
    public State getState();

    /**
     * Sets the current state of this <code>Entity</code> to the provided state.
     * @param state this <code>Entity</code>'s new state.
     */
    public void setState(State state);

    /**
     * Adds an event to the list of events to process this update period
     * @param event the event to be added to the events list.
     */
    public void queueEvent(Event event);

    /**
     * Retrieves the current queue of events to process this update period.
     * @return the current queue of events.
     */
    public Queue<Event> getEventQueue();

}
