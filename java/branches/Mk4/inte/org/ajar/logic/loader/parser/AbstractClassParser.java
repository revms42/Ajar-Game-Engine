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
 * org.ajar.logic.loader.parser
 * AbstractClassParser.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser;

import java.util.regex.Matcher;

import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.IParser;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ParsedClass;

/**
 * @author reverend
 *
 */
public abstract class AbstractClassParser<A extends Object> implements IParser<A> {

	public static String GROUP_NAME="name";
	public static String GROUP_CLASS="class";
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParser#getParsedClass(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IParsedClass<A> getParsedClass(String line) throws LogicParserException {
		if(isNamed(line)){
			String name = getName(line);
			ParsedClass<A> p = 
					(ParsedClass<A>) ParsedClass.getNamedClass(name);
			
			if(p == null){
				Class<A> c = parseClass(line);
				p = makeParsedClass(line, c);
				
				ParsedClass.putNamedClass(name, p);
			}
			return p;
		}else{
			Class<A> c = parseClass(line);
			
			return makeParsedClass(line,c);
		}
	}
	
	protected abstract Matcher getMatcher(String line);
	
	protected String getNameGroup(){
		return GROUP_NAME;
	}
	
	protected String getClassGroup(){
		return GROUP_CLASS;
	}
	
	@Override
	public boolean canParse(String line) {
		return getMatcher(line).find();
	}
	
	protected String getName(String line){
		Matcher m = getMatcher(line);
		if(m.find()){
			return m.group(getNameGroup());
		}else{
			return null;
		}
	}
	
	protected boolean isNamed(String line){
		return getName(line) != null;
	}
	
	@SuppressWarnings("unchecked")
	protected <E extends A> Class<E> parseClass(String line) throws LogicParserException{
		Matcher m = getMatcher(line);
		if(m.find()){
			String className = m.group(getClassGroup());
			try {
				Class<E> myClass = 
						(Class<E>) LogicLoader.class.getClassLoader().loadClass(className);
				
				return myClass;
			} catch (ClassNotFoundException e) {
				throw new LogicParserException("Cannot find class " + className,e);
			}
		}else{
			throw new LogicParserException("Cannot find class in line " + line);
		}
	}

	protected abstract <E extends A> ParsedClass<E> makeParsedClass(String line, Class<E> c);
}
