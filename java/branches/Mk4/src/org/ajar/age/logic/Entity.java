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
 * Entity.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic;

import java.util.List;

import org.ajar.age.Attributes;
import org.ajar.age.Decorator;

/**
 * TODO: Rewrite
 * Classes that implement this interface indicate that they will participate in logic updates within the game loop.
 * <p>
 * All <code>Entities</code> have (or are assumed to have) three separate related parts. The first is a <code>State</code>
 * , which is a representation of the current state of the entity based on previous actions taken, which contains mappings 
 * from the state the entity is currently into other states based on actions that the entity can take.
 * <p>
 * The second is a list of <code>Actions</code>, which represent actions taken or performed on this entity during the
 * current update period.
 * <p>
 * The third is a <code>Controller</code> (or <code>Controllers</code>), which provides input to the entity either from a 
 * user interface, or from an AI algorithm.
 * @author revms
 * @since 0.0.0.153
 */
public interface Entity<A extends Attributes> extends Decorator<A> {
	

	/**
	 * This method is used by a <code>GameLoop</code> to request that this entity update it's state.
	 * <p>
	 * Typically the entity will do this by iterating over its actions and updating its state for each action.
	 * Also, typically the controller (or controllers) is queried for input, either before iteration, or after, depending
	 * on logic architecture.
	 * @see #requestInput()
	 * @see State#perform(Entity, Action)
	 * @see org.ajar.age.GameLoop#logic()
	 */
	public void updateState();
	
	/**
	 * Adds an action to the list of actions to perform this update period
	 * @param action the action to be added to the actions list.
	 */
	public void addAction(Action action);
	
	/**
	 * Retrieves the current list of actions to be performed this update period. 
	 * @return the current list of actions.
	 */
	public List<Action> getActions();
	
	/**
	 * Returns this <code>Entity</code>'s current state.
	 * @return the current state of this <code>Entity</code>.
	 */
	public State<A> getState();
	
	/**
	 * Sets the current state of this <code>Entity</code> to the provided state.
	 * @param s this <code>Entity</code>'s new state.
	 */
	public void setState(State<A> s);
	
	/**
	 * Indicates whether or not this <code>Entity</code> needs to have its state updated during this update period.
	 * @return <code>true</code> if this <code>Entity</code> needs to update.
	 */
	public boolean needsStateUpdate();
	
	/**
	 * Gets the list of <code>Controllers</code> that provide this <code>Entity</code> with input.
	 * @return a list of all of this <code>Entity</code>'s controllers.
	 */
	public List<Controller<A>> getControllers();
	
	/**
	 * Adds a controller to this <code>Entity</code>'s list of controllers.
	 * @param controller a new controller that this <code>Entity</code> should use for input.
	 */
	public void addController(Controller<A> controller);
	
	/**
	 * Removes a controller from the list of controllers that this <code>Entity</code> uses for input.
	 * @param controller the controller to be removed.
	 * @return <code>true</code> if a controller was removed.
	 */
	public boolean removeController(Controller<A> controller);
	
	/**
	 * This method should be called when the <code>Entity</code> is asked to update.
	 * <p>
	 * This method should iterate over any controllers present and place their input actions into the action map.
	 * @see #updateState()
	 */
	public void requestInput();
}
