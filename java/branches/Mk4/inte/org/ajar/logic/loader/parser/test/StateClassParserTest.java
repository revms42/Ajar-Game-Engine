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

import org.ajar.logic.loader.parser.StateClassParser;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class StateClassParserTest {
	private final static String valid1 = "State:state1{com.state.State1}";
	
	private final static String invalid1 = "State:state1{Action1->Effect1=state1}";
	private StateClassParser parser;
	
	@Before
	public void setup() throws Exception {
		parser = new StateClassParser();
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractClassParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Valid input returned false!",parser.canParse(valid1));
		assertFalse("Invalid input returned true!", parser.canParse(invalid1));
	}

}
