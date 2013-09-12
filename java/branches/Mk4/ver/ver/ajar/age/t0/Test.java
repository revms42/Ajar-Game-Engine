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
 * ver.ajar.age.t0
 * Test.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t0;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.ajar.age.Attributes;
import org.ajar.age.GameLoop;
import org.ajar.age.Node;
import org.ajar.age.Visitor;
import org.ajar.age.disp.awt.AWTGraphicsPanel;

/**
 * @author reverend
 *
 */
public abstract class Test<A extends Attributes> extends JFrame {
	private static final long serialVersionUID = -7211301350878325892L;
	
	protected GameLoop<A> gameloop;
	protected AWTGraphicsPanel panel;
	
	protected Test(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new AWTGraphicsPanel();
		
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.CENTER);
		panel.setSize(640,480);
		
		this.setTitle("Test");
		
		setupGameLoop();
		this.pack();
	}
	
	protected abstract Node<A> setupRootNode();
	
	protected abstract Visitor<A> getCullingVisitor();
	protected abstract Visitor<A> getDisplayVisitor();
	protected abstract Visitor<A> getCollisionVisitor();
	protected abstract Visitor<A> getLogicVisitor();
	protected abstract Visitor<A> getSoundVisitor();
	
	protected abstract void addNodes(Node<A> root);
	
	protected void setupGameLoop(){
		Node<A> n = setupRootNode();
		gameloop = new GameLoop<A>(n);
		
		addNodes(n);
		
		gameloop.setCullingVisitor(getCullingVisitor());
		gameloop.setDisplayVisitor(getDisplayVisitor());
		gameloop.setCollisionVisitor(getCollisionVisitor());
		gameloop.setLogicVisitor(getLogicVisitor());
		gameloop.setSoundVisitor(getSoundVisitor());
	}
	
	public static <T extends Test<?>> void start(T test){
		test.setSize(640,480);
		test.setVisible(true);
		
		Thread t = new Thread(test.gameloop);
		t.start();
	}

}
