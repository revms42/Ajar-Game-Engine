package org.mdmk3.core.logic;

import java.util.List;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Decorator;

public interface Entity<A extends Attributes> extends Decorator<A> {
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 */
	public void updateState();
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param action
	 */
	public void addAction(Action action);
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public List<Action> getActions();
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public State<A> getState();
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param s
	 */
	public void setState(State<A> s);
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public boolean needsStateUpdate();
}
