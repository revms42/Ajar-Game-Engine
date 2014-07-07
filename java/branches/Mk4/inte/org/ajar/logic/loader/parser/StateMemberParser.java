/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 5, 2014 Matthew Stockbridge
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
 * StateMemberParser.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ajar.age.Attributes;
import org.ajar.age.logic.DefaultState;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.capsule.StateObject;

/**
 * @author mstockbr
 *
 */
public class StateMemberParser<A extends Attributes> extends AbstractMemberParser<DefaultState<A>> {
	
	private final static String patternString = "(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)?";
	private final static String mapString = "(\\s*\\w+\\Q->\\E.+?\\=.+\\n?)+";
	
	private final static Pattern instancePattern = 
			Pattern.compile(patternString);
	
	private final static Pattern mapPattern = 
			Pattern.compile(mapString);
	
	/**
	 * @param classParser
	 */
	public StateMemberParser(StateClassParser<A> classParser) {
		super(classParser);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractMemberParser#makeParsedObject(org.ajar.logic.loader.capsule.ParsedClass, java.lang.String)
	 */
	@Override
	protected <E extends DefaultState<A>> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		//Should only ever get what's between the {}'s
		return (ParsedObject<E>) new StateObject<A,E>(line, type);
	}

	@Override
	protected <E extends DefaultState<A>> ParsedObject<E> produceObject(String line) throws LogicParserException {
		ParsedClass<E> pc = parseClass(line);
		
		return makeParsedObject(pc,line);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends DefaultState<A>> IParsedClass<E> getParsedClass(String line) throws LogicParserException {
		if(line == null){
			return super.getParsedClass(null);
		}
		
		StateObject<A,E> state = null;
		if(ParsedObject.getNamedObject(line) != null){
			ParsedObject<?>  obj = ParsedObject.getNamedObject(line);
			
			if(obj.isSubClassOf(classParser.getParsedClass(null).objectClass())){
				state = (StateObject<A,E>) obj;
			}
		}else{
			state = (StateObject<A,E>) produceObject(line);
		}
		
		if(state.lineDefinition() == null && getMapMatcher(line).find()){
			state.setLineDefinition(line);
		}
		
		return (IParsedClass<E>) state;
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractMemberParser#getMatcherPattern()
	 */
	@Override
	protected String getMatcherPattern() {
		return patternString;
	}
	
	protected String getMapPattern() {
		return mapString;
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return instancePattern.matcher(line);
	}
	
	public static Matcher getMapMatcher(String line) {
		return mapPattern.matcher(line);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <E extends DefaultState<A>> ParsedClass<E> parseClass(String line) throws LogicParserException{
		Matcher m = getMatcher(line);
		if(ParsedClass.getNamedClass(line) != null){
			return (ParsedClass<E>) ParsedClass.getNamedClass(line);
		}else if(m.find()){
			String className = null;
			try{
				className = m.group(getClassGroup());
			}catch(IllegalArgumentException iae){
				return (ParsedClass<E>) new ParsedClass<DefaultState>(line,DefaultState.class);
			}
			
			ParsedClass<?> p = ParsedClass.getNamedClass(line);
			
			if(p == null){
				try{
					Class<E> c = classParser.loadClass(className);
					
					if(c != null){
						p = classParser.makeParsedClass(line, c);
					}
				}catch(LogicParserException lpe){
					return (ParsedClass<E>) new ParsedClass<DefaultState>(line,DefaultState.class);
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
