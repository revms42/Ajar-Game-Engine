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
 * org.ajar.logic.loader.parser.test
 * StateClassParserTest.java
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
import org.ajar.logic.loader.capsule.StateClass;
import org.ajar.logic.loader.parser.StateClassParser;
import org.ajar.logic.loader.parser.test.type.DummyState;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class StateClassParserTest {
	private final static String goodClass = 
			"State:Dummy{org.ajar.logic.loader.parser.test.type.DummyState}";
	
	private final static String badClass = 
			"State:Dummy{Action->Effect1=org.ajar.logic.loader.parser.test.type.DummyState}";
	private StateClassParser<HashAttributes> parser;
	
	@Before
	public void setup() throws Exception {
		LogicLoader.clearCaches();
		parser = new StateClassParser<HashAttributes>();
	}
	
	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractClassParser#getParsedClass(java.lang.String)}.
	 */
	@Test
	public void testGetParsedClass() {
		try{
			IParsedClass<?> pc = parser.getParsedClass(goodClass);
			assertNotNull("No output for good class!",pc);
			assertTrue("Parsed Class is not an State class!", pc.getClass() == StateClass.class);
			assertTrue("Parsed Class is not Dummy State!", pc.objectClass() == DummyState.class);
		}catch(LogicParserException e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractClassParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Valid input returned false!",parser.canParse(goodClass));
		assertFalse("Invalid input returned true!", parser.canParse(badClass));
	}

}
