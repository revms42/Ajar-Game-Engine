/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 25, 2013 Matthew Stockbridge
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
 * Visitor.java
 */
package org.ajar.age;

/**
 * Visitors visit every <code>Node</code> during the udpate
 * cycle and perform some sort of operation on those that
 * require an update.
 *
 * @author revms42
 * @since 0.0.214
 */
public interface Visitor {

    /**
     * Determines if this Visitor can visit the specified <code>Node</code>.
     * @param node the <code>Node</code> to be visited.
     * @return <code>true</code> if the node can be visited by this Visitor.
     */
    public boolean shouldVisit(Node node);

    /**
     * Schedules the specified <code>Node</code> to be visited by this Visitor
     * @param node the <code>Node</code> to schedule.
     */
    public void scheduleVisit(Node node);

    /**
     * Runs through the <code>Node</code>s that have been scheduled and visits them.
     */
    public void visit();
}