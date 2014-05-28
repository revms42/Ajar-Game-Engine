/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 28, 2014 Matthew Stockbridge
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
 * ConditionClassParser.java
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

import org.ajar.age.logic.Condition;
import org.ajar.logic.loader.capsule.ConditionClass;
import org.ajar.logic.loader.capsule.ParsedClass;

/**
 * @author mstockbr
 *
 */
public class ConditionClassParser extends AbstractClassParser<Condition<?>> {

	private final static Pattern conditionPattern = 
			Pattern.compile("[cC]ondition\\:(?<" + GROUP_NAME +">\\w+)=(?<" + GROUP_CLASS + ">.+)");
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractClassParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return conditionPattern.matcher(line);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractClassParser#makeParsedClass(java.lang.String, java.lang.Class)
	 */
	@Override
	protected <E extends Condition<?>> ParsedClass<E> makeParsedClass(String line, Class<E> c) {
		return new ConditionClass<E>(line,c);
	}

}
