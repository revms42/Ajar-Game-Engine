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

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.ajar.age.DefaultNode;
import org.ajar.age.GameLoop;
import org.ajar.age.SimpleVisitor;
import org.ajar.age.disp.awt.AWTGraphicsPanel;

/**
 * @author reverend
 *
 */
public class Test1 extends JFrame {
	private static final long serialVersionUID = -3717777373947866111L;

	private GameLoop<VerAttributes> gameloop;
	private AWTGraphicsPanel panel;
	
	private Test1(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new AWTGraphicsPanel();
		
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.CENTER);
		panel.setSize(640,480);
		
		this.setTitle("Test1");
		
		setupGameLoop();
		this.pack();
	}
	
	private void setupGameLoop(){
		DefaultNode<VerAttributes> root = new DefaultNode<VerAttributes>(new VerAttributes());
		root.getAttributes().xPos(200);
		root.getAttributes().yPos(200);
		new VerDisplayable(root);
		gameloop = new GameLoop<VerAttributes>(root);
		
		gameloop.setCullingVisitor(new SimpleVisitor<VerAttributes, VerDisplayable>(VerDisplayable.class));
		gameloop.setDisplayVisitor(new VerDisplayVisitor(panel));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test1 test = new Test1();
		test.setSize(640,480);
		test.setVisible(true);
		
		Thread t = new Thread(test.gameloop);
		t.start();
	}

}
