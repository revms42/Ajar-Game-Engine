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
 * org.ajar.logic.loader.parser.test
 * EffectInstanceParserTest.java
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
import org.ajar.logic.loader.capsule.EffectObject;
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.parser.EffectClassParser;
import org.ajar.logic.loader.parser.EffectInstanceParser;
import org.ajar.logic.loader.parser.EffectMemberParser;
import org.ajar.logic.loader.parser.test.type.DummyEffect;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class EffectInstanceParserTest {
	
	public final static String namedClass = 
			"Effect:DummyEffect{org.ajar.logic.loader.parser.test.type.DummyEffect}";
	public final static String namedNull = 
			"Effect:DummyNamed{*DummyEffect}";
	
	public final static String fullPathNull = 
			"Effect:Dummy{*org.ajar.logic.loader.parser.test.type.DummyEffect}";
	
	private EffectClassParser<HashAttributes> classParser;
	private EffectMemberParser<HashAttributes> memberParser;
	private EffectInstanceParser<HashAttributes> parser;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LogicLoader.clearCaches();
		classParser = new EffectClassParser<HashAttributes>();
		classParser.getParsedClass(namedClass);
		
		memberParser = new EffectMemberParser<HashAttributes>(classParser);
		
		parser = new EffectInstanceParser<HashAttributes>(memberParser);
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractInstanceParser#getParsedClass(java.lang.String)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetParsedClass() {
		try{
			//FullPathNull
			IParsedClass<?> pc = parser.getParsedClass(fullPathNull);
			assertNotNull("No output for full-path-null!",pc);
			assertTrue("Full-path-null is not an Effect object!", pc.getClass() == EffectObject.class);
			assertTrue("Full-path-null is not Dummy Effect!", pc.objectClass() == DummyEffect.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy"));
			DummyEffect d = ((EffectObject<DummyEffect>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null,null));
			
			//NamedNull
			pc = parser.getParsedClass(namedNull);
			assertNotNull("No output for named-null!",pc);
			assertTrue("Named-null is not an Effect object!", pc.getClass() == EffectObject.class);
			assertTrue("Named-null is not Dummy Effect!", pc.objectClass() == DummyEffect.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyNamed"));
			d = ((EffectObject<DummyEffect>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null,null));
		}catch(LogicParserException e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathNull));
		assertTrue("Cannot parse good instance!",parser.canParse(namedNull));
	}

}
