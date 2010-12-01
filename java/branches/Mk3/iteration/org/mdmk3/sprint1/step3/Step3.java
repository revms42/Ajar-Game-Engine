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
package org.mdmk3.sprint1.step3;

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
public class Step3 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DisplayPanel displayPanel;
	
	private Step3(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step5: Boundry Detection");
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
		Step3 step3 = (new Step3());
		DefaultNode<Step3Attributes> root = new DefaultNode<Step3Attributes>(null);
		
		Rectangle bounds = step3.displayPanel.getVisibleRect();
		Step3Attributes lvlAtts = new Step3Attributes(bounds,Step3ObjectType.LEVEL);
		DefaultNode<Step3Attributes> level = new DefaultNode<Step3Attributes>(lvlAtts);
		new Step3BouncingDecorator(level);
		root.addChild(level);
		
		Step3Attributes atts = new Step3Attributes(new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d), Step3ObjectType.BALL);
		DefaultNode<Step3Attributes> sprite = new DefaultNode<Step3Attributes>(atts);
		new Step3Entity(new Step3BouncingDecorator(new Step3DisplayDecorator(sprite)));
		root.addChild(sprite);
		
		Step3Attributes boxAtts = new Step3Attributes(new Rectangle2D.Double(0.0d,0.0d,60.0d,60.0d), Step3ObjectType.BOX);
		DefaultNode<Step3Attributes> box = new DefaultNode<Step3Attributes>(boxAtts);
		new Step3BouncingDecorator(new Step3DisplayDecorator(box));
		box.getAttributes().setPosition(400.0d, 300.0d);
		root.addChild(box);
		
		Step3GameLoop loop = new Step3GameLoop(root,step3.displayPanel);
		loop.setDisplayableClass(Step3DisplayDecorator.class);
		loop.setCollidableClass(Step3BouncingDecorator.class);
		loop.setEntityClass(Step3Entity.class);
		loop.setCullingSurface(new Step3CullingSurface(step3.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step3.setVisible(true);
	}

}
