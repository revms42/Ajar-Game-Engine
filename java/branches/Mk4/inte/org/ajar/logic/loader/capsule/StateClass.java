/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 18, 2014 Matthew Stockbridge
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
 * org.ajar.logic.loader.capsule
 * StateClass.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.capsule;

import java.lang.reflect.Constructor;
import java.util.List;

import org.ajar.age.logic.DefaultState;
import org.ajar.age.logic.State;
import org.ajar.logic.loader.LogicParserException;

/**
 * @author reverend
 *
 */
public class StateClass<A extends DefaultState<?>> extends ParsedClass<A> {
	
	/**
	 * @param line
	 * @param clazz
	 */
	public StateClass(String line, Class<A> clazz) {
		super(line, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public Class<A> objectClass(){
		if(super.objectClass() == null){
			return (Class<A>) DefaultState.class;
		}else{
			return super.objectClass();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedClass#constructorFor(java.util.List)
	 */
	@Override
	public Constructor<A> constructorFor(List<Class<?>> args) throws LogicParserException {
		try {
			return objectClass.getConstructor(args.toArray(new Class<?>[args.size()]));
		} catch (Exception e) {
			try {
				fixStateClass(args);
				return objectClass.getConstructor(args.toArray(new Class<?>[args.size()]));
			} catch (Exception e2) {
				String msg = "Could not get constructor for " + objectClass.getCanonicalName() + 
						" with arg types ";
				for(Class<?> c : args){
					msg = msg + c.getName() + ", ";
				}
				throw new LogicParserException(msg,e);
			}
		}
	}
	
	private void fixStateClass(List<Class<?>> args) {
		for(int i = 0; i < args.size(); i++){
			if(args.get(i).isAssignableFrom(State.class)){
				args.set(i, State.class);
			}
		}
	}
}
