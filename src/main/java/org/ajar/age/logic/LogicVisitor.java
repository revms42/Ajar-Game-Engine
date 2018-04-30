package org.ajar.age.logic;
/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 27, 2013 Matthew Stockbridge
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
 * LogicVisitor.java
 */
import org.ajar.age.AbstractVisitor;

import java.util.Queue;

/**
 * LogicVisitor is a base logic visitor that performs all the vital functions
 * needed for a logic update: It iterates the events of each entity scheduled
 * for update applying the event to the current state and updating the current
 * state according to the result of the event.
 * @param <E> the <code>Entity</code> class associated with this LogicVisitor.
 * @author revms42
 */
public class LogicVisitor<E extends Entity> extends AbstractVisitor<E> {

    /**
     * Creates a new LogicVisitor with the specified entity class.
     * @param decoratorClass the <code>Entity</code> decorator class for this
     *                       Logic Visitor.
     */
    public LogicVisitor(Class<? extends E> decoratorClass) {
        super(decoratorClass);
    }

    @Override
    protected boolean shouldVisit(E decorator) {
        return decorator.getState() != null;
    }

    @Override
    public void visit(E decorator) {
        Queue<Event> events = decorator.getEventQueue();
        State currentState = decorator.getState();
        while(!events.isEmpty()){
            currentState = currentState.perform(decorator, events.poll());
        }
        decorator.setState(currentState);
    }

    @Override
    protected void startProcess() {
        /* No-Op */
    }

    @Override
    protected void finishProcess() {
        /* No-Op */
    }
}
