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
 * AbstractEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.logic;

import org.mdmk3.core.Attributes;

/**
 * This class is a partial implementation of <code>Effect</code> that takes care of the implementation of the data methods
 * and abstracts the {@link #perform(Entity) perform} method into {@link #doAction(Entity)} (and subsequent returns the
 * result state specified during construction).
 * <p>
 * This class is meant as a simple implementation for effects that don't require a lot of special logic.
 * @author revms
 * @since 0.0.0.153
 */
public abstract class AbstractEffect<A extends Attributes> implements Effect<A> {
	private final Action a;
	private final State<A> result;
	
	/**
	 * Constructs a new <code>AbstractEffect</code> by specifying the action it represents and the resultant state.
	 * @param a the action this effect represents.
	 * @param result the resultant state of this effect.
	 */
	public AbstractEffect(Action a, State<A> result){
		this.a = a;
		this.result = result;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#getAction()
	 */
	public Action getAction() {
		return a;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#getResultState()
	 */
	public State<A> getResultState() {
		return result;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#perform(org.mdmk2.core.logic.StatedImp)
	 */
	public State<A> perform(Entity<A> state) {
		doAction(state);
		return result;
	}

	/**
	 * Performs any modification to the entity that needs to be performed as part of the {@link #perform(Entity) perform}
	 * method.
	 * @param entity the entity on which the effect is being performed.
	 */
	protected abstract void doAction(Entity<A> entity);
}
