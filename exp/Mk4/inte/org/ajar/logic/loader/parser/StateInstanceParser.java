/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 30, 2014 Matthew Stockbridge
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
 * StateInstanceParser.java
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
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.capsule.StateObject;

/**
 * @author reverend
 *
 */
public class StateInstanceParser<A extends Attributes> extends AbstractInstanceParser<DefaultState<A>> {

	private final static String GROUP_ARGS = "args";
	
	private final Pattern instancePattern;
	/**
	 * @param memberParser
	 */
	public StateInstanceParser(StateMemberParser<A> memberParser) {
		super(memberParser);
		instancePattern = Pattern.compile(
				"[sS]tate:(?<" + GROUP_NAME +">[" + CHARS + "]+)(\\((?<" + GROUP_ARGS + ">[" + CHARS + "\\s\\^\\,]+)\\))?\\s*\\{\\s*" +
				memberParser.getMapPattern() +
				"\\s*\\}"
		);
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParser#getParsedClass(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IParsedClass<A> getParsedClass(String line) throws LogicParserException {
		if(isNamed(line)){
			String name = getName(line);
			StateObject<A,?> p = (StateObject<A,?>) ParsedObject.getNamedObject(name);
			
			if(p == null){
				String definition = getDefinition(line);
				
				String args = getArgs(line);
				if(args != null){
					definition = "(" + args + ")" + definition;
				}
				
				p = (StateObject<A,?>) memberParser.getParsedClass(definition);
				
				ParsedObject.putNamedObject(name, p);
			}else if(p.lineDefinition() == null){
				String definition = getDefinition(line);
				
				p.setLineDefinition(definition);
			}
			
			return (IParsedClass<A>) p;
		}else{
			throw new LogicParserException("States must all be named! State: " + line);
		}
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return instancePattern.matcher(line);
	}

	protected String getArgs(String line) {
		if(line != null){
			Matcher m = getMatcher(line);
			
			if(m.find()){
				try{
					return m.group(GROUP_ARGS);
				}catch(IllegalArgumentException iae){};
			}
		}
		
		return null;
	}
}
