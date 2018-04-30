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
 * State.java
 */
package org.ajar.age.logic;

/**
 * Classes that implement this interface provide mappings from one state to another, given
 * an <code>Event</code> input.
 * <p>
 * Typically, a single state will map a variety of actions to a variety of new states by
 * performing an <code>Effect</code>, determined by the input action, on an <code>Entity</code> and then
 * taking the resultant state from the effect that was performed.
 * @author revms42
 * @since 0.0.0.153
 * @see Effect
 * @see Effect#perform(Entity,Entity)
 * @see Entity
 */
public interface State {

    /**
     * Performs a given <code>Effect</code> on the provided <code>Entity</code> by selecting the effect
     * associated by the provided <code>Event</code>.
     * @param subject the entity on which the effect will be performed
     * @param e the event indicating the effect to perform
     * @return the resultant <code>State</code> specified by the <code>Effect</code> that was performed.
     * @see Effect#perform(Entity,Entity)
     */
    public State perform(Entity subject, Event e);
}
