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
 * org.ajar.logic.loader
 * LogicLoader.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader;

import java.util.Vector;

import org.ajar.logic.loader.capsule.StateObject;

/**
 * @author reverend
 *
 */
public class LogicLoader {
	private final static Vector<IParser<?>> topLevelParsers;
	private final static Vector<IParser<?>> memberParsers;
	private final static Vector<IParser<?>> argumentParsers;
	
	static {
		topLevelParsers = new Vector<IParser<?>>();
		memberParsers = new Vector<IParser<?>>();
		argumentParsers = new Vector<IParser<?>>();
	}

	public static void parseFile(String data){
		String[] lines = data.split("\\;|\\}");
		
		parseLines:
		for(String line : lines){
			for(IParser<?> p : topLevelParsers){
				if(p.canParse(line)){
					try{
						IParsedClass<?> c = p.getParsedClass(line);
						if(c == null){
							throw new LogicParserException("Could not parse class!");
						}else{
							continue parseLines;
						}
					}catch(LogicParserException e){
						e.printStackTrace();
						return;
					}
				}
			}
			(new LogicParserException("No parser found!")).printStackTrace();
			return;
		}
	}
	
	public static IParser<?> findMemberParser(String line, Class<?> type){
		for(IParser<?> p : memberParsers){
			if(p.canParse(line)){
				if(type != null){
					try {
						if(p.getParsedClass(null).assignableFrom(type)){
							return p;
						}
					} catch (LogicParserException e) {}
				}else{
					return p;
				}
			}
		}
		return null;
	}
	
	public static IParser<?> findArgumentParser(String line, Class<?> type){
		for(IParser<?> p : argumentParsers){
			if(p.canParse(line)){
				if(type != null){
					try {
						if(p.getParsedClass(null).assignableFrom(type)){
							return p;
						}
					} catch (LogicParserException e) {}
				}else{
					return p;
				}
			}
		}
		return null;
	}
	
	public static void addMemberParser(IParser<?> p){
		memberParsers.add(p);
	}
	
	public static void addArgumentParser(IParser<?> p){
		argumentParsers.add(p);
	}
	
	public static void addTopLevelParser(IParser<?> p){
		topLevelParsers.add(p);
	}
	
	public static StateObject<?> findState(String name){
		//TODO: Find state.
	}
}
