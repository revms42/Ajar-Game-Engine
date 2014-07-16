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
 * EffectClassParserTest.java
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
import org.ajar.logic.loader.capsule.ConditionClass;
import org.ajar.logic.loader.parser.ConditionClassParser;
import org.ajar.logic.loader.parser.test.type.DummyCondition;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class ConditionClassParserTest {

	public final static String goodClass = 
			"Condition:Dummy{org.ajar.logic.loader.parser.test.type.DummyCondition}";
	
	private ConditionClassParser<HashAttributes> parser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LogicLoader.clearCaches();
		parser = new ConditionClassParser<HashAttributes>();
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractClassParser#getParsedClass(java.lang.String)}.
	 */
	@Test
	public void testGetParsedClass() {
		try{
			IParsedClass<?> pc = parser.getParsedClass(goodClass);
			assertNotNull("No output for good class!",pc);
			assertTrue("Parsed Class is not an Effect class!", pc.getClass() == ConditionClass.class);
			assertTrue("Parsed Class is not Dummy Condition!", pc.objectClass() == DummyCondition.class);
		}catch(LogicParserException e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Cannot parse good class!",parser.canParse(goodClass));
	}

}
