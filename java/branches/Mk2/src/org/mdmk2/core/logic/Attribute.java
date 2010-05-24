/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 16-May-10 Matthew Stockbridge
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
 * MDMk2
 * org.mdmk2.core.logic
 * Attribute.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.logic;

/**
 * @author mstockbridge
 * 16-May-10
 */
public interface Attribute<V> {
	/**
	 * Returns the current value of this Attribute.
	 * mstockbridge
	 * 23-May-10
	 * @return		the value of this Attribute.
	 */
	public V getValue();
	
	/**
	 * Sets the value of the Attribute, returning the actual value afterwards.
	 * <p>
	 * <b>Implementation Note:<b> Though not explicitly implemented due to the
	 * nature of an Interface, every implementation of Attribute should always
	 * fire an appropriate event to all registered listeners for any event type 
	 * that this Attribute returns from the {@link Attribute.registeredEvents()}
	 * method.
	 * mstockbridge
	 * 23-May-10
	 * @param	value	the value to set this Attribute to.
	 * @return			the actual value of this Attribute post-set.
	 * @see				{@link Attribute.registeredEvents()}
	 */
	public V setValue(V value);

	/**
	 * Add a listener to this observer registered for the appropriate events.
	 * Only those events which are registered by this Attribute will be added.
	 * <p>
	 * <b>Implementation Note:<b> Though not explicity implemented due to the 
	 * nature of an Interface, if the type parameter is <code>null<code> the
	 * listener should be registered for all event types returned from the
	 * {@link Attribute.registeredEvents()} method.
	 * mstockbridge
	 * 23-May-10
	 * @param	listener	the listener to add to this observer.
	 * @param	type		the type of events to report on.
	 * @return				the types of event registered if any events are registered.
	 * @see					{@link Attribute.registeredEvent()}
	 */
	public AttributeEventType[] addAttributeListener(AttributeListener listener, AttributeEventType... type);
	
	/**
	 * Removes a registered listener for specific event types.
	 * Event types that aren't registered by this Attribute are ignored.
	 * <p>
	 * <b>Implementation Note:<b> Though not explicity implemented due to the 
	 * nature of an Interface, every implementation of Attribute should always
	 * recursively search for the specified listener and remove it if the supplied
	 * type parameter is <code>null</code> or length zero.
	 * mstockbridge
	 * 23-May-10
	 * @param	listener	the listener to remove from this observer.
	 * @param	type		the type of event to stop reporting to the observer.
	 */
	public void removeAttributeListener(AttributeListener listener, AttributeEventType... type);
	
	/**
	 * Returns an array of events that this ChangeObserver can report on.
	 * mstockbridge
	 * 23-May-10
	 * @return	an array of AttributeEventTypes that this observer can report on.
	 */
	public AttributeEventType[] registeredEvents();
}
