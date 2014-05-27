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
 * EffectObject.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.capsule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.ajar.age.logic.Effect;
import org.ajar.age.logic.State;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;

/**
 * @author reverend
 *
 */
public class EffectObject<A extends Effect<?>> extends ParsedObject<A> {
	
	private StateObject<?> resultantState;
	/**
	 * @param line
	 * @param c
	 */
	public EffectObject(String line, IParsedClass<A> c) {
		super(line, c);
	}
	
	public StateObject<?> getResultantState() {
		return resultantState;
	}
	
	public void setResultantState(StateObject<?> resultantState) {
		this.resultantState = resultantState;
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedObject#getParsedObject()
	 */
	@Override
	public A getParsedObject() throws LogicParserException{
		if(getInstance() == null){
			Constructor<A> con = null;
			List<Class<?>> argTypes = getArgTypes();
			//TODO: We can get the argument types, we need the objects too.
			Vector<Object> args = new Vector<Object>();
			Collections.addAll(args, getArguments());
			try {
				con = super.constructorFor(argTypes);
			} catch (LogicParserException e) {
				State<?> result = parseResultantState();
				for(int i = 0; i < argTypes.size(); i++){
					argTypes.add(i, State.class);
					args.add(i,result);
					try{
						con = super.constructorFor(argTypes);
						break;
					}catch(LogicParserException e2){
						argTypes.remove(State.class);
						args.remove(result);
					}
				}
			}
			try {
				this.instance = con.newInstance(args);
			} catch (InstantiationException e) {
				throw new LogicParserException("Error constructing new instance.",e);
			} catch (IllegalAccessException e) {
				throw new LogicParserException("Error constructing new instance.",e);
			} catch (IllegalArgumentException e) {
				throw new LogicParserException("Error constructing new instance.",e);
			} catch (InvocationTargetException e) {
				throw new LogicParserException("Error constructing new instance.",e);
			}
		}
		return super.getInstance();
	}
	
	protected State<?> parseResultantState() throws LogicParserException{
		if(resultantState == null){
			//TODO: This assumes that we're only getting a effect=state
			String[] effectState = this.lineDefinition().split("=");
			if(effectState.length > 1){
				resultantState = LogicLoader.findState(effectState[1]);
			}
		}
		return (State<?>)resultantState.getParsedObject();

		
	}
}
