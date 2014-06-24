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
 * org.ajar.logic.loader.capsule
 * StateObject.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.capsule;

import java.util.Vector;
import java.util.regex.Matcher;

import org.ajar.age.logic.DefaultState;
import org.ajar.logic.loader.IArgParser;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;

/**
 * @author reverend
 *
 */
public class StateObject<A extends DefaultState<?>> extends ParsedObject<A> {
	
	/**
	 * @param line
	 * @param c
	 */
	public StateObject(String line, IParsedClass<A> c) {
		super(line, c);
	}
	
	public StateObject(){
		super(null,null);
	}
	
	//TODO: It would be good to figure out how to merge this with the parent class definition.
	protected Object[] getArguments() throws LogicParserException{
		if(arguments == null){
			Matcher m = args.matcher(lineDefinition());
			
			Vector<Object> args = new Vector<Object>();
			while(m.find()){
				String arg = m.group(1);
				
				if(arg.startsWith("^")){
					args.add(LogicLoader.findState(arg.substring(1)));
				}else{
					IArgParser<?> p = LogicLoader.findArgumentParser(arg, null);
					Object a = p.parse(arg);
					
					args.add(a);
				}
			}
			
			arguments = args;
		}

		return arguments.toArray(new Object[arguments.size()]);
	}
	
}
