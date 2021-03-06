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
 * org.ajar.logic.loader
 * AbstractParsedClass.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.capsule;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicParserException;

/**
 * @author reverend
 *
 */
public class ParsedClass<A extends Object> implements IParsedClass<A> {

	private final static HashMap<String,ParsedClass<?>> mappedClasses;
	
	static{
		mappedClasses = new HashMap<String,ParsedClass<?>>();
	}
	
	private final String lineDefinition;
	protected final Class<A> objectClass;
	
	public ParsedClass(String line, Class<A> clazz){
		this.lineDefinition = line;
		this.objectClass = clazz;
		mappedClasses.put(objectClass.getCanonicalName(), this);
	}
	
	public static void clearCache(){
		mappedClasses.clear();
	}

	public String lineDefinition(){
		return lineDefinition;
	}
	
	public Class<A> objectClass(){
		return objectClass;
	}
	
	public boolean isSubClassOf(Class<?> clazz){
		return clazz.isAssignableFrom(objectClass);
	}
	
	public static ParsedClass<?> getParsedClass(Class<?> c){
		return mappedClasses.get(c.getCanonicalName());
	}
	
	public static ParsedClass<?> getNamedClass(String name){
		return mappedClasses.get(name);
	}
	
	public static void putNamedClass(String name, ParsedClass<?> c) throws LogicParserException{
		if(mappedClasses.containsKey(name)){
			throw new LogicParserException("An entry with that key already exists!");
		}else{
			mappedClasses.put(name, c);
		}
	}

//	/* (non-Javadoc)
//	 * @see org.ajar.logic.loader.IParsedClass#constructorFor(java.util.List)
//	 */
//	@Override
//	public Constructor<A> constructorFor(List<Class<?>> args) throws LogicParserException {
//		try {
//			return objectClass.getConstructor(args.toArray(new Class<?>[args.size()]));
//		} catch (Exception e) {
//			String msg = "Could not get constructor for " + objectClass.getCanonicalName() + 
//					" with arg types ";
//			for(Class<?> c : args){
//				msg = msg + c.getName() + ", ";
//			}
//			throw new LogicParserException(msg,e);
//		}
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Constructor<A> constructorFor(List<Class<?>> args) throws LogicParserException {
		search:
		for(Constructor<?> con : objectClass.getConstructors()){
			Class<?>[] params = con.getParameterTypes();
			
			if(con.isVarArgs()){
				int i = 0;
				for(; i < params.length; i++){
					if(!params[i].isAssignableFrom(args.get(i))){
						continue search;
					}
				}
				for(int j = i; j < args.size(); j++){
					if(!params[i].isAssignableFrom(args.get(j))){
						continue search;
					}
				}
				return (Constructor<A>) con;
			}else{
				if(args.size() == params.length){
					for(int i = 0; i < params.length; i++){
						if(!params[i].isAssignableFrom(args.get(i))){
							continue search;
						}
					}
					return (Constructor<A>) con;
				}
			}
		}
		String msg = "Could not get constructor for " + objectClass.getCanonicalName() + " with arg types ";
		for(Class<?> c : args){
			msg = msg + c.getName() + ", ";
		}
		throw new LogicParserException(msg);
//		try {
//			return objectClass.getConstructor(args.toArray(new Class<?>[args.size()]));
//		} catch (Exception e) {
//			try {
//				fixStateClass(args);
//				return objectClass.getConstructor(args.toArray(new Class<?>[args.size()]));
//			} catch (Exception e2) {
//				String msg = "Could not get constructor for " + objectClass.getCanonicalName() + 
//						" with arg types ";
//				for(Class<?> c : args){
//					msg = msg + c.getName() + ", ";
//				}
//				throw new LogicParserException(msg,e);
//			}
//		}
	}
}
