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
 * EffectInstanceParser.java
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
import org.ajar.age.logic.Effect;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.EffectObject;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 * TODO: Rewriting to use member parsers instead of class parsers.
 */
public class EffectInstanceParser<A extends Attributes> extends AbstractInstanceParser<Effect<A>> {

	private final static Pattern instancePattern = 
			Pattern.compile("[eE]ffect:(?<" + GROUP_NAME +">\\w+)\\{\\*(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)(\\(.*?\\))?(=\\w+)?\\}");
	
	/**
	 * @param classParser
	 */
	public EffectInstanceParser(EffectClassParser<A> classParser) {
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
	@Override
	protected <E extends Effect<A>> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		return new EffectObject<E>(line,type);
	}

}
