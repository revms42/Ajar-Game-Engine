package org.mdmk3.core;

import java.util.List;

public class DefaultDecorator<A extends Attributes> implements Decorator<A> {
	
	protected final Node<A> node;

	public DefaultDecorator(Node<A> node){
		this.node = node;
		node.addDecorator(this);
	}
	
	public boolean hasChildren() {
		return node.hasChildren();
	}

	public void addChild(Node<A> child) {
		node.addChild(child);
	}

	public boolean removeChild(Node<A> child) {
		return node.removeChild(child);
	}

	public List<Node<A>> getChildren() {
		return node.getChildren();
	}

	public Node<A> getParent() {
		return node.getParent();
	}

	public A getAttributes() {
		return node.getAttributes();
	}

	public boolean hasCapability(Class<? extends Decorator<A>> c) {
		return node.hasCapability(c);
	}

	public <D extends Decorator<A>> D getDecorator(Class<D> c) {
		return node.getDecorator(c);
	}

	public void addDecorator(Decorator<A> decorator) {
		node.addDecorator(decorator);
	}

	public <D extends Decorator<A>> D removeDecorator(Class<D> c) {
		return node.removeDecorator(c);
	}

	@Override
	public Node<A> getUndecoratedNode() {
		return node.getUndecoratedNode();
	}
}
