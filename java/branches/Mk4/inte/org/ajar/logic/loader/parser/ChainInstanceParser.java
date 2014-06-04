/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 30, 2014 Matthew Stockbridge
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
 * ChainInstanceParser.java
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
import org.ajar.age.logic.ChainableEffect;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ChainObject;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 * TODO: Rewriting to use member parsers instead of class parsers.
 */
public class ChainInstanceParser<A extends Attributes> extends AbstractInstanceParser<ChainableEffect<A>> {

	private final static Pattern instancePattern = 
			Pattern.compile("[cC]hain:(?<" + GROUP_NAME +">\\w+)\\{\\*(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)\\&?|(\\(.*?\\))?(=\\w+)?\\&?.*\\}");
	
	/**
	 * @param classParser
	 */
	public ChainInstanceParser(ChainClassParser<A> classParser) {
		super(classParser);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return instancePattern.matcher(line);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractInstanceParser#makeParsedObject(org.ajar.logic.loader.capsule.ParsedClass, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected <E extends ChainableEffect<A>> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		//Get the guts of the chain.
		line = line.split("\\{",2)[1];
		if(line.endsWith("}")) line = line.substring(0, line.length()-1);
		
		String[] nextLine = line.split("\\&",2);
		
		ChainObject<E> first = makeChainObject(nextLine[0],null,type);
		
		if(nextLine.length > 1){
			line = nextLine[1];
			ChainObject last = first;
			while(line.contains("&")){
				nextLine = line.split("\\&",2);
				
				last = makeChainObject(nextLine[0],last,null);
				
				line = nextLine[1];
			}
			last = makeChainObject(nextLine[1],last,null);
		}

		return first;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ChainObject makeChainObject(String line, ChainObject last, ParsedClass<?> type) throws LogicParserException {
		ParsedObject<?> po = ParsedObject.getNamedObject(line);
		ChainObject next = null;
		if(po != null && po instanceof ChainObject){
			next = (ChainObject)po;
		}else if(line.startsWith("*")){
			String newLine = line.substring(1);
			if(type == null){
				next = new ChainObject(newLine, parseClass(newLine));
			}else{
				next = new ChainObject(newLine,type);
			}
		}else{
			throw new LogicParserException("Couldn't find chain class " + line);
		}
		if(last != null){
			last.addChild(next);
		}
		
		return next;
	}
}
