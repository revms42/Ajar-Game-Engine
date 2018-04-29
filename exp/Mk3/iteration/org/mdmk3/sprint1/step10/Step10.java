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
 * Step10.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.sprint1.step10;

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
import org.mdmk3.core.logic.CompoundEffect;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step10 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String mapPath = "iteration/org/mdmk3/sprint1/step10/res/level.png";
	private static BufferedImage map;

	private final DisplayPanel displayPanel;
	
	private Step10(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step10: Bullets!");
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
		Step10 step10 = (new Step10());
		
		DefaultNode<Step10Attributes> root = new DefaultNode<Step10Attributes>(null);
		
		Rectangle bounds = step10.displayPanel.getVisibleRect();
		Step10Attributes lvlAtts = new Step10Attributes(bounds,Step10ObjectType.LEVEL);
		DefaultNode<Step10Attributes> level = new DefaultNode<Step10Attributes>(lvlAtts);
		new Step10BouncingDecorator(level);
		root.addChild(level);
		
		Step10TilePalette palette = new Step10TilePalette();
		
		Step10Attributes atts = new Step10Attributes(new Rectangle2D.Double(20.0d,20.0d,20.0d,20.0d), Step10ObjectType.BALL);
		atts.setCurrentFrame(10);
		atts.setPosition(129, 129);
		DefaultNode<Step10Attributes> sprite = new DefaultNode<Step10Attributes>(atts);
		Step10Entity player = new Step10Entity(new Step10BouncingDecorator(new Step10DisplayDecorator(sprite)),createPlayerState());
		Step10PlayerController controller = new Step10PlayerController();
		player.addController(controller);
		step10.addKeyListener(controller);
		sprite.getDecorator(Step10DisplayDecorator.class).setProvider(palette);
		root.addChild(sprite);
		
		Step10Attributes atts2 = new Step10Attributes(new Rectangle2D.Double(20.0d,20.0d,20.0d,20.0d), Step10ObjectType.ENEMY);
		atts2.setCurrentFrame(10);
		atts2.setPosition(256, 256);
		DefaultNode<Step10Attributes> enemy = new DefaultNode<Step10Attributes>(atts2);
		new Step10Entity(new Step10BouncingDecorator(new Step10DisplayDecorator(enemy)), new Step10EnemyState());
		enemy.getDecorator(Step10DisplayDecorator.class).setProvider(palette);
		root.addChild(enemy);
		
		try {
			File path = new File(mapPath);
			map = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Step10Converter converter = new Step10Converter(palette,new Dimension(64,64));
		
		Step10MapLoader loader = new Step10MapLoader();
		loader.setConverter(converter);
		
		root.addChild(loader.loadFromImage(map));
		
		Step10GameLoop loop = new Step10GameLoop(root,step10.displayPanel);
		loop.setDisplayableClass(Step10DisplayDecorator.class);
		loop.setCollidableClass(Step10BouncingDecorator.class);
		loop.setEntityClass(Step10Entity.class);
		loop.setCullingSurface(new Step10CullingSurface(step10.displayPanel));
		loop.setUpdatePeriod(10);
		
		Thread t = new Thread(loop);
		
		t.start();
		step10.setVisible(true);
	}

	
	@SuppressWarnings("unchecked")
	private static Step10GameState createPlayerState() throws IOException{
		Step10DBounceState d = new Step10DBounceState();
		Step10HBounceState h = new Step10HBounceState(d);
		Step10VBounceState v = new Step10VBounceState(d);
		Step10GameState state = new Step10GameState(h,v,d);
		
		d.put(new Step10MoveEffect(Step10ActionType.MOVE,state));
		d.put(new CompoundEffect<Step10Attributes>(
				null,
				state,
				new Step10MoveEffect(null,state),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,state)
		));
		
		h.put(new Step10MoveEffect(Step10ActionType.MOVE,state));
		h.put(new CompoundEffect<Step10Attributes>(
				null,
				state,
				new Step10MoveEffect(null,state),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,state)
		));
		
		v.put(new Step10MoveEffect(Step10ActionType.MOVE,state));
		v.put(new CompoundEffect<Step10Attributes>(
				null,
				state,
				new Step10MoveEffect(null,state),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,state)
		));
		
		return state;
	}
}
