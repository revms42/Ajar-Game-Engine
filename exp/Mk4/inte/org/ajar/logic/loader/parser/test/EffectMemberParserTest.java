/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 5, 2014 Matthew Stockbridge
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
 * org.ajar.logic.loader.parser
 * EffectMemberParserTest.java
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
import org.ajar.logic.loader.LogicLoader;
import org.ajar.logic.loader.parser.EffectClassParser;
import org.ajar.logic.loader.parser.EffectMemberParser;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mstockbr
 *
 */
public class EffectMemberParserTest {
	public final static String namedClass = 
			"Effect:DummyEffect{org.ajar.logic.loader.parser.test.type.DummyEffect}";
	
	public final static String goodPath = "*DummyEffect";
	public final static String bad1Path = "*DummyEffect1&*DummyEffect2";
	public final static String bad2Path = "*DummyEffect?*DummyEffect|*DummyEffect";
	
	private EffectClassParser<HashAttributes> classParser;
	private EffectMemberParser<HashAttributes> memberParser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LogicLoader.clearCaches();
		classParser = new EffectClassParser<HashAttributes>();
		classParser.getParsedClass(namedClass);
		
		memberParser = new EffectMemberParser<HashAttributes>(classParser);
	}

	/**
	 * Test method for {@link org.ajar.logic.loader.parser.AbstractParser#canParse(java.lang.String)}.
	 */
	@Test
	public void testCanParse() {
		assertTrue("Cannot parse good instance!",memberParser.canParse(goodPath));
		assertFalse("Parsed bad instance!",memberParser.canParse(bad1Path));
		assertFalse("Parsed bad instance!",memberParser.canParse(bad2Path));
	}

}
