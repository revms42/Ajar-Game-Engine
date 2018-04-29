/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 4, 2014 Matthew Stockbridge
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
 * ConditionInstanceParserTest.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser.test;

import static org.junit.Assert.*;

import org.ajar.age.logic.HashAttributes;
import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ConditionObject;
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.parser.ChainClassParser;
import org.ajar.logic.loader.parser.ConditionClassParser;
import org.ajar.logic.loader.parser.ConditionInstanceParser;
import org.ajar.logic.loader.parser.ConditionMemberParser;
import org.ajar.logic.loader.parser.EffectClassParser;
import org.ajar.logic.loader.parser.test.type.DummyChain;
import org.ajar.logic.loader.parser.test.type.DummyCondition;
import org.ajar.logic.loader.parser.test.type.DummyEffect;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class ConditionInstanceParserTest {

	public final static String effectClass = 
			"Effect:DummyEffect{org.ajar.logic.loader.parser.test.type.DummyEffect}";
	public final static String chainClass = 
			"Chain:DummyChain{org.ajar.logic.loader.parser.test.type.DummyChain}";
	public final static String effectInstance = 
			"Effect:DummyEffect{*org.ajar.logic.loader.parser.test.type.DummyEffect}";
	
	public final static String namedClass = 
			"Condition:DummyCondition{org.ajar.logic.loader.parser.test.type.DummyCondition}";
	//Conditions with no = sign will be treated as returning null, or remaining in the same state.
	public final static String namedNewCondition = 
			"Condition:DummyNamedCondition{*DummyCondition?*DummyEffect|*DummyEffect}";
	
	public final static String namedFullCondition = 
			"Condition:DummyFullCondition{*org.ajar.logic.loader.parser.test.type.DummyCondition?" +
			"*org.ajar.logic.loader.parser.test.type.DummyEffect|" +
			"*org.ajar.logic.loader.parser.test.type.DummyEffect}";
	
	public final static String namedComplicatedCondition = 
			"Condition:DummyComplicatedCondition{*DummyCondition?" +
			"*DummyCondition?" +
			"*DummyEffect|" +
			"*DummyEffect|" +
			"*DummyChain&*DummyChain&}";
	
	private ConditionClassParser<HashAttributes> classParser;
	private ConditionMemberParser<HashAttributes> memberParser;
	private ConditionInstanceParser<HashAttributes> parser;
	
	private EffectClassParser<HashAttributes> effectClassParser;
	private ChainClassParser<HashAttributes> chainClassParser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LogicLoader.clearCaches();
		effectClassParser = new EffectClassParser<HashAttributes>();
		effectClassParser.getParsedClass(effectClass);
		
		chainClassParser = new ChainClassParser<HashAttributes>();
		chainClassParser.getParsedClass(chainClass);
		
		classParser = new ConditionClassParser<HashAttributes>();
		classParser.getParsedClass(namedClass);
		
		memberParser = new ConditionMemberParser<HashAttributes>(classParser);
		
		parser = new ConditionInstanceParser<HashAttributes>(memberParser);
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractInstanceParser#getParsedClass(java.lang.String)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetParsedClass() {
		try{
			//Named new condition
			IParsedClass<?> pc = parser.getParsedClass(namedNewCondition);
			assertNotNull("No output for full-path-null!",pc);
			assertTrue("Full-path-null is not an Condition object!", pc.getClass() == ConditionObject.class);
			assertTrue("Full-path-null is not Dummy Condition!", pc.objectClass() == DummyCondition.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyNamedCondition"));
			DummyCondition d = ((ConditionObject<DummyCondition>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null,null));
			assertTrue(d.trueEffect instanceof DummyEffect);
			assertTrue(d.falseEffect instanceof DummyEffect);
			assertTrue(d.trueEffect != d.falseEffect);
			
			//Named full condition
			pc = parser.getParsedClass(namedFullCondition);
			assertNotNull("No output for full-path-null!",pc);
			assertTrue("Full-path-null is not an Condition object!", pc.getClass() == ConditionObject.class);
			assertTrue("Full-path-null is not Dummy Condition!", pc.objectClass() == DummyCondition.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyFullCondition"));
			d = ((ConditionObject<DummyCondition>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null,null));
			assertTrue(d.trueEffect instanceof DummyEffect);
			assertTrue(d.falseEffect instanceof DummyEffect);
			assertTrue(d.trueEffect != d.falseEffect);
			
			//Named complicated condition
			pc = parser.getParsedClass(namedComplicatedCondition);
			assertNotNull("No output for full-path-null!",pc);
			assertTrue("Full-path-null is not an Condition object!", pc.getClass() == ConditionObject.class);
			assertTrue("Full-path-null is not Dummy Condition!", pc.objectClass() == DummyCondition.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyComplicatedCondition"));
			d = ((ConditionObject<DummyCondition>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null,null));
			assertTrue(d.trueEffect instanceof DummyCondition);
			assertTrue(d.falseEffect instanceof DummyChain);
			
			DummyCondition t = (DummyCondition) d.trueEffect;
			assertTrue(t.trueEffect instanceof DummyEffect);
			assertTrue(t.falseEffect instanceof DummyEffect);
			assertTrue(t.trueEffect != t.falseEffect);
			
			DummyChain f = (DummyChain) d.falseEffect;
			assertNotNull(f.getChild());
		}catch(LogicParserException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Cannot parse good instance!",parser.canParse(namedNewCondition));
		assertTrue("Cannot parse good instance!",parser.canParse(namedFullCondition));
		assertTrue("Cannot parse good instance!",parser.canParse(namedComplicatedCondition));
	}

}
