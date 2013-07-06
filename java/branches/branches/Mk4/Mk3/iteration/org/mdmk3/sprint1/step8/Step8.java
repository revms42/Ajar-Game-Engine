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
 * Step8.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step8;

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
public class Step8 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String mapPath = "iteration/org/mdmk3/sprint1/step8/res/level.png";
	private static BufferedImage map;

	private final DisplayPanel displayPanel;
	
	private Step8(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step8: Changing Sprite State");
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
		Step8 step8 = (new Step8());
		
		DefaultNode<Step8Attributes> root = new DefaultNode<Step8Attributes>(null);
		
		Rectangle bounds = step8.displayPanel.getVisibleRect();
		Step8Attributes lvlAtts = new Step8Attributes(bounds,Step8ObjectType.LEVEL);
		DefaultNode<Step8Attributes> level = new DefaultNode<Step8Attributes>(lvlAtts);
		new Step8BouncingDecorator(level);
		root.addChild(level);
		
		Step8TilePalette palette = new Step8TilePalette();
		
		Step8Attributes atts = new Step8Attributes(new Rectangle2D.Double(20.0d,20.0d,20.0d,20.0d), Step8ObjectType.BALL);
		atts.setCurrentFrame(10);
		atts.setPosition(129, 129);
		DefaultNode<Step8Attributes> sprite = new DefaultNode<Step8Attributes>(atts);
		step8.addKeyListener(new Step8Entity(new Step8BouncingDecorator(new Step8DisplayDecorator(sprite))));
		sprite.getDecorator(Step8DisplayDecorator.class).setProvider(palette);
		root.addChild(sprite);
		
		try {
			File path = new File(mapPath);
			map = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Step8Converter converter = new Step8Converter(palette,new Dimension(64,64));
		
		Step8MapLoader loader = new Step8MapLoader();
		loader.setConverter(converter);
		
		root.addChild(loader.loadFromImage(map));
		
		Step8GameLoop loop = new Step8GameLoop(root,step8.displayPanel);
		loop.setDisplayableClass(Step8DisplayDecorator.class);
		loop.setCollidableClass(Step8BouncingDecorator.class);
		loop.setEntityClass(Step8Entity.class);
		loop.setCullingSurface(new Step8CullingSurface(step8.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step8.setVisible(true);
	}

}
