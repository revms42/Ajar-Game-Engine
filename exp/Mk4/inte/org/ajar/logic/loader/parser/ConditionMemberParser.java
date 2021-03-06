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
 * ConditionMemberParser.java
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
import org.ajar.age.logic.Condition;
import org.ajar.age.logic.Effect;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.IParser;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ConditionObject;
import org.ajar.logic.loader.capsule.EffectObject;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 *
 */
public class ConditionMemberParser<A extends Attributes> extends AbstractMemberParser<Condition<A>> {

	public static final String GROUP_TRUE = "true";
	public static final String GROUP_FALSE = "false";
	
	private final static String patternString = "(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)\\?" + 
					"(?<" + GROUP_TRUE + ">.+)\\|(?<" + GROUP_FALSE + ">.+)";
	
	private final static Pattern instancePattern = Pattern.compile("\\*?" + patternString + "$|\n");
	
	/**
	 * @param classParser
	 */
	public ConditionMemberParser(ConditionClassParser<A> classParser) {
		super(classParser);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractMemberParser#makeParsedObject(org.ajar.logic.loader.capsule.ParsedClass, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <E extends Condition<A>> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		ConditionObject<E> condition = new ConditionObject<E>(line,type);
		
		Matcher m = getMatcher(line);
		
		if(m.find()){
			String trueGroup = m.group(GROUP_TRUE);
			
			if(trueGroup != null){
				IParser<Effect<A>> trueParser = (IParser<Effect<A>>) LogicLoader.findMemberParser(trueGroup, Effect.class);
				
				if(trueParser != null){
					IParsedClass<Effect<A>> trueClass = trueParser.getParsedClass(trueGroup);
					
					if(trueClass instanceof EffectObject){
						condition.setTrueEffect((EffectObject<Effect<A>>)trueClass);
					}else{
						throw new LogicParserException("The parser for '" + trueGroup + "' did not return an EffectObject!");
					}
				}else{
					throw new LogicParserException("A parser for '" + trueGroup + "' could not be located!");
				}
			}
			
			String falseGroup = m.group(GROUP_FALSE);
			
			if(falseGroup != null){
				IParser<Effect<A>> falseParser = (IParser<Effect<A>>) LogicLoader.findMemberParser(falseGroup, Effect.class);
				
				if(falseParser != null){
					IParsedClass<Effect<A>> falseClass = falseParser.getParsedClass(falseGroup);
					
					if(falseClass instanceof EffectObject){
						condition.setFalseEffect((EffectObject<Effect<A>>)falseClass);
					}else{
						throw new LogicParserException("The parser for '" + falseGroup + "' did not return an EffectObject!");
					}
					
				}else{
					throw new LogicParserException("A parser for '" + falseGroup + "' could not be located!");
				}
			}
		}
		
		return condition;
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
