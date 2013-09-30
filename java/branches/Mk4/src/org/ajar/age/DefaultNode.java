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
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
/**
 * @todo: Rewrite.
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
 * @see Attributes
 * @author revms
 * @since 0.0.0.153
 */
public class DefaultNode<A extends Attributes> implements Node<A> {

	/**
	 * The attributes representing this node.
	 */
	private final A attributes;
	
	/**
	 * The children of this node.
	 */
	private final Vector<Node<A>> children;
	
	/**
	 * The parent of this node.
	 */
	private Node<A> parent;
	
	/**
	 * This node's decorators.
	 */
	private final HashMap<Class<Decorator<A>>,Decorator<A>> decorators;
	
	/**
	 * Creates a new DefaultNode, which is represented by the supplied attributes.
	 * @param attrs the attributes that represent this node.
	 */
	public DefaultNode(A attrs){
		this.attributes = attrs;
		children = new Vector<Node<A>>();
		decorators = new HashMap<Class<Decorator<A>>,Decorator<A>>();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addChild(Node<A> child) {
		if(child.getUndecoratedNode() instanceof DefaultNode){
			((DefaultNode) child.getUndecoratedNode()).setParent(this);
		}
		children.add(child);
	}

	/**
	 * Removes the node <code>child</code> from the list of this node's children.
	 * <p>
	 * If the child is an instance of DefaultNode than it will have it's parent variable set to <code>null</code>.
	 * @param child the node to remove from the list of this node's children.
	 * @return <code>true</code> if the child node was removed.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean removeChild(Node<A> child) {
		if(child.getParent() == this && child.getUndecoratedNode() instanceof DefaultNode){
			((DefaultNode) child.getUndecoratedNode()).setParent(null);
		}
		return children.remove(child.getUndecoratedNode());
	}

	@Override
	public List<Node<A>> getChildren() {
		return Collections.unmodifiableList(children);
	}

	@Override
	public Node<A> getParent() {
		return parent;
	}
	
	/*
	 * Sets the parent member variable to the supplied node.
	 * @param parent the parent of this node.
	 */
	private void setParent(Node<A> parent){
		this.parent = parent;
	}

	@Override
	public A getAttributes() {
		return attributes;
	}

	@Override
	public boolean hasCapability(Class<? extends Decorator<A>> c) {
		return decorators.containsKey(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends Decorator<A>> D getDecorator(Class<D> c) {
		return (D) decorators.get(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addDecorator(Decorator<A> decorator) {
		decorators.put((Class<Decorator<A>>) decorator.getClass(), decorator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends Decorator<A>> D removeDecorator(Class<D> c) {
		return (D) decorators.remove(c);
	}

	@Override
	public Node<A> getUndecoratedNode() {
		return this;
	}

	@Override
	public void accept(Visitor<A> visitor) {
		if(visitor.isInView(this)){
			if(hasChildren()){
				for(Node<A> child : children){
					child.accept(visitor);
				}
			}
			
			visitor.visit(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.Node#getRoot()
	 */
	@Override
	public Node<A> getRoot() {
		if(getParent() != null){
			return getParent().getRoot();
		}else{
			return this;
		}
	}

}
