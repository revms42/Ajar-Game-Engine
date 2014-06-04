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
import java.util.regex.Pattern;

import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractInstanceParser<A extends Object> extends AbstractParser<A> {
	
	public static String GROUP_NAME="name";
	public static String GROUP_DEFINITION="definition";
	
	protected final static Pattern definition = Pattern.compile("\\{(?<" + GROUP_DEFINITION + ">.+)\\}$");
	
	private final AbstractMemberParser<A> memberParser;
	
	public AbstractInstanceParser(AbstractMemberParser<A> memberParser){
		this.memberParser = memberParser;
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
				String definition = getDefinition(line);
				
				p = (ParsedObject<E>) memberParser.getParsedClass(definition);
				
				ParsedObject.putNamedObject(name, p);
			}
			
			return p;
		}else{
			String definition = getDefinition(line);
			return (ParsedObject<E>) memberParser.getParsedClass(definition);
		}
	}
	
	protected String getDefinition(String line) throws LogicParserException {
		Matcher m = definition.matcher(line);
		
		if(m.find()){
			return m.group(GROUP_DEFINITION);
		}else{
			throw new LogicParserException("Cannot find definition in line " + line);
		}
	}
}
