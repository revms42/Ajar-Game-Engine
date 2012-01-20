package org.mdmk3.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class DefaultNode<A extends Attributes> implements Node<A> {

	private final A attributes;
	private final Vector<Node<A>> children;
	private Node<A> parent;
	private final HashMap<Class<Decorator<A>>,Decorator<A>> decorators;
	
	public DefaultNode(A attrs){
		this.attributes = attrs;
		children = new Vector<Node<A>>();
		decorators = new HashMap<Class<Decorator<A>>,Decorator<A>>();
	}
	
	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addChild(Node<A> child) {
		if(child.getUndecoratedNode() instanceof DefaultNode){
			((DefaultNode) child.getUndecoratedNode()).setParent(this);
		}
		children.add(child);
	}

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

}
