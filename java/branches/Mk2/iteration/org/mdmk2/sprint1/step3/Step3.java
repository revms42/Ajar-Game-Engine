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
 * org.mdmk2.sprint1.step3
 * Step3.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import org.mdmk2.core.RootNode;
import org.mdmk2.core.disp2d.DisplayPanel;

/**
 * @author reverend
 * 15-May-10
 */
public class Step3 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DisplayPanel displayPanel;
	private final RootNode<Rectangle> displayRoot;
	
	private Step3(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step3: Moving Display with Obstacles");
		this.displayPanel = new DisplayPanel();
		
		displayPanel.setSize(600, 600);
		
		this.setLayout(new BorderLayout());
		this.add(displayPanel,BorderLayout.CENTER);
		this.pack();
		this.setSize(600, 600);
		
		displayRoot = new RootNode<Rectangle>();
		setup();
	}
	
	/**
	 * mstockbridge
	 * 15-May-10
	 */
	private void setup() {
		Ellipse2D circle = new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d);
		addSprite(circle, Color.RED, 10.0d, 10.0d);
		
		Rectangle2D rect = new Rectangle2D.Double(0.0d,0.0d,20.0d,20.0d);
		addSprite(rect, Color.BLUE, 500.0d, 400.0d);
		addSprite(rect, Color.BLUE, 200.0d, 350.0d);
		addSprite(rect, Color.BLUE, 550.0d, 100.0d);
		addSprite(rect, Color.BLUE, 280.0d, 150.0d);
	}
	
	/**
	 * mstockbridge
	 * 15-May-10
	 */
	private void addSprite(Shape s, Color c, double x, double y) {
		Step3Sprite sprite = new Step3Sprite(s,c);
		sprite.setTransform(AffineTransform.getTranslateInstance(x,y));
		displayRoot.addChild(sprite);
	}

	/**
	 * mstockbridge
	 * 15-May-10
	 * @param args
	 */
	public static void main(String[] args) {
		Step3 step3 = new Step3();
		Step3GameLoop loop = new Step3GameLoop(step3.displayRoot,step3.displayPanel);
		
		Thread t = new Thread(loop);
		
		t.start();
		step3.setVisible(true);
	}

}
