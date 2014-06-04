/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 4, 2014 Matthew Stockbridge
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
 * ConditionInstanceParser.java
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
 * TODO: Rewriting to use member parsers instead of class parsers.
 */
public class ConditionInstanceParser<A extends Attributes> extends AbstractInstanceParser<Condition<A>> {
	
	public static final String GROUP_TRUE = "true";
	public static final String GROUP_FALSE = "false";
	
	private final static Pattern instancePattern = 
			Pattern.compile("[cC]ondition:(?<" + GROUP_NAME +">\\w+)\\{\\*(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)\\?" + 
					"(?<" + GROUP_TRUE + ">.+?)\\|(?<" + GROUP_FALSE + ">.+?)\\}");
	
	/**
	 * @param classParser
	 */
	public ConditionInstanceParser(ConditionClassParser<A> classParser) {
		super(classParser);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractInstanceParser#makeParsedObject(org.ajar.logic.loader.capsule.ParsedClass, java.lang.String)
	 */
	@Override
	protected <E extends Condition<A>> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		ConditionObject<E> condition = new ConditionObject<E>(line,type);
		
		Matcher m = getMatcher(line);
		
		if(m.find()){
			String trueGroup = m.group(GROUP_TRUE);
			
			if(trueGroup != null){
				IParser<?> trueParser = LogicLoader.findMemberParser(trueGroup, Effect.class);
				
				if(trueParser != null){
					IParsedClass<?> trueClass = trueParser.getParsedClass(trueGroup);
					
					if(trueClass instanceof EffectObject){
						condition.setTrueEffect((EffectObject<?>)trueClass);
					}else{
						condition.setTrueEffect(createNewInstance(trueClass,trueGroup));
					}
				}else{
					throw new LogicParserException("A parser for '" + trueGroup + "' could not be located!");
				}
			}
			
			String falseGroup = m.group(GROUP_FALSE);
			
			if(falseGroup != null){
				IParser<?> trueParser = LogicLoader.findMemberParser(falseGroup, Effect.class);
				
				if(trueParser != null){
					IParsedClass<?> falseClass = trueParser.getParsedClass(falseGroup);
					
					if(falseClass instanceof EffectObject){
						condition.setFalseEffect((EffectObject<?>)falseClass);
					}else{
						condition.setFalseEffect(createNewInstance(falseClass,trueGroup));
					}
					
				}else{
					throw new LogicParserException("A parser for '" + falseGroup + "' could not be located!");
				}
			}
		}
		
		return condition;
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return instancePattern.matcher(line);
	}

	private EffectObject<?> createNewInstance(IParsedClass<?> protoType, String line){
		//TODO: Before we can do this right we need member parsers.
	}
}
