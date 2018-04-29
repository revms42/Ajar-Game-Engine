/*
 * This file is part of Macchiato Doppio Java Game Framework.
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
 * MDMk3
 * org.mdmk3.core.logic
 * DefaultEntity.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.logic;

import java.util.List;
import java.util.Vector;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

/**
 * This class is a default implementation of <code>AbstractEntity</code>, which provides the anticipated behavior for
 * actions to be performed and state access.
 * @author revms
 * @since 0.0.0.153
 */
public class DefaultEntity<A extends Attributes> extends AbstractEntity<A> {
	
	private final Vector<Action> actions;
	private State<A> state;
	
	/**
	 * Constructs a new <code>DefaultEntity</code> that decorates the provided node.
	 * @param node the node to be decorated by this <code>DefaultEntity</code>.
	 */
	public DefaultEntity(Node<A> node) {
		super(node);
		actions = new Vector<Action>();
	}
	
	public void addAction(Action action) {
		actions.add(action);
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public State<A> getState() {
		return state;
	}
	
	public boolean needsStateUpdate() {
		return true;
	}
	
	public void setState(State<A> s) {
		this.state = s;
	}
	
	/**
	 * Performs the following actions.
	 * <p>
	 * First, if there are actions in the actions list, these are iterated over. Each action is used to retrieve an
	 * effect from the current state. The effect is called to perform its logic on this entity, and the resultant state 
	 * becomes the current state. When all actions have been completed, the actions list is cleared.
	 * <p>
	 * Second, regardless of whether there are actions in the actions list, the effect mapped to <code>null</code> is 
	 * retrieved and asked to perform an update on this entity. Note, for this reason logic that should be handled every 
	 * turn for a given state (such as animation) should always be included in the <code>null</code> mapped effect.
	 * <p>
	 * Third, <code>requestInput</code> is called, which repopulates the actions list for the next update. This ensures
	 * that any actions taken this update cycle are applied prior to collision detection next cycle.
	 * @see #requestInput()
	 */
	public void updateState() {
		if(actions.size() > 0){
			for(Action a : actions){
				state = state.perform(this,a);
			}
			actions.removeAllElements();
		}
		state = state.perform(this, null);
		requestInput();
		
	}

	/**
	 * Iterates over each controller in the controllers list calling {@link Controller#pollForInput(Entity) pollForInput} in
	 * order to populate the actions list with actions being taken by this entity.
	 * @see Controller#pollForInput(Entity)
	 */
	@Override
	public void requestInput() {
		for(Controller<A> controller : getControllers()){
			controller.pollForInput(this);
		}
	}
}
