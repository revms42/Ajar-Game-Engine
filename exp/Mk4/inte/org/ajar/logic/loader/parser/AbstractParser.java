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
 * AbstractParser.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser;

import java.util.regex.Matcher;

import org.ajar.logic.loader.IParser;

/**
 * @author mstockbr
 *
 */
public abstract class AbstractParser<A extends Object> implements IParser<A> {
	
	public static String GROUP_NAME="name";
	public static String CHARS = "a-zA-Z0-9_\\-\\.";
	
	protected abstract Matcher getMatcher(String line);
	
	protected String getNameGroup(){
		return GROUP_NAME;
	}
	
	@Override
	public boolean canParse(String line) {
		return getMatcher(line).find();
	}
	
	protected String getName(String line){
		if(line != null){
			Matcher m = getMatcher(line);
			if(m.find()){
				return m.group(getNameGroup());
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	protected boolean isNamed(String line){
		return getName(line) != null;
	}
}
