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
 * ChainInstanceParserTest.java
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
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ChainObject;
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.parser.ChainClassParser;
import org.ajar.logic.loader.parser.ChainInstanceParser;
import org.ajar.logic.loader.parser.ChainMemberParser;
import org.ajar.logic.loader.parser.test.type.DummyChain;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class ChainInstanceParserTest {
	
	public final static String namedClass = 
			"Chain:DummyChain{org.ajar.logic.loader.parser.test.type.DummyChain}";
	public final static String namedNull = 
			"Chain:DummyNamed{*DummyChain}";
	public final static String namedNewChain = 
			"Chain:DummyNamedChain{*DummyChain&*DummyChain&*DummyChain}";
	
	public final static String fullPathNull = 
			"Chain:Dummy{*org.ajar.logic.loader.parser.test.type.DummyChain}";
	
	private ChainClassParser<HashAttributes> classParser;
	private ChainMemberParser<HashAttributes> memberParser;
	private ChainInstanceParser<HashAttributes> parser;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		classParser = new ChainClassParser<HashAttributes>();
		classParser.getParsedClass(namedClass);
		
		memberParser = new ChainMemberParser<HashAttributes>(classParser);
		
		parser = new ChainInstanceParser<HashAttributes>(memberParser);
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
			assertTrue("Full-path-null is not an Chain object!", pc.getClass() == ChainObject.class);
			assertTrue("Full-path-null is not Dummy Chain!", pc.objectClass() == DummyChain.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy"));
			DummyChain d = ((ChainObject<DummyChain>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null));
			
			//NamedNull
			pc = parser.getParsedClass(namedNull);
			assertNotNull("No output for named null!",pc);
			assertTrue("Named-null is not an Chain object!", pc.getClass() == ChainObject.class);
			assertTrue("Named-null is not Dummy Chain!", pc.objectClass() == DummyChain.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyNamed"));
			d = ((ChainObject<DummyChain>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null));
			
			//NamedNewChain
			pc = parser.getParsedClass(namedNewChain);
			assertNotNull("No output for named new chain!",pc);
			assertTrue("Named new chain is not an Chain object!", pc.getClass() == ChainObject.class);
			assertTrue("Named new chain is not Dummy Chain!", pc.objectClass() == DummyChain.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyNamedChain"));
			d = ((ChainObject<DummyChain>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			assertNull(d.perform(null));
			assertNotNull(d.getChild());
			assertEquals(DummyChain.class,d.getChild().getClass());
			assertNotNull(d.getChild().getChild());
			assertEquals(DummyChain.class,d.getChild().getChild().getClass());
			
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
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathNull));
		assertTrue("Cannot parse good instance!",parser.canParse(namedNull));
		assertTrue("Cannot parse good instance!",parser.canParse(namedNewChain));
	}
}
