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
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import org.mdmk2.core.GameLoop;
import org.mdmk2.core.RootNode;
import org.mdmk2.core.disp2d.DisplayPanel;
import org.mdmk2.core.logic.StateWrapperNode;

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
		this.setTitle("Step2: Moving Display");
		this.displayPanel = new DisplayPanel();
		
		GameLoop.debug = true;
		
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
		RootNode<Rectangle> root = new RootNode<Rectangle>();
		Ellipse2D circle = new Ellipse2D.Double(0.0d,0.0d,50.0d,50.0d);
		Step3Mover sprite = new Step3Mover(new Area(circle),Color.RED,550,550);
		//sprite.getTransform().setToTranslation(0.0d, 50.0d);
		
		StateWrapperNode<Rectangle,Step3Mover> wrapper = new StateWrapperNode<Rectangle,Step3Mover>();
		Step3Matrix matrix = new Step3Matrix(wrapper);
		sprite.setState(matrix.defaultState());
		wrapper.addChild(sprite);
		wrapper.setEntity(sprite);
		wrapper.setMatrix(matrix);
		
		root.addChild(wrapper);
		
		Rectangle2D box = new Rectangle2D.Double(0.0d,0.0d,50.0d,50.0d);
		Step3Obstacle ob1 = new Step3Obstacle(new Area(box),Color.BLUE,550,550);
		ob1.setX(150.0d);ob1.setY(50.0d);root.addChild(ob1);
		Step3Obstacle ob2 = new Step3Obstacle(new Area(box),Color.BLUE,550,550);
		ob2.setX(350.0d);ob2.setY(150.0d);root.addChild(ob1);
		Step3Obstacle ob3 = new Step3Obstacle(new Area(box),Color.BLUE,550,550);
		ob3.setX(275.0d);ob3.setY(250.0d);root.addChild(ob1);
		
		Step3GameLoop loop = new Step3GameLoop(root,step3.displayPanel);
		loop.setUpdatePeriod(30);
		
		Thread t = new Thread(loop);
		
		t.start();
		step3.setVisible(true);
	}

}
