/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 4, 2014 Matthew Stockbridge
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
 * ConditionObject.java
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

import org.ajar.age.logic.Condition;
import org.ajar.age.logic.Effect;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicParserException;

/**
 * @author mstockbr
 *
 */
public class ConditionObject<A extends Condition<?>> extends EffectObject<A> {

	private EffectObject<?> trueEffect;
	private EffectObject<?> falseEffect;
	
	/**
	 * @param line
	 * @param c
	 */
	public ConditionObject(String line, IParsedClass<A> c) {
		super(line, c);
	}
	
	public void setTrueEffect(EffectObject<?> trueEffect){
		this.trueEffect = trueEffect;
	}
	
	public void setFalseEffect(EffectObject<?> falseEffect){
		this.falseEffect = falseEffect;
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
				if(args.size() > 0){
					for(int i = 0; i < argTypes.size(); i++){
						argTypes.add(i, Effect.class);
						args.add(i,(Effect<?>)trueEffect.getParsedObject());
						argTypes.add(i+1, Effect.class);
						args.add(i+1,(Effect<?>)falseEffect.getParsedObject());
						try{
							con = super.constructorFor(argTypes);
							break;
						}catch(LogicParserException e2){
							argTypes.remove(i);
							argTypes.remove(i+1);
							args.remove(i);
							args.remove(i+1);
						}
					}
				}else{
					argTypes.add(0, Effect.class);
					args.add(0,(Effect<?>)trueEffect.getParsedObject());
					argTypes.add(1, Effect.class);
					args.add(1,(Effect<?>)falseEffect.getParsedObject());
					try{
						con = super.constructorFor(argTypes);
					}catch(LogicParserException e2){
						argTypes.remove(0);
						argTypes.remove(1);
						args.remove(0);
						args.remove(1);
					}
				}
			}
			try {
				this.instance = con.newInstance(args.toArray(new Object[args.size()]));
			} catch (InstantiationException e) {
				throw new LogicParserException("Error constructing new instance.",e);
			} catch (IllegalAccessException e) {
				throw new LogicParserException("Error constructing new instance.",e);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new LogicParserException("Error constructing new instance.",e);
			} catch (InvocationTargetException e) {
				throw new LogicParserException("Error constructing new instance.",e);
			}
		}
		return super.getInstance();
	}

}
