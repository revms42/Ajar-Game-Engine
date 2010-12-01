package org.mdmk3.core;

import java.util.List;

public interface Node<A extends Attributes> {

	public boolean hasChildren();
	public void addChild(Node<A> child);
	public boolean removeChild(Node<A> child);
	public List<Node<A>> getChildren();
	public Node<A> getParent();
	
	public A getAttributes();
	
	public boolean hasCapability(Class<? extends Decorator<A>> c);
	public <D extends Decorator<A>> D getDecorator(Class<D> c);
	public void addDecorator(Decorator<A> decorator);
	public <D extends Decorator<A>> D removeDecorator(Class<D> c);
}
