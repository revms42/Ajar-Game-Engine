/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 15-May-10 Matthew Stockbridge
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
 * org.mdmk3.sprint1.step1
 * Step1.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.disp2d.DisplayPanel;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DisplayPanel displayPanel;
	
	private Step1(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step1: Static Display");
		this.displayPanel = new DisplayPanel();
		
		displayPanel.setSize(600, 600);
		
		this.setLayout(new BorderLayout());
		this.add(displayPanel,BorderLayout.CENTER);
		this.pack();
		this.setSize(600, 600);
	}
	
	/**
	 * mstockbridge
	 * 15-May-10
	 * @param args
	 */
	public static void main(String[] args) {
		Step1 step1 = (new Step1());
		DefaultNode<Step1Attributes> root = new DefaultNode<Step1Attributes>(null);
		Step1Attributes atts = new Step1Attributes(new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d), Color.BLUE);
		DefaultNode<Step1Attributes> node = new DefaultNode<Step1Attributes>(atts);
		new Step1DisplayDecorator(node);
		root.addChild(node);
		Step1GameLoop loop = new Step1GameLoop(root,step1.displayPanel);
		loop.setDisplayableClass(Step1DisplayDecorator.class);
		loop.setCullingSurface(new Step1CullingSurface(step1.displayPanel));
		
		Thread t = new Thread(loop);
		
		t.start();
		step1.setVisible(true);
	}

}
