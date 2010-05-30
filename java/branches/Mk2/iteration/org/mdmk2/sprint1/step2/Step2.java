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
 * org.mdmk2.sprint1.step1
 * Step1.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.mdmk2.core.AbstractAttributeNode;
import org.mdmk2.core.AttributeListenerNode;
import org.mdmk2.core.Node;
import org.mdmk2.core.RootNode;
import org.mdmk2.core.disp2d.AbstractSprite2d;
import org.mdmk2.core.disp2d.DisplayPanel;
import org.mdmk2.core.logic.Attribute;
import org.mdmk2.core.logic.AttributeEventType;
import org.mdmk2.core.logic.AttributeListener;
import org.mdmk2.core.logic.Entity;
import org.mdmk2.core.logic.LinearAttribute;

/**
 * @author mstockbridge
 * 15-May-10
 */
public class Step2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DisplayPanel displayPanel;
	
	private Step2(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Step2: Moving Display");
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
		Step2 step2 = (new Step2());
		RootNode<Rectangle> root = new RootNode<Rectangle>();
		Ellipse2D circle = new Ellipse2D.Double(0.0d,0.0d,20.0d,20.0d);
		Step2Sprite sprite = new Step2Sprite(circle,Color.RED);
		sprite.setTransform(AffineTransform.getTranslateInstance(10.0d, 10.0d));
		
		final LinearAttribute<Integer> x = new LinearAttribute<Integer>(1,600,0,0);
		final LinearAttribute<Integer> y = new LinearAttribute<Integer>(1,600,0,0);

		final LinearAttribute<Integer> dx = new LinearAttribute<Integer>(1,1,-1,1);
		final LinearAttribute<Integer> dy = new LinearAttribute<Integer>(1,1,-1,1);
		
		sprite.addAttribute(x.type, x);
		sprite.addAttribute(y.type, y);

		sprite.addAttribute(dx.type, dx);
		sprite.addAttribute(dy.type, dy);
		
		AttributeListener reverseListener = new AttributeListener(){

			public void attributeChanged(AttributeEventType event, Attribute<?> attr, Entity entity) {
				if(attr == x){
					Attribute<Integer> xvel = entity.getAttribute(dx.type);
					xvel.setValue(xvel.getValue());
				}else{
					Attribute<Integer> yvel = entity.getAttribute(dy.type);
					yvel.setValue(yvel.getValue());					
				}
			}
		};
		sprite.addAttributeListener(reverseListener, x, LinearAttribute.LinearAttributeEvent.MAX_REACHED);
		sprite.addAttributeListener(reverseListener, x, LinearAttribute.LinearAttributeEvent.MIN_REACHED);
		
		sprite.addAttributeListener(reverseListener, y, LinearAttribute.LinearAttributeEvent.MAX_REACHED);
		sprite.addAttributeListener(reverseListener, y, LinearAttribute.LinearAttributeEvent.MIN_REACHED);
		
		AttributeListener transformListener = new AttributeListener(){

			public void attributeChanged(AttributeEventType event, Attribute<?> attr, Entity entity) {
				AbstractSprite2d sprite = (AbstractSprite2d)entity;
				
				float xpos = sprite.getAttribute(x.type).getValue().floatValue();
				float ypos = sprite.getAttribute(y.type).getValue().floatValue();
				
				sprite.getTransform().setToTranslation(xpos, ypos);
			}
			
		};
		
		sprite.addAttributeListener(transformListener, y, LinearAttribute.LinearAttributeEvent.VALUE_CHANGED);
		
		Step2ModNode mod = new Step2ModNode(10);
		
		AttributeListenerNode<Rectangle> updatePos = new AttributeListenerNode<Rectangle>(){

			public void attributeChanged(AttributeEventType event, Attribute<?> attr, Entity entity) {
				for(Node<Rectangle> node : this.getChildren()){
					if(node instanceof Step2Sprite){
						Step2Sprite sprite = (Step2Sprite)node;
						
						int xvel = sprite.getAttribute(dx.type).getValue();
						int yvel = sprite.getAttribute(dy.type).getValue();
						
						int xpos = sprite.getAttribute(x.type).getValue();
						int ypos = sprite.getAttribute(y.type).getValue();
						
						sprite.setValue(x.type, xpos + xvel);
						sprite.setValue(y.type, ypos + yvel);
					}
				}
			}

			public boolean isInRange(Rectangle range) {
				return true;
			}
		};
		mod.addAttributeListener(updatePos, mod.registeredEvents());
		updatePos.addChild(sprite);
		
		root.addChild(mod);
		root.addChild(updatePos);
		Step2GameLoop loop = new Step2GameLoop(root,step2.displayPanel);
		
		Thread t = new Thread(loop);
		
		t.start();
		step2.setVisible(true);
	}

}
