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
 * LongParser.java
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

import org.ajar.logic.loader.AbstractArgParser;

/**
 * @author mstockbr
 *
 */
public class LongParser extends AbstractArgParser<Long> {
	private final static Pattern p = Pattern.compile("^\\d+[lL]$");
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IArgParser#parse(java.lang.String)
	 */
	@Override
	public Long parse(String line) {
		Matcher m = getMatcher(line);
		
		if(m.find()){
			return Long.parseLong(m.group().split("[lL]")[0]);
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IArgParser#assignableFrom(java.lang.Class)
	 */
	@Override
	public boolean assignableFrom(Class<?> c) {
		return c == Long.class || c == Long.TYPE;
	}

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.AbstractArgParser#getMatcher(java.lang.String)
	 */
	@Override
	protected Matcher getMatcher(String line) {
		return p.matcher(line);
	}

}
