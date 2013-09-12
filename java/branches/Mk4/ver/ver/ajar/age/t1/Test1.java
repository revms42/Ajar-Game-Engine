/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 5, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t1
 * Test1.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t1;

import org.ajar.age.DefaultNode;
import org.ajar.age.Node;
import org.ajar.age.SimpleVisitor;
import org.ajar.age.Visitor;

import ver.ajar.age.t0.Test;

/**
 * @author reverend
 *
 */
public class Test1 extends Test<VerAttributes> {
	private static final long serialVersionUID = -3717777373947866111L;
	
	private Test1(){
		super();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		start(new Test1());
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#setupRootNode()
	 */
	@Override
	protected Node<VerAttributes> setupRootNode() {
		DefaultNode<VerAttributes> root = new DefaultNode<VerAttributes>(new VerAttributes());
		root.getAttributes().xPos(200);
		root.getAttributes().yPos(200);
		new VerDisplayable(root);
		return root;
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getCullingVisitor(org.ajar.age.Node)
	 */
	@Override
	protected Visitor<VerAttributes> getCullingVisitor() {
		return new SimpleVisitor<VerAttributes, VerDisplayable>(VerDisplayable.class);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getDisplayVisitor(org.ajar.age.Node)
	 */
	@Override
	protected Visitor<VerAttributes> getDisplayVisitor() {
		return new VerDisplayVisitor(panel);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getCollisionVisitor(org.ajar.age.Node)
	 */
	@Override
	protected Visitor<VerAttributes> getCollisionVisitor() {return null;}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getLogicVisitor(org.ajar.age.Node)
	 */
	@Override
	protected Visitor<VerAttributes> getLogicVisitor() {return null;}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getSoundVisitor(org.ajar.age.Node)
	 */
	@Override
	protected Visitor<VerAttributes> getSoundVisitor() {return null;}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#addNodes(org.ajar.age.Node)
	 */
	@Override
	protected void addNodes(Node<VerAttributes> root) {}

}
