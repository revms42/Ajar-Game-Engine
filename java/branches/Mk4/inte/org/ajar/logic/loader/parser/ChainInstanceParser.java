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
 *
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <E extends ChainableEffect<A>> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		String[] nextLine = line.split("\\&",2);
		
		ChainObject<E> first = new ChainObject<E>(nextLine[0],type);
		
		//TODO: This skips the last chain element because it doesn't have a '&'
		boo
		if(nextLine.length > 1){
			line = nextLine[1];
			ChainObject last = first;
			ChainObject next = null;
			while(line.contains("&")){
				nextLine = line.split("\\&",2);
				ParsedObject<?> po = ParsedObject.getNamedObject(nextLine[0]);
				if(po != null && po instanceof ChainObject){
					next = (ChainObject)po;
				}else if(nextLine[0].startsWith("*")){
					String newLine = nextLine[0].substring(1);
					next = new ChainObject(newLine,parseClass(newLine));
				}else{
					throw new LogicParserException("Couldn't find chain class " + nextLine[0]);
				}
				
				last.addChild(next);
				last = next;
				
				line = nextLine[1];
			}
		}

		return first;
	}

}
