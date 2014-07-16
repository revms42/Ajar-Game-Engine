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
 * ChainMemberParser.java
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
import org.ajar.logic.loader.IParser;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ChainObject;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 *
 */
public class ChainMemberParser<A extends Attributes> extends AbstractMemberParser<ChainableEffect<A>> {

	private final static String patternString = "(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)(\\&|(\\(.*?\\))\\&|\\=).*";
	
	private final static Pattern instancePattern = 
			Pattern.compile("^(?<!\\||\\?)\\*?" + patternString + "(?!\\||\\?)$");
	
	/**
	 * @param classParser
	 */
	public ChainMemberParser(ChainClassParser<A> classParser) {
		super(classParser);
	}
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractMemberParser#makeParsedObject(org.ajar.logic.loader.capsule.ParsedClass, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <E extends ChainableEffect<A>> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		String[] nextLine = line.split("\\&",2);
		
		ChainObject<E> first = makeChainObject(nextLine[0],null,type);
		
//		if(nextLine.length > 1){
//			line = nextLine[1];
//			ChainObject last = first;
//			while(line.contains("&")){
//				nextLine = line.split("\\&",2);
//				
//				last = makeChainObject(nextLine[0],last,null);
//				
//				line = nextLine[1];
//			}
//			last = makeChainObject(nextLine[1],last,null);
//		}
		
		if(nextLine.length > 1 && nextLine[1].length() > 0){
			IParser<ChainableEffect<A>> secondParser = 
					(IParser<ChainableEffect<A>>) 
					LogicLoader.findMemberParser(nextLine[1], ChainableEffect.class);
			
			ChainObject<E> secondPO = 
					(ChainObject<E>)
					secondParser.getParsedClass(nextLine[1]);
			
			first.addChild(secondPO);
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

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractMemberParser#getMatcherPattern()
	 */
	@Override
	protected String getMatcherPattern() {
		return patternString;
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return instancePattern.matcher(line);
	}
}
