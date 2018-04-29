/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 30, 2014 Matthew Stockbridge
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
 * org.ajar.logic.loader.parser.test
 * StateInstanceParserTest.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser.test;

import static org.junit.Assert.*;

import org.ajar.age.logic.Action;
import org.ajar.age.logic.ChainableEffect;
import org.ajar.age.logic.Condition;
import org.ajar.age.logic.DefaultEvent;
import org.ajar.age.logic.DefaultState;
import org.ajar.age.logic.DerivedState;
import org.ajar.age.logic.Effect;
import org.ajar.age.logic.HashAttributes;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.capsule.StateObject;
import org.ajar.logic.loader.parser.StateClassParser;
import org.ajar.logic.loader.parser.StateInstanceParser;
import org.ajar.logic.loader.parser.StateMemberParser;
import org.ajar.logic.loader.parser.test.type.DummyEffect;
import org.junit.Before;
import org.junit.Test;

/**
 * @author reverend
 *
 */
public class StateInstanceParserTest {

	public final static String actionClass =
			"Action:DummyAction{org.ajar.logic.loader.parser.test.type.DummyAction}";
	public final static String effectClass = 
			"Effect:DummyEffect{org.ajar.logic.loader.parser.test.type.DummyEffect}";
	public final static String chainClass = 
			"Chain:DummyChain{org.ajar.logic.loader.parser.test.type.DummyChain}";
	public final static String conditionClass = 
			"Condition:DummyCondition{org.ajar.logic.loader.parser.test.type.DummyCondition}";
	
	public final static String actionName1 = "DummyAction1";
	public final static String actionName2 = "DummyAction2";
	public final static String actionName3 = "DummyAction3";
	
	public final static String actionInstance1 =
			"Action:" + actionName1 + "{*DummyAction}";
	public final static String actionInstance2 =
			"Action:" + actionName2 + "{*DummyAction}";
	public final static String actionInstance3 =
			"Action:" + actionName3 + "{*DummyAction}";

	public final static String effectInstance1 =
			"Effect:DummyEffect1{*DummyEffect}";
	public final static String effectInstance2 =
			"Effect:DummyEffect2{*DummyEffect=DummyNamed2}";
	
	
	public final static String chainInstance1 = 
			"Chain:DummyChain1{*DummyChain&}";
	public final static String chainInstance2 = 
			"Chain:DummyChain2{*DummyChain&*DummyChain&*DummyChain=DummyNamed2}";
	
//	public final static String conditionInstance1 = 
//			"Condition:DummyCondition1{*DummyCondition}";
	public final static String conditionInstance2 = 
			"Condition:DummyCondition2{*DummyCondition?*DummyEffect=DummyNamed2|*DummyEffect=DummyNamed3}";
	
	public final static String namedClass = 
			"State:DummyState{org.ajar.logic.loader.parser.test.type.DummyState}";
	
	public final static String namedEState = 
			"State:DummyNamed1{" + actionName1 + "->*DummyEffect=DummyNamed1\n"
			+ actionName2 + "->DummyEffect1\n"
			+ actionName3 + "->DummyEffect2}";
	public final static String namedCState = 
			"State:DummyNamed2{" + actionName1 + "->*DummyChain&*DummyChain&*DummyChain=DummyNamed2\n"
			+ actionName2 + "->DummyChain1\n"
			+ actionName3 + "->DummyChain2}";
	public final static String namedNState = 
			"State:DummyNamed3{" + actionName1 + "->*DummyCondition?*DummyEffect=DummyNamed2|*DummyEffect=DummyNamed3\n"
			+ actionName2 + "->*DummyCondition?*DummyEffect=DummyNamed2|*DummyEffect=DummyNamed3\n"
			+ actionName3 + "->DummyCondition2}";
	//State:DummyNamed4(^DummyNamed1){DummyAction1->*DummyEffect=DummyNamed3}
	public final static String namedDState = 
			"State:DummyNamed4(^DummyNamed1){" 
			+ actionName1 + "->*DummyEffect=DummyNamed3}";
	
	private StateClassParser<HashAttributes> classParser;
	private StateMemberParser<HashAttributes> memberParser;
	private StateInstanceParser<HashAttributes> parser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LogicLoader.clearCaches();
		classParser = new StateClassParser<HashAttributes>();
		classParser.getParsedClass(namedClass);
		
		memberParser = new StateMemberParser<HashAttributes>(classParser);
		
		parser = new StateInstanceParser<HashAttributes>(memberParser);
		
		LogicLoader.findTopLevelParser(actionClass, Action.class).getParsedClass(actionClass);
		LogicLoader.findTopLevelParser(actionInstance1, Action.class).getParsedClass(actionInstance1);
		LogicLoader.findTopLevelParser(actionInstance2, Action.class).getParsedClass(actionInstance2);
		LogicLoader.findTopLevelParser(actionInstance3, Action.class).getParsedClass(actionInstance3);
		
