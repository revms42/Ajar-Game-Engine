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

import org.ajar.age.logic.Action;
import org.ajar.age.Attributes;
import org.ajar.age.logic.DefaultState;
import org.ajar.age.logic.Effect;
import org.ajar.logic.loader.IArgParser;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.IParsedObject;
import org.ajar.logic.loader.IParser;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;

/**
 * @author reverend
 *
 */
public class StateObject<H extends Attributes, A extends DefaultState<H>> extends ParsedObject<A> {
	
	private boolean populated;
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
	
	public boolean setLineDefinition(String line){
		if(line == null){
			this.line = line;
			return true;
		}else{
			return false;
		}
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
	
	/* (non-Javadoc)
	 * @see org.ajar.logic.loader.IParsedObject#getParsedObject()
	 */
	@Override
	public A getParsedObject() throws LogicParserException{
		super.getParsedObject();
		
		if(instance != null && !populated){
			populateState();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	private void populateState() throws LogicParserException{
		this.populated = true;
		String[] mappings = lineDefinition().split("\n");
		
		for(String line : mappings){
			String[] actionEffect = line.split("\\Q->\\E");
			
			IParser<Action> actionParser = 
				(IParser<Action>) LogicLoader.findMemberParser(actionEffect[0], Action.class);
			
			if(actionParser != null){
				IParsedObject<Action> a = (IParsedObject<Action>) actionParser.getParsedClass(actionEffect[0]);
				
				if(a == null) throw new LogicParserException(actionEffect[0] + " is parsed to null!");
				
				IParser<Effect<?>> effectParser = 
					(IParser<Effect<?>>) LogicLoader.findMemberParser(actionEffect[1], Effect.class);
				
				if(effectParser != null){
					IParsedObject<Effect<?>> e = (IParsedObject<Effect<?>>) effectParser.getParsedClass(actionEffect[1]);
					
					if(e == null) throw new LogicParserException(actionEffect[1] + " is parsed to null!");
					
					instance.put(a.getParsedObject(), (Effect<H>) e.getParsedObject());
				}else{
					throw new LogicParserException(actionEffect[0] + " is not a recognized effect mapping!");
				}
			}else{
				throw new LogicParserException(actionEffect[0] + " is not a recognized action!");
			}
		}
	}
}
