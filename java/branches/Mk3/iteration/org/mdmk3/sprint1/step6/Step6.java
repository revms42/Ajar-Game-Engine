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
 * Step6.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.disp2d.DisplayPanel;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step6 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color[] map =	{
		null,		Color.RED, 		null,			null,			null,			Color.YELLOW,
		null,		Color.RED,		null,			null,			null,			Color.YELLOW,
		null,		null,			null,			Color.GREEN,	null,			Color.YELLOW,
		null,		null,			null,			Color.GREEN,	null,			Color.YELLOW,
		Color.RED,	null,			null,			Color.GREEN,	Color.YELLOW,	Color.YELLOW,
		Color.RED,	Color.MAGENTA,	Color.MAGENTA,	Color.GREEN,	Color.GREEN,	Color.YELLOW
	};

	private final DisplayPanel displayPanel;
	
	private Step6(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step6: Mapped Level");
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
		Step6 step6 = (new Step6());
		DefaultNode<Step6Attributes> root = new DefaultNode<Step6Attributes>(null);
		
		Rectangle bounds = step6.displayPanel.getVisibleRect();
		Step6Attributes lvlAtts = new Step6Attributes(bounds,Step6ObjectType.LEVEL,null);
		DefaultNode<Step6Attributes> level = new DefaultNode<Step6Attributes>(lvlAtts);
		new Step6BouncingDecorator(level);
		root.addChild(level);
		
		Step6Attributes atts = new Step6Attributes(new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d), Step6ObjectType.BALL, Color.BLUE);
		DefaultNode<Step6Attributes> sprite = new DefaultNode<Step6Attributes>(atts);
		step6.addKeyListener(new Step6Entity(new Step6BouncingDecorator(new Step6DisplayDecorator(sprite))));
		root.addChild(sprite);
		
		Step6Converter converter = new Step6Converter();
		converter.setTileSize(new Dimension(100,100));
		
		Step6MapLoader loader = new Step6MapLoader();
		loader.setConverter(converter);
		
		/*
		Step6Attributes boxAtts = new Step6Attributes(new Rectangle2D.Double(0.0d,0.0d,60.0d,60.0d), Step6ObjectType.BOX);
		DefaultNode<Step6Attributes> box = new DefaultNode<Step6Attributes>(boxAtts);
		new Step6BouncingDecorator(new Step6DisplayDecorator(box));
		box.getAttributes().setPosition(400.0d, 300.0d);
		root.addChild(box);
		*/
		
		root.addChild(loader.loadFromArray(map, 6));
		
		Step6GameLoop loop = new Step6GameLoop(root,step6.displayPanel);
		loop.setDisplayableClass(Step6DisplayDecorator.class);
		loop.setCollidableClass(Step6BouncingDecorator.class);
		loop.setEntityClass(Step6Entity.class);
		loop.setCullingSurface(new Step6CullingSurface(step6.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step6.setVisible(true);
	}

}
