/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 29, 2014 Matthew Stockbridge
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
 * AbstractInstanceParser.java
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
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractInstanceParser<A extends Object> extends AbstractParser<A> {

	public static String GROUP_NAME="name";
	public static String GROUP_CLASS="class";
	
	private final AbstractClassParser<A> classParser;
	
	public AbstractInstanceParser(AbstractClassParser<A> classParser){
		this.classParser = classParser;
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParser#getParsedClass(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends A> IParsedClass<E> getParsedClass(String line) throws LogicParserException {
		if(isNamed(line)){
			String name = getName(line);
			ParsedObject<E> p = (ParsedObject<E>) ParsedObject.getNamedObject(name);
			
			if(p == null){
				p = produceObject(line);
				
				ParsedObject.putNamedObject(name, p);
			}
			
			return p;
		}else{
			return produceObject(line);
		}
	}
	
	private <E extends A> ParsedObject<E> produceObject(String line) throws LogicParserException {
		ParsedClass<E> pc = parseClass(line);
		
		return makeParsedObject(pc,line);
	}
	
	protected String getClassGroup(){
		return GROUP_CLASS;
	}
	
	protected abstract <E extends A> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line);

	@SuppressWarnings("unchecked")
	protected <E extends A> ParsedClass<E> parseClass(String line) throws LogicParserException{
		Matcher m = getMatcher(line);
		if(m.find()){
			String className = m.group(getClassGroup());
			
			ParsedClass<?> p = ParsedClass.getNamedClass(className);
			
			if(p == null){
				Class<E> c = classParser.loadClass(className);
				
				if(c != null){
					p = classParser.makeParsedClass(line, c);
				}
			}
			
			if(p == null){
				throw new LogicParserException("Cannot parse class '" + className + "' for instance!");
			}
			
			return (ParsedClass<E>)p;
		}else{
			throw new LogicParserException("Cannot find class in line " + line);
		}
	}
}
