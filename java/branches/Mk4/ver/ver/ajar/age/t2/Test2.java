/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 22, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t2
 * Test2.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t2;

import org.ajar.age.DefaultNode;
import org.ajar.age.Node;
import org.ajar.age.SimpleVisitor;
import org.ajar.age.Visitor;
import org.ajar.age.logic.HashAttributes;

import ver.ajar.age.t0.Test;
import ver.ajar.age.t2.display.VerDisplayVisitor;
import ver.ajar.age.t2.display.VerDisplayable;
import ver.ajar.age.t2.logic.VerEntity;
import ver.ajar.age.t2.logic.VerLogicVisitor;

/**
 * The purpose of this verification step is to test out animation.
 * @author reverend
 *
 */
public class Test2 extends Test<HashAttributes> {
	private static final long serialVersionUID = -3717777373947866111L;
	
	private Test2(){
		super();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//GameLoop.debug = true;
		start(new Test2());
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#setupRootNode()
	 */
	@Override
	protected Node<HashAttributes> setupRootNode() {
		DefaultNode<HashAttributes> root = new DefaultNode<HashAttributes>(new HashAttributes());
		
		root.getAttributes().setAttribute(VerAttribute.X_POS);
		root.getAttributes().setAttribute(VerAttribute.Y_POS);
		root.getAttributes().setAttribute(VerAttribute.WIDTH, 640);
		root.getAttributes().setAttribute(VerAttribute.HEIGHT, 480);
		
		return new VerDisplayable(root);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getCullingVisitor()
	 */
	@Override
	protected Visitor<HashAttributes> getCullingVisitor() {
		return new SimpleVisitor<HashAttributes, VerDisplayable>(VerDisplayable.class);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getDisplayVisitor()
	 */
	@Override
	protected Visitor<HashAttributes> getDisplayVisitor() {
		return new VerDisplayVisitor(panel);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getCollisionVisitor()
	 */
	@Override
	protected Visitor<HashAttributes> getCollisionVisitor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getLogicVisitor()
	 */
	@Override
	protected Visitor<HashAttributes> getLogicVisitor() {
		return new VerLogicVisitor();
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getSoundVisitor()
	 */
	@Override
	protected Visitor<HashAttributes> getSoundVisitor() {return null;}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#addNodes(org.ajar.age.Node)
	 */
	@Override
	protected void addNodes(Node<HashAttributes> root) {
		DefaultNode<HashAttributes> node = new DefaultNode<HashAttributes>(new HashAttributes());
		
		node.getAttributes().setSimpleAttributes(VerAttribute.values());
		node.getAttributes().setAttribute(VerAttribute.X_VEL, 1);
		node.getAttributes().setAttribute(VerAttribute.Y_VEL, 1);
		
		root.addChild(new VerEntity(new VerDisplayable(node)));
	}

}
