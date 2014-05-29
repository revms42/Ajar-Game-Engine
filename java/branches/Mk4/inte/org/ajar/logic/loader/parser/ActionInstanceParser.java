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
 * ActionObjectParser.java
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
import org.ajar.logic.loader.capsule.ActionObject;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author mstockbr
 *
 */
public class ActionInstanceParser extends AbstractInstanceParser<Action> {

	private final static Pattern instancePattern = 
			Pattern.compile("[aA]ction:(?<" + GROUP_NAME +">\\w+)\\{\\*(?<" + GROUP_CLASS + ">[a-zA-Z0-9_\\-\\.]+)((?:\\().*?(?:\\)))?\\}");
	/**
	 * @param classParser
	 */
	public ActionInstanceParser(ActionClassParser classParser) {
		super(classParser);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractInstanceParser#makeParsedObject(org.ajar.logic.loader.capsule.ParsedClass, java.util.List, java.lang.String)
	 */
	@Override
	protected <E extends Action> ParsedObject<E> makeParsedObject(ParsedClass<E> type, String line) {
		return new ActionObject<E>(line,type);
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.parser.AbstractParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return instancePattern.matcher(line);
	}

}
