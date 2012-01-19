package org.mdmk3.core.logic;

import java.util.List;
import java.util.Vector;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.DefaultDecorator;
import org.mdmk3.core.Node;

public abstract class AbstractEntity<A extends Attributes> extends DefaultDecorator<A> implements Entity<A> {

	private final Vector<Controller<A>> controllers;
	
	public AbstractEntity(Node<A> node) {
		super(node);
		this.controllers = new Vector<Controller<A>>();
	}

	/**
	 * mstockbridge
	 * 19-Jan-12
	 * @return
	 */
	public List<Controller<A>> getControllers(){
		return controllers;
	}
	
	/**
	 * mstockbridge
	 * 19-Jan-12
	 * @return
	 */
	public void addController(Controller<A> controller){
		controllers.add(controller);
	}
	
	/**
	 * mstockbridge
	 * 19-Jan-12
	 * @return
	 */
	public boolean removeController(Controller<A> controller){
		return controllers.remove(controller);
	}
}
