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
 * org.ajar.age.logic
 * DefaultEntity.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import java.util.List;
import java.util.Vector;

import org.ajar.age.Attributes;
import org.ajar.age.Node;

/**
 * TODO: Rewrite.
 * This class is a default implementation of <code>AbstractEntity</code>, which provides the anticipated behavior for
 * events to be performed and state access.
 * @author revms
 * @since 0.0.0.153
 */
public class DefaultEntity<A extends Attributes> extends AbstractEntity<A> {
	
	private final Vector<Event<A>> events;
	private State<A> state;
	
	/**
	 * Constructs a new <code>DefaultEntity</code> that decorates the provided node.
	 * @param node the node to be decorated by this <code>DefaultEntity</code>.
	 */
	public DefaultEntity(Node<A> node) {
		super(node);
		events = new Vector<Event<A>>();
	}
	
	@Override
	public void addEvent(Event<A> action) {
		events.add(action);
	}
	
	@Override
	public List<Event<A>> getEvents() {
		return events;
	}
	
	@Override
	public State<A> getState() {
		return state;
	}
	
	@Override
	public boolean needsStateUpdate() {
		return true;
	}
	
	@Override
	public void setState(State<A> s) {
		this.state = s;
	}
	
	/**
	 * Performs the following actions.
	 * <p>
	 * First, if there are events in the events list, these are iterated over. Each action is used to retrieve an
	 * effect from the current state. The effect is called to perform its logic on this entity, and the resultant state 
	 * becomes the current state. When all events have been completed, the events list is cleared.
	 * <p>
	 * Second, regardless of whether there are events in the events list, the effect mapped to <code>null</code> is 
	 * retrieved and asked to perform an update on this entity. Note, for this reason logic that should be handled every 
	 * turn for a given state (such as animation) should always be included in the <code>null</code> mapped effect.
	 * <p>
	 * Third, <code>requestInput</code> is called, which repopulates the actions list for the next update. This ensures
	 * that any events taken this update cycle are applied prior to collision detection next cycle.
	 * <p>
	 * In order to avoid problems with poorly initialized states null states returned
	 * from an effect perform call result in the same state being maintained subsequently.
	 * @see #requestInput()
	 */
	@Override
	public void updateState() {
		State<A> result = null;
		if(events.size() > 0){
			for(Event<A> a : events){
				state = (result = state.perform(this,a)) == null ? state : result;
			}
			events.removeAllElements();
		}
		state = (result = state.perform(this, null)) == null ? state : result;
		requestInput();
		
	}

	/**
	 * Iterates over each controller in the controllers list calling {@link Controller#pollForInput(Entity) pollForInput} in
	 * order to populate the events list with events being taken by this entity.
	 * @see Controller#pollForInput(Entity)
	 */
	@Override
	public void requestInput() {
		for(Controller<A> controller : getControllers()){
			controller.pollForInput(this);
		}
	}
}
