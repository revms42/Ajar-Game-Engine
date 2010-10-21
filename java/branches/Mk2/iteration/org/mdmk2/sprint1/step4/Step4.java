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
 * org.mdmk2.sprint1.step2
 * Step2.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import org.mdmk2.core.collision.Solid;
import org.mdmk2.core.disp2d.DisplayPanel;
import org.mdmk2.core.disp2d.Sprite;
import org.mdmk2.core.node.Node;

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
		this.setTitle("Step4: Multiple Objects");
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
		Step4 Step4 = (new Step4());
		Node<Step4Attributes> root = Step4SpriteFactory.singleton.createNode();
		Solid<Step4Attributes> sprite = Step4SpriteFactory.singleton.createSolid();
		sprite.getTransform().setToTranslation(0.0d, 0.0d);
		root.addChild(sprite);
		
		Step4.addKeyListener((Step4StatedImp)sprite.getStatedImp());
		
		Sprite<Step4Attributes> sprite2 = Step4SpriteFactory.createBox(new Rectangle2D.Double(0,0,100,100));
		sprite2.getTransform().setToTranslation(200.0d, 300.0d);
		root.addChild(sprite2);
		
		Dimension d = Step4.displayPanel.getSize();
		
		root.addChild(Step4SpriteFactory.createBoundry(new Rectangle2D.Double(0,0,1,d.height)));
		root.addChild(Step4SpriteFactory.createBoundry(new Rectangle2D.Double(0,0,d.width,1)));
		root.addChild(Step4SpriteFactory.createBoundry(new Rectangle2D.Double(0,d.height,d.width,1)));
		root.addChild(Step4SpriteFactory.createBoundry(new Rectangle2D.Double(d.width,0,1,d.height)));
		Step4GameLoop loop = new Step4GameLoop(root,Step4.displayPanel);
		loop.setCullingSurface(new Step4CullingSurface(Step4.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		Step4.setVisible(true);
	}

}
