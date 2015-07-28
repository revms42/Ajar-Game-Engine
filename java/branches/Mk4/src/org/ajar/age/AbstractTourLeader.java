/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 20, 2015 Matthew Stockbridge
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
 * AbstractTourLeader.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractTourLeader<A extends Attributes, D extends Decorator<A>> extends AbstractVisitor<A, D> implements TourLeader<A> {

	private boolean processStarted;
	
	/**
	 * @param decoratorClass
	 */
	public AbstractTourLeader(Class<? extends D> decoratorClass) {
		super(decoratorClass);
	}

	public void startProcess(){
		processStarted = true;
	}
	
	public void finishProcess(){
		processStarted = false;
	}
	
	
	/**
	 * The big difference between this class and a normal visitor is that this class explicitly needs
	 * start and finish process to be called around the process() call.
	 */
	@Override
	public void process() {
		if(!processStarted) startProcess();
		
		for(D disp : needsUpdate){
			update(disp);
		}
		needsUpdate.clear();
	}
}
