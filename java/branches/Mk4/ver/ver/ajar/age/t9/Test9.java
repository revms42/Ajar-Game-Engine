/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jul 22, 2013 Matthew Stockbridge
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
 * AGE
 * ver.ajar.age.t2
 * Test2.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t9;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.ajar.age.DefaultNode;
import org.ajar.age.GameLoop;
import org.ajar.age.Node;
import org.ajar.age.SimpleVisitor;
import org.ajar.age.Visitor;
import org.ajar.age.collision.CollisionVisitor;

import ver.ajar.age.t0.Test;
import ver.ajar.age.t9.VerAttribute;
import ver.ajar.age.t9.display.VerDisplayVisitor;
import ver.ajar.age.t9.display.VerDisplayable;
import ver.ajar.age.t9.collision.CollisionAttribute;
import ver.ajar.age.t9.collision.VerCollidable;
import ver.ajar.age.t9.loader.VerTileMapLoader;
import ver.ajar.age.t9.logic.VerController;
import ver.ajar.age.t9.logic.VerEntity;
import ver.ajar.age.t9.logic.VerLogicVisitor;

/**
 * The purpose of this verification step is to allow for two units to take turns moving.
 * @author reverend
 *
 */
public class Test9 extends Test<VerAttributes> {
	private static final long serialVersionUID = -3717777373947866111L;
	
	private Test9(){
		super();
		this.gameloop.setUpdatePeriod(50);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//GameLoop.debug = true;
		start(new Test9());
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#setupRootNode()
	 */
	@Override
	protected Node<VerAttributes> setupRootNode() {
		DefaultNode<VerAttributes> root = new DefaultNode<VerAttributes>(new VerAttributes());
		
		//640x480 moved in 8x8 and scaled back to 8x8 from the other edge.
		Rectangle box = new Rectangle(8,8,640-16,480-16);
		root.getAttributes().setAttribute(CollisionAttribute.BOUNDING_BOX,box);
		root.getAttributes().setAttribute(VerAttribute.TEAM);
		root.getAttributes().setAttribute(VerMapAttribute.CURRENT_PLAYER, 1);
		
		return new VerRefDecorator(new VerDisplayable(root));
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getCullingVisitor()
	 */
	@Override
	protected Visitor<VerAttributes> getCullingVisitor() {
		return new SimpleVisitor<VerAttributes, VerDisplayable>(VerDisplayable.class);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getDisplayVisitor()
	 */
	@Override
	protected Visitor<VerAttributes> getDisplayVisitor() {
		return new VerDisplayVisitor(panel);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getCollisionVisitor()
	 */
	@Override
	protected Visitor<VerAttributes> getCollisionVisitor() {
		return new CollisionVisitor<VerAttributes>(VerCollidable.class,VerEntity.class);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getLogicVisitor()
	 */
	@Override
	protected Visitor<VerAttributes> getLogicVisitor() {
		return new VerLogicVisitor();
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#getSoundVisitor()
	 */
	@Override
	protected Visitor<VerAttributes> getSoundVisitor() {return null;}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Test#addNodes(org.ajar.age.Node)
	 */
	@Override
	protected void addNodes(Node<VerAttributes> root) {
		try {
			BufferedImage bi = ImageIO.read(
					//new File("/home/mstockbr/private-workspace/AGE/ver/ver/ajar/age/t8/loader/map.png")
					new File("/home/reverend/workspace/AGE/ver/ver/ajar/age/t8/loader/map.png")
			);
			
			int[] array = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
			Integer[] intArray = new Integer[array.length];
			for(int i = 0; i < intArray.length; i++){
				intArray[i] = array[i];
			}
			VerTileMapLoader loader = new VerTileMapLoader();
			Node<VerAttributes> map = loader.loadTileMap(40, intArray);
			
			root.addChild(map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VerController controller = new VerController(
				VerAttribute.X_TILE_REQ,
				VerAttribute.Y_TILE_REQ,
				root
		);
		
		//Unit 1
		DefaultNode<VerAttributes> unit1 = new DefaultNode<VerAttributes>(new VerAttributes());
		
		unit1.getAttributes().setSimpleAttributes(VerAttribute.values());
		unit1.getAttributes().setAttribute(VerAttribute.TEAM, 1);
		Rectangle bounds = new Rectangle(0,0,16,16);
		unit1.getAttributes().setAttribute(CollisionAttribute.BOUNDING_BOX,bounds);
		
		root.addChild(new VerCollidable(new VerEntity(new VerDisplayable(unit1))));
		

		this.panel.addMouseListener(controller);
		unit1.getDecorator(VerEntity.class).addController(controller);
		
		//Unit 2
		DefaultNode<VerAttributes> unit2 = new DefaultNode<VerAttributes>(new VerAttributes());
		
		unit2.getAttributes().setSimpleAttributes(VerAttribute.values());
		unit2.getAttributes().setTilePosition(4, 4);
		unit2.getAttributes().setAttribute(VerAttribute.IMAGE_X, 15);
		unit2.getAttributes().setAttribute(VerAttribute.TEAM, 2);
		Rectangle bounds2 = new Rectangle(4,4,16,16);
		unit2.getAttributes().setAttribute(CollisionAttribute.BOUNDING_BOX,bounds2);
		
		root.addChild(new VerCollidable(new VerEntity(new VerDisplayable(unit2))));
		
		this.panel.addMouseListener(controller);
		unit2.getDecorator(VerEntity.class).addController(controller);
	}

}
