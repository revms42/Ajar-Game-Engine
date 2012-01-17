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
 * Step9.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.disp2d.DisplayPanel;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step9 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String mapPath = "iteration/org/mdmk3/sprint1/step9/res/level.png";
	private static BufferedImage map;

	private final DisplayPanel displayPanel;
	
	private Step9(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step9: Enemy Blockss");
		this.displayPanel = new DisplayPanel();
		
		displayPanel.setSize(640, 640);
		
		this.setLayout(new BorderLayout());
		this.add(displayPanel,BorderLayout.CENTER);
		this.pack();
		this.setSize(650, 680);
	}
	
	/**
	 * mstockbridge
	 * 15-May-10
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Step9 step9 = (new Step9());
		
		DefaultNode<Step9Attributes> root = new DefaultNode<Step9Attributes>(null);
		
		Rectangle bounds = step9.displayPanel.getVisibleRect();
		Step9Attributes lvlAtts = new Step9Attributes(bounds,Step9ObjectType.LEVEL);
		DefaultNode<Step9Attributes> level = new DefaultNode<Step9Attributes>(lvlAtts);
		new Step9BouncingDecorator(level);
		root.addChild(level);
		
		Step9TilePalette palette = new Step9TilePalette();
		
		Step9Attributes atts = new Step9Attributes(new Rectangle2D.Double(20.0d,20.0d,20.0d,20.0d), Step9ObjectType.BALL);
		atts.setCurrentFrame(10);
		atts.setPosition(129, 129);
		DefaultNode<Step9Attributes> sprite = new DefaultNode<Step9Attributes>(atts);
		step9.addKeyListener(new Step9PlayerEntity(new Step9BouncingDecorator(new Step9DisplayDecorator(sprite))));
		sprite.getDecorator(Step9DisplayDecorator.class).setProvider(palette);
		root.addChild(sprite);
		
		Step9Attributes atts2 = new Step9Attributes(new Rectangle2D.Double(20.0d,20.0d,20.0d,20.0d), Step9ObjectType.ENEMY);
		atts2.setCurrentFrame(10);
		atts2.setPosition(256, 256);
		DefaultNode<Step9Attributes> enemy = new DefaultNode<Step9Attributes>(atts2);
		new Step9EnemyEntity(new Step9BouncingDecorator(new Step9DisplayDecorator(enemy)));
		enemy.getDecorator(Step9DisplayDecorator.class).setProvider(palette);
		root.addChild(enemy);
		
		try {
			File path = new File(mapPath);
			map = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Step9Converter converter = new Step9Converter(palette,new Dimension(64,64));
		
		Step9MapLoader loader = new Step9MapLoader();
		loader.setConverter(converter);
		
		root.addChild(loader.loadFromImage(map));
		
		Step9GameLoop loop = new Step9GameLoop(root,step9.displayPanel);
		loop.setDisplayableClass(Step9DisplayDecorator.class);
		loop.setCollidableClass(Step9BouncingDecorator.class);
		loop.setEntityClass(Step9Entity.class);
		loop.setCullingSurface(new Step9CullingSurface(step9.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step9.setVisible(true);
	}

}
