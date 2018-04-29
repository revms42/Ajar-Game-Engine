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
 * org.ajar.age
 * Node.java
 */
package org.ajar.age;

import java.util.List;

/**
 * The Node interface represents the interface that all game objects must implement.
 * <p>
 * The Macchiato Doppio game engine uses <code>Node</code> trees to perform all relevant
 * game engine tasks, from rendering to collision detection. Though most of the actual
 * logic of these operations is performed by subclasses of {@link Decorator} all 
 * <code>Decorators</code> must reference one concrete instance of <code>Node</code>.
 * @see DefaultNode
 * @see Decorator
 * @author revms42
 * @since 0.0.0.153
 */
public interface Node {

    /**
     * Returns whether this node has any children. 
     * @return <code>true</code> if this node has child nodes.
     */
    public boolean hasChildren();

    /**
     * Adds the node <code>child</code> to the end of the list of this node's children.
     * @param child the node that this node will be parent to.
     */
    public void addChild(Node child);

    /**
     * Adds the node <code>child</code> to this node's children at the given index.
     * <p>
     * If the child is an instance of DefaultNode than it will have it's parent variable set to this node.
     * @param index the index to insert the child at.
     * @param child the node that this node will be parent to.
     */
    public void addChild(int index, Node child);
    /**
     * Removes the node <code>child</code> from the list of this node's children.
     * @param child the node to remove from the list of this node's children.
     * @return <code>true</code> if the child node was removed.
     */
    public boolean removeChild(Node child);

    /**
     * Gets a <code>List</code> that contains all of this node's children.
     * @return a list of all this node's children.
     */
    public List<Node> getChildren();

    /**
     * Gets the parent of this node.
     * @return this node's parent.
     */
    public Node getParent();

    /**
     * Called to have a visitor perform an operation on this node.
     * @param visitor
     */
    public void accept(Visitor visitor);

    /**
     * Determines whether or not this node has a decorator compatible with the decorator <code>Class</code> c.
     * @param c the class of the decorator to look for
     * @return <code>true</code> if this node has a decorator that matches the class <code>c</code>.
     */
    public boolean hasCapability(Class<? extends Decorator> c);

    /**
     * Gets the decorator of class <code>{@literal <D>}</code> from this node's list of decorators.
     * @param <D> the type represented by the class <code>c</code>.
     * @param c the class of decorator to get from the decorator list.
     * @return the decorator of type <code>{@literal <D>}</code> for this node.
     */
    public <D extends Decorator> D getDecorator(Class<D> c);

    /**
     * Adds a decorator to this node's decorator list
     * @param decorator the decorator to add to this node's decorator list.
     */
    public void addDecorator(Decorator decorator);

    /**
     * Removes (and returns) the decorator of class <code>{@literal <D>}</code> from this node's decorator
     * list.
     * @param <D> the type represented by the class <code>c</code>.
     * @param c the class of decorator to remove from the decorator list.
     * @return the decorator of type <code>{@literal <D>}</code> that was removed from this node.
     */
    public <D extends Decorator> D removeDecorator(Class<D> c);

    /**
     * This method returns <code>this</code> in <code>Node</code> implementations. This method exists as a way for 
     * <code>Node</code> implementations to get the bare node from a <code>Decorator</code> in order to set the
     * parent/child relationship directly on the root node, so that the node tree consists solely of actual nodes
     * and not their decorators.
     * @return <code>this</code> if the class implementing this method is an implementation of <code>Node</code>, or 
     * the node being decorated if the class implementing this method is an implementation of <code>Decorator</code>.
     * @see Decorator
     */
    public Node getUndecoratedNode();

    /**
     * This method will return the root of the Node hierarchy.
     * @return the root of the <code>Node</code> hierarchy this Node is a part of.
     * @since 186
     */
    public Node getRoot();
}