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
package org.mdmk3.sprint1.step4;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.disp2d.DisplayPanel;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step4 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DisplayPanel displayPanel;
	
	private Step4(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step5: User Control");
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
		Step4 step4 = (new Step4());
		DefaultNode<Step4Attributes> root = new DefaultNode<Step4Attributes>(null);
		
		Rectangle bounds = step4.displayPanel.getVisibleRect();
		Step4Attributes lvlAtts = new Step4Attributes(bounds,Step4ObjectType.LEVEL);
		DefaultNode<Step4Attributes> level = new DefaultNode<Step4Attributes>(lvlAtts);
		new Step4BouncingDecorator(level);
		root.addChild(level);
		
		Step4Attributes atts = new Step4Attributes(new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d), Step4ObjectType.BALL);
		DefaultNode<Step4Attributes> sprite = new DefaultNode<Step4Attributes>(atts);
		step4.addKeyListener(new Step4Entity(new Step4BouncingDecorator(new Step4DisplayDecorator(sprite))));
		root.addChild(sprite);
		
		Step4Attributes boxAtts = new Step4Attributes(new Rectangle2D.Double(0.0d,0.0d,60.0d,60.0d), Step4ObjectType.BOX);
		DefaultNode<Step4Attributes> box = new DefaultNode<Step4Attributes>(boxAtts);
		new Step4BouncingDecorator(new Step4DisplayDecorator(box));
		box.getAttributes().setPosition(400.0d, 300.0d);
		root.addChild(box);
		
		Step4GameLoop loop = new Step4GameLoop(root,step4.displayPanel);
		loop.setDisplayableClass(Step4DisplayDecorator.class);
		loop.setCollidableClass(Step4BouncingDecorator.class);
		loop.setEntityClass(Step4Entity.class);
		loop.setCullingSurface(new Step4CullingSurface(step4.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step4.setVisible(true);
	}

}
