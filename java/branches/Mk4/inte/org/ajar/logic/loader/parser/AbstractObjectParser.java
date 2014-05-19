/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 18, 2014 Matthew Stockbridge
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
 * AbstractObjectParser.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser;

import org.ajar.logic.loader.IParsedObject;
import org.ajar.logic.loader.IParser;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ParsedObject;

/**
 * @author reverend
 *
 */
public abstract class AbstractObjectParser<A extends Object> implements IParser<A> {

	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParser#getParsedClass(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IParsedObject<A> getParsedClass(String line) throws LogicParserException {
		if(isNamed(line)){
			String name = getName(line);
			ParsedObject<A> p = (ParsedObject<A>) ParsedObject.getNamedObject(name);
			
			if(p == null){
				p = makeParsedObject(line);
				
				ParsedObject.putNamedObject(name, p);
			}
			
			return p;
		}else{
			return makeParsedObject(line);
		}
	}

	protected abstract boolean isObject(String line);
	protected abstract boolean isNamed(String line);
	protected abstract String getName(String line);
	protected abstract ParsedObject<A> makeParsedObject(String line);
}
