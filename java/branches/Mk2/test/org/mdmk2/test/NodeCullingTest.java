/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Jun 4, 2010 Matthew Stockbridge
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
 * MDMk2
 * org.mdmk2.test
 * NodeCullingTest.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.test;


import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdmk2.core.GameLoop;
import org.mdmk2.core.node.Node;

/**
 * @author mstockbridge
 * Jun 4, 2010
 */
public abstract class NodeCullingTest<E extends Node<R>,R> extends GameLoop<R>{
	/**
	 * @param displayRoot
	 */
	public NodeCullingTest(Node<R> displayRoot) {
		super(displayRoot);
	}

	public R range;
	
	public Node<R> firstWhollyIn;
	public Node<R> firstPartiallyIn;
	public Node<R> firstOutside;
	
	public Node<R> secondWhollyIn;
	public Node<R> secondPartiallyIn;
	public Node<R> secondOutside;
	
	public Node<R> thirdWhollyIn;
	public Node<R> thirdPartiallyIn;
	public Node<R> thirdOutside;
	
	public boolean logicCalled;
	public boolean renderCalled;
	
	protected abstract E createFirstWhollyIn();
	protected abstract E createFirstPartiallyIn();
	protected abstract E createFirstOutside();
	
	protected abstract E createSecondWhollyIn();
	protected abstract E createSecondPartiallyIn();
	protected abstract E createSecondOutside();
	
	protected abstract E createThirdWhollyIn();
	protected abstract E createThirdPartiallyIn();
	protected abstract E createThirdOutside();
	
	/**
	 * mstockbridge
	 * Jun 4, 2010
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		firstWhollyIn = createFirstWhollyIn();
		firstPartiallyIn = createFirstPartiallyIn();
		firstOutside = createFirstOutside();
		
		secondWhollyIn = createSecondWhollyIn();
		secondPartiallyIn = createSecondPartiallyIn();
		secondOutside = createSecondOutside();
		
		thirdWhollyIn = createThirdWhollyIn();
		thirdPartiallyIn = createThirdPartiallyIn();
		thirdOutside = createThirdOutside();
		
		this.displayRoot.addChild(firstWhollyIn);
		this.displayRoot.addChild(firstPartiallyIn);
		this.displayRoot.addChild(firstOutside);
		
		firstPartiallyIn.addChild(secondWhollyIn);
		firstPartiallyIn.addChild(secondPartiallyIn);
		firstPartiallyIn.addChild(secondOutside);
		
		secondPartiallyIn.addChild(thirdWhollyIn);
		secondPartiallyIn.addChild(thirdPartiallyIn);
		secondPartiallyIn.addChild(thirdOutside);
	}
	
	/**
	 * 
	 * mstockbridge
	 * Jun 4, 2010
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void firstOrderLogicTest() {
		callUpdate();
		assertTrue(
			"Status update not scheduled for node contained fully in range in root node!", 
			this.needsStatusUpdate.contains(firstWhollyIn)
		);
		assertTrue(
			"Status update not scheduled for node contained parially in range in root node!", 
			this.needsStatusUpdate.contains(firstPartiallyIn)
		);
		assertTrue(
			"Status update scheduled for node not contained in range in root node!", 
			this.needsStatusUpdate.contains(firstOutside)
		);
		assertTrue("Logic method not called during update!",logicCalled);
	}
	
	@Test
	public void firstOrderRenderTest() {
		callUpdate();
		assertTrue(
			"Status update not scheduled for node contained fully in range in root node!", 
			this.needsDisplayUpdate.contains(firstWhollyIn)
		);
		assertTrue(
			"Status update not scheduled for node contained parially in range in root node!", 
			this.needsDisplayUpdate.contains(firstPartiallyIn)
		);
		assertTrue(
			"Status update scheduled for node not contained in range in root node!", 
			this.needsDisplayUpdate.contains(firstOutside)
		);
		assertTrue("Render method not called during update!",renderCalled);
	}
	
	@Test
	public void secondOrderLogicTest() {
		callUpdate();
		assertTrue(
			"Status update not scheduled for node contained fully in range in order 1 node!", 
			this.needsStatusUpdate.contains(secondWhollyIn)
		);
		assertTrue(
			"Status update not scheduled for node contained parially in range in order 1 node!", 
			this.needsStatusUpdate.contains(secondPartiallyIn)
		);
		assertTrue(
			"Status update scheduled for node not contained in range in order 1 node!", 
			this.needsStatusUpdate.contains(secondOutside)
		);
		assertTrue("Logic method not called during update!",logicCalled);
	}
	
	@Test
	public void secondOrderRenderTest() {
		callUpdate();
		assertTrue(
			"Status update not scheduled for node contained fully in range in order 1 node!", 
			this.needsDisplayUpdate.contains(secondWhollyIn)
		);
		assertTrue(
			"Status update not scheduled for node contained parially in range in order 1 node!", 
			this.needsDisplayUpdate.contains(secondPartiallyIn)
		);
		assertTrue(
			"Status update scheduled for node not contained in range in order 1 node!", 
			this.needsDisplayUpdate.contains(secondOutside)
		);
		assertTrue("Render method not called during update!",renderCalled);
	}
	
	@Test
	public void thirdOrderLogicTest() {
		callUpdate();
		assertTrue(
			"Status update not scheduled for node contained fully in range in order 2 node!", 
			this.needsStatusUpdate.contains(thirdWhollyIn)
		);
		assertTrue(
			"Status update not scheduled for node contained parially in range in order 2 node!", 
			this.needsStatusUpdate.contains(thirdPartiallyIn)
		);
		assertTrue(
			"Status update scheduled for node not contained in range in order 2 node!", 
			this.needsStatusUpdate.contains(thirdOutside)
		);
		assertTrue("Logic method not called during update!",logicCalled);
	}
	
	@Test
	public void thirdOrderRenderTest() {
		callUpdate();
		assertTrue(
			"Status update not scheduled for node contained fully in range in order 2 node!", 
			this.needsDisplayUpdate.contains(thirdWhollyIn)
		);
		assertTrue(
			"Status update not scheduled for node contained parially in range in order 2 node!", 
			this.needsDisplayUpdate.contains(thirdPartiallyIn)
		);
		assertTrue(
			"Status update scheduled for node not contained in range in order 2 node!", 
			this.needsDisplayUpdate.contains(thirdOutside)
		);
		assertTrue("Render method not called during update!",renderCalled);
	}

	public void callUpdate(){
		this.update(this.displayRoot, range);
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#getRange()
	 */
	@Override
	public R getRange() {
		return range;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#logic()
	 */
	@Override
	public void logic() {
		logicCalled = true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.GameLoop#render()
	 */
	@Override
	public void render() {
		renderCalled = true;
	}

}