		LogicLoader.findTopLevelParser(effectClass, Effect.class).getParsedClass(effectClass);
		LogicLoader.findTopLevelParser(effectInstance1, Effect.class).getParsedClass(effectInstance1);
		LogicLoader.findTopLevelParser(effectInstance2, Effect.class).getParsedClass(effectInstance2);
		
		LogicLoader.findTopLevelParser(chainClass, ChainableEffect.class).getParsedClass(chainClass);
		LogicLoader.findTopLevelParser(chainInstance1, ChainableEffect.class).getParsedClass(chainInstance1);
		LogicLoader.findTopLevelParser(chainInstance2, ChainableEffect.class).getParsedClass(chainInstance2);
		
		LogicLoader.findTopLevelParser(conditionClass, Condition.class).getParsedClass(conditionClass);
//		LogicLoader.findTopLevelParser(conditionInstance1, Condition.class).getParsedClass(conditionInstance1);
		LogicLoader.findTopLevelParser(conditionInstance2, Condition.class).getParsedClass(conditionInstance2);
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractInstanceParser#getParsedClass(java.lang.String)}.
	 * @throws LogicParserException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetParsedClass() throws LogicParserException {
		try {
			//Effect State
			IParsedClass<?> pc = parser.getParsedClass(namedEState);
			parser.getParsedClass(namedCState);
			parser.getParsedClass(namedNState);
			assertNotNull("No output for full-path-null!",pc);
			assertTrue("Named Effect State is not an State object!", pc.getClass() == StateObject.class);
			assertTrue("Named Effect State is not Default State!", pc.objectClass() == DefaultState.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyNamed1"));
			DefaultState<HashAttributes> d = 
					((StateObject<HashAttributes,DefaultState<HashAttributes>>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNotNull(d.getEffectMap());
			
			Action action1 = (Action) ParsedObject.getNamedObject(actionName1).getParsedObject();
			assertEquals(d.getEffectMap().get(action1).getClass(),DummyEffect.class);
			assertEquals(((DummyEffect)d.getEffectMap().get(action1)).getResultantState(), d);
			
			Action action2 = (Action) ParsedObject.getNamedObject(actionName2).getParsedObject();
			assertEquals(d.getEffectMap().get(action2).getClass(),DummyEffect.class);
			assertEquals(((DummyEffect)d.getEffectMap().get(action2)).getResultantState(), null);
			
			Action action3 = (Action) ParsedObject.getNamedObject(actionName3).getParsedObject();
			DefaultState<HashAttributes> state2 = 
					(DefaultState<HashAttributes>) ParsedObject.getNamedObject("DummyNamed2").getParsedObject();
			assertEquals(d.getEffectMap().get(action3).getClass(),DummyEffect.class);
			assertEquals(((DummyEffect)d.getEffectMap().get(action3)).getResultantState(), state2);

		} catch (LogicParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractInstanceParser#getParsedClass(java.lang.String)}.
	 * @throws LogicParserException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetParsedClassDerived() throws LogicParserException {
		try {
			//Derived State
			parser.getParsedClass(namedEState);
			IParsedClass<?> pc = parser.getParsedClass(namedDState);
			parser.getParsedClass(namedCState);
			parser.getParsedClass(namedNState);
			assertNotNull("No output for full-path-null!",pc);
			assertTrue("Named Derived State is not an State object!", pc.getClass() == StateObject.class);
			assertTrue("Named Derived State is not Derived State!", pc.objectClass() == DerivedState.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyNamed4"));
			DerivedState<HashAttributes> d = 
					((StateObject<HashAttributes,DerivedState<HashAttributes>>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNotNull(d.getEffectMap());
			
			Action action2 = (Action) ParsedObject.getNamedObject(actionName2).getParsedObject();
			//assertEquals(d.getEffectMap().get(action2).getClass(),DummyEffect.class);
			assertEquals(d.perform(null, new DefaultEvent<HashAttributes>(action2,null)), null);
			
			Action action3 = (Action) ParsedObject.getNamedObject(actionName3).getParsedObject();
			DefaultState<HashAttributes> state2 = 
					(DefaultState<HashAttributes>) ParsedObject.getNamedObject("DummyNamed2").getParsedObject();
			//assertEquals(d.getEffectMap().get(action3).getClass(),DummyEffect.class);
			assertEquals(d.perform(null, new DefaultEvent<HashAttributes>(action3,null)), state2);
			
			Action action1 = (Action) ParsedObject.getNamedObject(actionName1).getParsedObject();
			DefaultState<HashAttributes> state3 = 
					(DefaultState<HashAttributes>) ParsedObject.getNamedObject("DummyNamed3").getParsedObject();
			assertEquals(d.getEffectMap().get(action1).getClass(),DummyEffect.class);
			assertEquals(((DummyEffect)d.getEffectMap().get(action1)).getResultantState(), state3);

		} catch (LogicParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Cannot parse good instance!",parser.canParse(namedEState));
	}

}
