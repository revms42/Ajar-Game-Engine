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

import org.ajar.age.Attributes;
import org.ajar.age.logic.DefaultState;
import org.ajar.logic.loader.capsule.ParsedClass;
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.capsule.StateObject;
import org.ajar.logic.loader.parser.ActionClassParser;
import org.ajar.logic.loader.parser.ActionInstanceParser;
import org.ajar.logic.loader.parser.ActionMemberParser;
import org.ajar.logic.loader.parser.BooleanParser;
import org.ajar.logic.loader.parser.ChainClassParser;
import org.ajar.logic.loader.parser.ChainInstanceParser;
import org.ajar.logic.loader.parser.ChainMemberParser;
import org.ajar.logic.loader.parser.CharParser;
import org.ajar.logic.loader.parser.ConditionClassParser;
import org.ajar.logic.loader.parser.ConditionInstanceParser;
import org.ajar.logic.loader.parser.ConditionMemberParser;
import org.ajar.logic.loader.parser.EffectClassParser;
import org.ajar.logic.loader.parser.EffectInstanceParser;
import org.ajar.logic.loader.parser.EffectMemberParser;
import org.ajar.logic.loader.parser.FloatParser;
import org.ajar.logic.loader.parser.IntParser;
import org.ajar.logic.loader.parser.LongParser;
import org.ajar.logic.loader.parser.DoubleParser;
import org.ajar.logic.loader.parser.StateClassParser;
import org.ajar.logic.loader.parser.StateInstanceParser;
import org.ajar.logic.loader.parser.StateMemberParser;
import org.ajar.logic.loader.parser.StringParser;

/**
 * @author reverend
 *
 */
public class LogicLoader {
	private final static Vector<IParser<?>> topLevelParsers;
	private final static Vector<IParser<?>> memberParsers;
	private final static Vector<IArgParser<?>> argumentParsers;
	
	static {
		argumentParsers = new Vector<IArgParser<?>>();
		memberParsers = new Vector<IParser<?>>();
		topLevelParsers = new Vector<IParser<?>>();
		addDefaultParsers();
	}

	private static void addDefaultParsers(){
		addArgumentParser(new IntParser());
		addArgumentParser(new LongParser());
		addArgumentParser(new FloatParser());
		addArgumentParser(new DoubleParser());
		addArgumentParser(new CharParser());
		addArgumentParser(new BooleanParser());
		addArgumentParser(new StringParser());
		
		ActionClassParser acp = new ActionClassParser();
		ActionMemberParser amp = new ActionMemberParser(acp);
		ActionInstanceParser aip = new ActionInstanceParser(amp);
		addTopLevelParser(acp);addMemberParser(amp);addTopLevelParser(aip);
		
		EffectClassParser<Attributes> ecp = new EffectClassParser<Attributes>();
		EffectMemberParser<Attributes> emp = new EffectMemberParser<Attributes>(ecp);
		EffectInstanceParser<Attributes> eip = new EffectInstanceParser<Attributes>(emp);
		addTopLevelParser(ecp);addMemberParser(emp);addTopLevelParser(eip);
		
		ChainClassParser<Attributes> ccp = new ChainClassParser<Attributes>();
		ChainMemberParser<Attributes> cmp = new ChainMemberParser<Attributes>(ccp);
		ChainInstanceParser<Attributes> cip = new ChainInstanceParser<Attributes>(cmp);
		addTopLevelParser(ccp);addMemberParser(cmp);addTopLevelParser(cip);
		
		ConditionClassParser<Attributes> ocp = new ConditionClassParser<Attributes>();
		ConditionMemberParser<Attributes> omp = new ConditionMemberParser<Attributes>(ocp);
		ConditionInstanceParser<Attributes> oip = new ConditionInstanceParser<Attributes>(omp);
		addTopLevelParser(ocp);addMemberParser(omp);addTopLevelParser(oip);
		
		StateClassParser<Attributes> scp = new StateClassParser<Attributes>();
		StateMemberParser<Attributes> smp = new StateMemberParser<Attributes>(scp);
		StateInstanceParser<Attributes> sip = new StateInstanceParser<Attributes>(smp);
		addTopLevelParser(scp);/*addMemberParser(omp);*/addTopLevelParser(sip);
	}
	
	public static void parseFile(String data){
		String[] lines = data.split("\\}");
		
		parseLines:
		for(String line : lines){
			line = line + "}";
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
						if(type.isAssignableFrom(p.getParsedClass(null).objectClass())){
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
	
	public static IParser<?> findTopLevelParser(String line, Class<?> type){
		for(IParser<?> p : topLevelParsers){
			if(p.canParse(line)){
				if(type != null){
					try {
						IParsedClass<?> pc = p.getParsedClass(line);
						if(pc != null && pc.isSubClassOf(type)){
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
	
	public static IArgParser<?> findArgumentParser(String line, Class<?> type){
		for(IArgParser<?> p : argumentParsers){
			if(p.canParse(line)){
				if(type != null){
					if(p.assignableFrom(type)){
						return p;
					}
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
	
	public static void addArgumentParser(IArgParser<?> p){
		argumentParsers.add(p);
	}
	
	public static void addTopLevelParser(IParser<?> p){
		topLevelParsers.add(p);
	}
	
	public static StateObject<?,?> findState(String name) throws LogicParserException{
		StateObject<?,?> object = (StateObject<?, ?>) ParsedObject.getNamedObject(name);
		if(object == null){
			object = new StateObject<Attributes,DefaultState<Attributes>>();
			ParsedObject.putNamedObject(name, object);
		}
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Object> A read(Class<A> c, String name){
		ParsedObject<?> o = ParsedObject.getNamedObject(name);
		if(o.isSubClassOf(c)){
			return (A) o;
		}else{
			return null;
		}
	}
	
	public static void clearCaches(){
		ParsedObject.clearCache();
		ParsedClass.clearCache();
		topLevelParsers.clear();
		memberParsers.clear();
		argumentParsers.clear();
		addDefaultParsers();
	}
}
