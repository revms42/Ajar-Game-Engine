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
 * ActionInstanceParserTest.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser.test;

import static org.junit.Assert.*;

import org.ajar.logic.loader.IParsedClass;
import org.ajar.logic.loader.LogicParserException;
import org.ajar.logic.loader.capsule.ActionObject;
import org.ajar.logic.loader.capsule.ParsedObject;
import org.ajar.logic.loader.parser.ActionClassParser;
import org.ajar.logic.loader.parser.ActionInstanceParser;
import org.ajar.logic.loader.parser.ActionMemberParser;
import org.ajar.logic.loader.parser.test.type.DummyAction;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class ActionInstanceParserTest {
	
	public final static String namedClass =
			"Action:DummyAction{org.ajar.logic.loader.parser.test.type.DummyAction}";
	public final static String namedNull = 
			"Action:DummyNamed{*DummyAction}";

	public final static String fullPathNull = 
			"Action:Dummy{*org.ajar.logic.loader.parser.test.type.DummyAction}";
	public final static String fullPathNull2 = 
			"Action:Dummy2{*org.ajar.logic.loader.parser.test.type.DummyAction()}";
	public final static String fullPathInt = 
			"Action:Dummy3{*org.ajar.logic.loader.parser.test.type.DummyAction(1)}";
	public final static String fullPathLong = 
			"Action:Dummy4{*org.ajar.logic.loader.parser.test.type.DummyAction(1l)}";
	public final static String fullPathFloat = 
			"Action:Dummy5{*org.ajar.logic.loader.parser.test.type.DummyAction(1.0f)}";
	public final static String fullPathDouble = 
			"Action:Dummy6{*org.ajar.logic.loader.parser.test.type.DummyAction(1.0d)}";
	public final static String fullPathChar = 
			"Action:Dummy7{*org.ajar.logic.loader.parser.test.type.DummyAction('!')}";
	public final static String fullPathBool = 
			"Action:Dummy8{*org.ajar.logic.loader.parser.test.type.DummyAction(true)}";
	public final static String fullPathString = 
			"Action:Dummy9{*org.ajar.logic.loader.parser.test.type.DummyAction(\"Dummy\")}";
	
	private ActionClassParser classParser;
	private ActionMemberParser memberParser;
	private ActionInstanceParser parser;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		classParser = new ActionClassParser();
		classParser.getParsedClass(namedClass);
		
		memberParser = new ActionMemberParser(classParser);
		
		parser = new ActionInstanceParser(memberParser);
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
			assertTrue("Full-path-null is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-null is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy"));
			DummyAction d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,-1);
			
			//FullPathNull2
			pc = parser.getParsedClass(fullPathNull2);
			assertNotNull("No output for full-path-null2!",pc);
			assertTrue("Full-path-null2 is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-null2 is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy2"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,-1);
			
			//FullPathInt
			pc = parser.getParsedClass(fullPathInt);
			assertNotNull("No output for full-path-int!",pc);
			assertTrue("Full-path-int is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-int is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy3"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,0);
			
			//FullPathLong
			pc = parser.getParsedClass(fullPathLong);
			assertNotNull("No output for full-path-long!",pc);
			assertTrue("Full-path-long is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-long is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy4"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,1);
			
			//FullPathFloat
			pc = parser.getParsedClass(fullPathFloat);
			assertNotNull("No output for full-path-float!",pc);
			assertTrue("Full-path-float is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-float is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy5"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			
			//FullPathDouble
			pc = parser.getParsedClass(fullPathDouble);
			assertNotNull("No output for full-path-double!",pc);
			assertTrue("Full-path-double is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-double is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy6"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,3);
			
			//FullPathChar
			pc = parser.getParsedClass(fullPathChar);
			assertNotNull("No output for full-path-char!",pc);
			assertTrue("Full-path-char is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-char is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy7"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,5);
			
			//FullPathBool
			pc = parser.getParsedClass(fullPathBool);
			assertNotNull("No output for full-path-bool!",pc);
			assertTrue("Full-path-bool is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-bool is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy8"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,6);
			
			//FullPathString
			pc = parser.getParsedClass(fullPathString);
			assertNotNull("No output for full-path-string!",pc);
			assertTrue("Full-path-string is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Full-path-string is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("Dummy9"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,7);
			
			//NamedNull
			pc = parser.getParsedClass(namedNull);
			assertNotNull("No output for named null!",pc);
			assertTrue("Named-null is not an Action object!", pc.getClass() == ActionObject.class);
			assertTrue("Named-null is not Dummy Action!", pc.objectClass() == DummyAction.class);
			assertEquals(pc,ParsedObject.getNamedObject("DummyNamed"));
			d = ((ActionObject<DummyAction>)pc).getParsedObject();
			assertNotNull("Parsed object is null!",d);
			checkAll(d,-1);
		}catch(LogicParserException e){
			fail(e.getMessage());
		}
	}
	
	private void checkAll(DummyAction d, int position){
		assertEquals(position == 0 ? 1 : -1,d.a);
		assertEquals(position == 1 ? 1 : -1,d.b);
		assertEquals(position == 2 ? 1 : -1,d.c,0);
		assertEquals(position == 3 ? 1 : -1,d.d,0);
		assertEquals(position == 4 ? 1 : -1,d.e);
		assertEquals(position == 5 ? '!' : '-',d.f);
		assertEquals(position == 6 ? true : false,d.g);
		assertEquals(position == 7 ? "Dummy" : null,d.h);
	}
	
	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathNull));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathNull2));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathInt));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathLong));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathFloat));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathDouble));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathChar));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathBool));
		assertTrue("Cannot parse good instance!",parser.canParse(fullPathString));
		assertTrue("Cannot parse good instance!",parser.canParse(namedNull));
	}

}
