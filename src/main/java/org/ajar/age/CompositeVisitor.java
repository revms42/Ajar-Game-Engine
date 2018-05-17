/*
 * This file is part of Ajar Game Engine
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
 * AGE
 * org.ajar.age
 * CompositeVisitor.java
 */
package org.ajar.age;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A CompositeVisitor takes a collection of other <code>Visitor</code>s and
 * brings them under one umbrella. The basic idea is to have the composite
 * run through the visitors in order when an update is requested.
 * <p>
 * For {@link #shouldVisit(Node)} the first <code>Visitor</code>s {@link Visitor#shouldVisit(Node)}
 * method is called, and the result is returned. In this way, the first visitor makes the determination
 * of whether the update should be done at all, and acts as the culling criteria.
 * <p>
 * For {@link #scheduleVisit(Node)} the list of <code>Visitor</code>s is iterated through, with each
 * visitor getting its {@link Visitor#shouldVisit(Node)} method getting called, with a <code>true</code>
 * triggering a subsequent call to its {@link Visitor#scheduleVisit(Node)} method.
 * <p>
 * The {@link #visit()} method iterates the list again, calling each <code>Visitor</code>s
 * {@link Visitor#visit()} method in turn.
 * @author revms42
 */
public class CompositeVisitor implements Visitor {

    private final List<Visitor> visitors;
    private final List<Visitor> scheduled;

    public CompositeVisitor(Visitor... visitors) {
        this(Arrays.asList(visitors));
    }

    public CompositeVisitor(List<Visitor> visitors) {
        this.visitors = visitors;
        for(Visitor visitor : visitors) {
            if(visitor == null) throw new IllegalArgumentException("Visitor values cannot be null");
        }
        this.scheduled = new ArrayList<>();
    }

    @Override
    public boolean shouldVisit(Node node) {
        return visitors.get(0).shouldVisit(node);
    }

    @Override
    public void scheduleVisit(Node node) {
        for(Visitor visitor : visitors) {
            boolean scheduled = false;
            if(visitor.shouldVisit(node)){
                visitor.scheduleVisit(node);
                scheduled = true;
            }
            if(scheduled) this.scheduled.add(visitor);
        }
    }

    @Override
    public void visit() {
        for(Visitor visitor : scheduled) {
            visitor.visit();
        }
        scheduled.clear();
    }
}
