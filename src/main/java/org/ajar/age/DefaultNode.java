/*
 * This file is part of Ajar Game Engine
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
 * DefaultNode.java
 */
package org.ajar.age;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
/**
 * DefaultNode is a general-purpose implementation of the <code>Node</code> class.
 * For most purposes DefaultNode should provide all the functionality that is
 * required for a node implementation in a game.
 * <p>
 * DefaultNode's {@link #addChild(Node) addChild} and {@link #removeChild(Node) removeChild} methods
 * have logic that ensures that children added will have the appropriate parent set, provided they are
 * also DefaultNodes. This ensures that the node tree remains
 * comprised exclusively of node instances, with no decorators present (which, in turn, improves speed
 * and reduces complexity).
 * @see Node
 * @see Decorator
 * @author revms42
 * @since 0.0.0.153
 */
public class DefaultNode implements Node {

    /**
     * The children of this node.
     */
    private final Vector<Node> children;

    /**
     * The parent of this node.
     */
    private Node parent;

    /**
     * This node's decorators.
     */
    private final HashMap<Class<Decorator>,Decorator> decorators;

    /**
     * Creates a new DefaultNode.
     */
    public DefaultNode(){
        children = new Vector<>();
        decorators = new HashMap<>();
    }

    @Override
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Adds the node <code>child</code> to the end of the list of this node's children.
     * <p>
     * If the child is an instance of DefaultNode than it will have it's parent variable set to this node.
     * @param child the node that this node will be parent to.
     */
    @Override
    public void addChild(Node child) {
        if(child.getUndecoratedNode() instanceof DefaultNode){
            ((DefaultNode) child.getUndecoratedNode()).setParent(this);
        }
        children.add(child);
    }

    /**
     * Adds the node <code>child</code> to this node's children at the given index.
     * <p>
     * If the child is an instance of DefaultNode than it will have it's parent variable set to this node.
     * @param child the node that this node will be parent to.
     */
    @Override
    public void addChild(int index, Node child) {
        if(child.getUndecoratedNode() instanceof DefaultNode){
            ((DefaultNode) child.getUndecoratedNode()).setParent(this);
        }
        children.add(index,child);
    }

    /**
     * Removes the node <code>child</code> from the list of this node's children.
     * <p>
     * If the child is an instance of DefaultNode than it will have it's parent variable set to <code>null</code>.
     * @param child the node to remove from the list of this node's children.
     * @return <code>true</code> if the child node was removed.
     */
    @Override
    public boolean removeChild(Node child) {
        if(child.getParent() == this && child.getUndecoratedNode() instanceof DefaultNode){
            ((DefaultNode) child.getUndecoratedNode()).setParent(null);
        }
        return children.remove(child.getUndecoratedNode());
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    /*
     * Sets the parent member variable to the supplied node.
     * @param parent the parent of this node.
     */
    private void setParent(Node parent){
        this.parent = parent;
    }

    @Override
    public boolean hasCapability(Class<? extends Decorator> c) {
        return decorators.containsKey(c);
    }

    @Override
    public <D extends Decorator> D getDecorator(Class<D> c) {
        return (D) decorators.get(c);
    }

    @Override
    public void addDecorator(Decorator decorator) {
        decorators.put((Class<Decorator>) decorator.getClass(), decorator);

        for(Annotation a : decorator.getClass().getAnnotations()){
            if(a.annotationType() == DecoratesFor.class){
                for(Class<? extends Decorator> c : ((DecoratesFor)a).types()){
                    decorators.put((Class<Decorator>) c, decorator);
                }
            }
        }
    }

    @Override
    public <D extends Decorator> D removeDecorator(Class<D> c) {
        return (D) decorators.remove(c);
    }

    @Override
    public Node getUndecoratedNode() {
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        if(visitor.shouldVisit(this)){
            if(hasChildren()){
                for(Node child : children){
                    if(child != null){
                        child.accept(visitor);
                    }
                }
            }

            visitor.scheduleVisit(this);
        }
    }

    /* (non-Javadoc)
     * @see org.ajar.age.Node#getRoot()
     */
    @Override
    public Node getRoot() {
        if(getParent() != null){
            return getParent().getRoot();
        }else{
            return this;
        }
    }

}