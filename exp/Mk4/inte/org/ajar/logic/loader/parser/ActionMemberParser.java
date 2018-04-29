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
 * ActionMemberParser.java
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

import org.ajar.age.logic.Action;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ActionObject;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 *
 */
public class ActionMemberParser extends AbstractMemberParser<Action> {

	private final static String patternString = 
			"(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)((?:\\().*?(?:\\)))?";
	
	private final static Pattern instancePattern = 
			Pattern.compile("\\*?" + patternString);
	/**
	 * @param classParser
	 */
	public ActionMemberParser(ActionClassParser classParser) {
		super(classParser);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractMemberParser#makeParsedObject(org.ajar.logic.loader.capsule.ParsedClass, java.lang.String)
	 */
	@Override
	protected <E extends Action> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) throws LogicParserException {
		return new ActionObject<E>(line,type);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return instancePattern.matcher(line);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractMemberParser#getMatcherPattern()
	 */
	@Override
	protected String getMatcherPattern() {
		return patternString;
	}

}
