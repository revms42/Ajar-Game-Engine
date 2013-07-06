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
 * Step7.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step7;

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
public class Step7 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String mapPath = "iteration/org/mdmk3/sprint1/step7/res/level.png";
	private static BufferedImage map;

	private final DisplayPanel displayPanel;
	
	private Step7(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step7: Mapped Level");
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
		Step7 step7 = (new Step7());
		
		DefaultNode<Step7Attributes> root = new DefaultNode<Step7Attributes>(null);
		
		Rectangle bounds = step7.displayPanel.getVisibleRect();
		Step7Attributes lvlAtts = new Step7Attributes(bounds,Step7ObjectType.LEVEL);
		DefaultNode<Step7Attributes> level = new DefaultNode<Step7Attributes>(lvlAtts);
		new Step7BouncingDecorator(level);
		root.addChild(level);
		
		Step7TilePalette palette = new Step7TilePalette();
		
		Step7Attributes atts = new Step7Attributes(new Rectangle2D.Double(0.0d,0.0d,64.0d,64.0d), Step7ObjectType.BALL);
		atts.setCurrentFrame(10);
		atts.setPosition(129, 129);
		DefaultNode<Step7Attributes> sprite = new DefaultNode<Step7Attributes>(atts);
		step7.addKeyListener(new Step7Entity(new Step7BouncingDecorator(new Step7DisplayDecorator(sprite))));
		sprite.getDecorator(Step7DisplayDecorator.class).setProvider(palette);
		root.addChild(sprite);
		
		try {
			File path = new File(mapPath);
			map = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Step7Converter converter = new Step7Converter(palette,new Dimension(64,64));
		
		Step7MapLoader loader = new Step7MapLoader();
		loader.setConverter(converter);
		
		root.addChild(loader.loadFromImage(map));
		
		Step7GameLoop loop = new Step7GameLoop(root,step7.displayPanel);
		loop.setDisplayableClass(Step7DisplayDecorator.class);
		loop.setCollidableClass(Step7BouncingDecorator.class);
		loop.setEntityClass(Step7Entity.class);
		loop.setCullingSurface(new Step7CullingSurface(step7.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step7.setVisible(true);
	}

}
