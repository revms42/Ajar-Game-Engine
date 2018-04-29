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
 * org.ajar.age
 * AbstractVisitor.java
 */
package org.ajar.age;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is a simple implementation of the base behaviors expected in a <code>Visitor</code>.
 * <p>
 * Specifically, calling <code>shouldVisit(Node)</code> will determine if this node can be
 * visited, calling <code>scheduleVisit(Node)</code> will queue the specified <code>
 * Node</code> for a visit (if it needs an update), and calling <code>visit()</code> will
 * iteratively call <code>visit(D)</code> on each node in the sequence they were added
 * to the queue to update them.
 * @see Decorator
 * @author revms42
 *
 */
public abstract class AbstractVisitor<D extends Decorator> implements Visitor{

    private final Class<? extends D> decoratorClass;
    protected final Queue<D> needsUpdate;

    public AbstractVisitor(Class<? extends D> decoratorClass){
        this.decoratorClass = decoratorClass;
        needsUpdate = new LinkedList<>();
    }

    /* (non-Javadoc)
     * @see org.ajar.age.Visitor#shouldVisit(org.ajar.age.Node)
     */
    @Override
    public boolean shouldVisit(Node node) {
        if(node.hasCapability(decoratorClass)){
            return shouldVisit(node.getDecorator(decoratorClass));
        }
        return false;
    }

    protected abstract boolean shouldVisit(D decorator);

    /* (non-Javadoc)
     * @see org.ajar.age.Visitor#scheduleVisit(org.ajar.age.Node)
     */
    @Override
    public void scheduleVisit(Node node) {
        D disp = node.getDecorator(decoratorClass);
        if(disp.needsUpdate()){
            needsUpdate.add(disp);
        }
    }

    /* (non-Javadoc)
     * @see org.ajar.age.Visitor#visit()
     */
    @Override
    public void visit() {
        startProcess();
        while(!needsUpdate.isEmpty()){
            visit(needsUpdate.poll());
        }
        finishProcess();
    }

    /**
     * Performs an update logic against the <code>Decorator</code> specified.
     * @see Visitor#visit()
     * @param decorator
     */
    public abstract void visit(D decorator);

    /**
     * Performs any special initialization logic that needs to happen before
     * iterating through the queue of scheduled visits.
     */
    protected abstract void startProcess();

    /**
     * Performs any special global post-update logic that needs to happen after
     * iterating through the queue.
     */
    protected abstract void finishProcess();
}