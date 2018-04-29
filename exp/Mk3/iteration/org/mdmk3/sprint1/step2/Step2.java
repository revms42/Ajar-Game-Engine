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
 * org.mdmk3.sprint1.step2
 * Step5.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.disp2d.DisplayPanel;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DisplayPanel displayPanel;
	
	private Step2(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step5: Moving Display");
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
		Step2 step2 = (new Step2());
		DefaultNode<Step2Attributes> root = new DefaultNode<Step2Attributes>(null);
		
		Rectangle bounds = step2.displayPanel.getVisibleRect();
		Step2Attributes lvlAtts = new Step2Attributes(bounds,null);
		DefaultNode<Step2Attributes> level = new DefaultNode<Step2Attributes>(lvlAtts);
		new Step2BouncingDecorator(level);
		
		Step2Attributes atts = new Step2Attributes(new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d), Color.BLUE);
		DefaultNode<Step2Attributes> sprite = new DefaultNode<Step2Attributes>(atts);
		new Step2Entity(new Step2BouncingDecorator(new Step2DisplayDecorator(sprite)));
		root.addChild(sprite);
		root.addChild(level);
		
		Step2GameLoop loop = new Step2GameLoop(root,step2.displayPanel);
		loop.setDisplayableClass(Step2DisplayDecorator.class);
		loop.setCollidableClass(Step2BouncingDecorator.class);
		loop.setEntityClass(Step2Entity.class);
		loop.setCullingSurface(new Step2CullingSurface(step2.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step2.setVisible(true);
	}

}
