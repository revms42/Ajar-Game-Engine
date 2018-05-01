package org.ajar.age.logic;
/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 1st, 2018 Matthew Stockbridge
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
 * DefaultEntity.java
 */

import org.ajar.age.DefaultDecorator;
import org.ajar.age.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * DefaultEntity is a simple implementation of the <code>Entity</code> interface that is based on
 * a <code>DefaultDecorator</code>.
 * @see org.ajar.age.DefaultDecorator
 * @see Entity
 * @author revms42
 */
public class DefaultEntity extends DefaultDecorator implements Entity {
    protected State state;
    protected final Queue<Event> events;

    /**
     * Constructs a new <code>DefaultEntity</code> that is decorating the supplied <code>Node</code>.
     *
     * @param    node    the node that this decorator will decorate.
     * @see org.ajar.age.DefaultDecorator
     */
    public DefaultEntity(Node node) {
        super(node);
        this.events = new LinkedList<>();
    }

    /* (non-Javadoc)
     * @see org.ajar.age.State#getState()
     */
    @Override
    public State getState() {
        return state;
    }

    /* (non-Javadoc)
     * @see org.ajar.age.State#setState(State)
     */
    @Override
    public void setState(State state) {
        this.state = state;
    }

    /* (non-Javadoc)
     * @see org.ajar.age.State#queueEvent(Event)
     */
    @Override
    public void queueEvent(Event event) {
        events.add(event);
    }

    /* (non-Javadoc)
     * @see org.ajar.age.State#getEventQueue()
     */
    @Override
    public Queue<Event> getEventQueue() {
        return events;
    }
}
