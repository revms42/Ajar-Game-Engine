/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 5, 2013 Matthew Stockbridge
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
 * AGE
 * org.ajar.age
 * SimpleVisitor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age;

/**
 * @author reverend
 *
 */
public class SimpleVisitor<A extends Attributes,D extends Decorator<A>> extends AbstractVisitor<A, D> {

	/**
	 * @param decoratorClass
	 */
	public SimpleVisitor(Class<? extends D> decoratorClass) {
		super(decoratorClass);
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#isInView(org.ajar.age.Decorator)
	 */
	@Override
	protected boolean isInView(D decorator) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#update(org.ajar.age.Decorator)
	 */
	@Override
	public void update(D decorator) {}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#startProcess()
	 */
	@Override
	protected void startProcess() {}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#finishProcess()
	 */
	@Override
	protected void finishProcess() {}

}
