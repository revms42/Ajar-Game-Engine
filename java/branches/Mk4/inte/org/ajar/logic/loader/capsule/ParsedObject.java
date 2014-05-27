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
 * ParsedObject.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.IParsedObject;
import org.ajar.logic.loader.IParser;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;

/**
 * @author reverend
 *
 */
public class ParsedObject<A extends Object> implements IParsedObject<A> {

	private final static HashMap<String,ParsedObject<?>> namedObjects;
	
	static{
		namedObjects = new HashMap<String,ParsedObject<?>>();
	}
	
	protected final static Pattern args = Pattern.compile("(?>\\()(.+)\\s?,?(?>\\))");
	
	private final String line;
	private final IParsedClass<A> clazz;
	
	private Vector<Object> arguments;
	private Vector<Class<?>> argTypes;
	protected A instance;
	
	public ParsedObject(String line, IParsedClass<A> c){
		this.line = line;
		this.clazz = c;
	}
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedClass#lineDefinition()
	 */
	@Override
	public String lineDefinition() {
		return line;
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedClass#objectClass()
	 */
	@Override
	public Class<A> objectClass() {
		return clazz.objectClass();
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedClass#assignableFrom(java.lang.Class)
	 */
	@Override
	public boolean assignableFrom(Class<?> clazz) {
		return this.clazz.assignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedObject#getParsedObject()
	 */
	@Override
	public A getParsedObject() throws LogicParserException{
		if(instance == null){
			Constructor<A> con = clazz.constructorFor(getArgTypes());
			try {
				instance = con.newInstance(getArguments());
			} catch (InstantiationException e) {
				throw new LogicParserException("Error creating new instance", e);
			} catch (IllegalAccessException e) {
				throw new LogicParserException("Error creating new instance", e);
			} catch (IllegalArgumentException e) {
				throw new LogicParserException("Error creating new instance", e);
			} catch (InvocationTargetException e) {
				throw new LogicParserException("Error creating new instance", e);
			}
		}
		return instance;
	}
	
	protected A getInstance() {
		return instance;
	}
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedClass#constructorFor(java.util.List)
	 */
	@Override
	public Constructor<A> constructorFor(List<Class<?>> args) throws LogicParserException {
		return clazz.constructorFor(args);
	}

	protected Object[] getArguments() throws LogicParserException{
		if(arguments == null){
			Matcher m = args.matcher(line);
			
			Vector<Object> args = new Vector<Object>();
			while(m.find()){
				String arg = m.group(1);
				
				IParser<?> p = LogicLoader.findArgumentParser(arg, null);
				IParsedClass<?> pc = p.getParsedClass(arg);
				
				if(pc instanceof IParsedObject){
					args.add(((IParsedObject<?>)pc).getParsedObject());
				}
			}
			
			arguments = args;
		}

		return arguments.toArray(new Object[arguments.size()]);
	}
	
	protected List<Class<?>> getArgTypes() throws LogicParserException{
		if(argTypes == null){
			getArguments();
			
			argTypes = new Vector<Class<?>>();
			for(Object o : arguments){
				argTypes.add(o.getClass());
			}
		}
		
		return argTypes;
	}
	
	public static ParsedObject<?> getNamedObject(String name){
		return namedObjects.get(name);
	}
	
	public static void putNamedObject(String name, ParsedObject<?> c) throws LogicParserException{
		if(namedObjects.containsKey(name)){
			throw new LogicParserException("An entry with that key already exists!");
		}else{
			namedObjects.put(name, c);
		}
	}
}
