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
 * org.mdmk2.core.disp2d
 * AbstractDisplayable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.core.disp2d;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Vector;

import org.mdmk2.core.Node;

/**
 * This class is an implementation of Node2d and Displayable that implements all of their methods in a way that should
 * be suitable to most applications. In order to ensure that subclasses will be able to convieniently display themselves
 * the abstract <code>drawSelf</code> method has been provided.
 * @author mstockbridge
 * 15-May-10
 */
public abstract class AbstractDisplayable implements Displayable, Node2d {

	protected Shape[] boundingSurface;
	protected AffineTransform transform;
	protected Vector<Node<Rectangle>> children;
	protected Node2d parent;
	
	public AbstractDisplayable(){
		children = new Vector<Node<Rectangle>>();
		transform = AffineTransform.getTranslateInstance(0.0d, 0.0d);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#addChild(org.mdmk2.core.Node)
	 */
	public void addChild(Node<Rectangle> child) {
		children.add(child);
		
		if(child instanceof Node2d){
			Node2d c2d = (Node2d)child;
			
			if(!c2d.hasParent()){
				c2d.setParent(this);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#getChildren()
	 */
	public List<Node<Rectangle>> getChildren() {
		return children;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#hasChildren()
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#isInRange(java.lang.Object)
	 */
	public boolean isInRange(Rectangle range) {
		AffineTransform at = getDrawTransform();
		for(Shape r : boundingSurface){
			if(at != null){
				if(at.createTransformedShape(r).intersects(range)) return true;
			}else{
				if(r.intersects(range)) return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#removeChild(org.mdmk2.core.Node)
	 */
	public void removeChild(Node<Rectangle> child) {
		children.remove(child);
	}

	/**
	 * Gets the local transform of this object relative to it's parent.
	 * mstockbridge
	 * 15-May-10
	 * @return	the local <code>AffineTransform</code>
	 */
	public AffineTransform getTransform() {
		return transform;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#getDrawTransform()
	 */
	public AffineTransform getDrawTransform(){
		AffineTransform local = getTransform();
		AffineTransform ret = null;
		
		if(hasParent()){
			AffineTransform pt = getParent().getDrawTransform();
			
			if(pt != null){
				ret = (AffineTransform) pt.clone();
				
				if(local != null){
					ret.concatenate(local);
				}
			}
		}else if(local != null){
			ret = (AffineTransform) local.clone();
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#getParent()
	 */
	public Node2d getParent() {
		return parent;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#setParent(org.mdmk2.core.Node)
	 */
	public void setParent(Node2d parent) {
		this.parent = parent;
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.disp2d.Node2d#hasParent()
	 */
	public boolean hasParent() {
		return parent != null;
	}
	
	/**
	 * Returns any <code>Shapes</code> currently defining the bounding surface
	 * being used in the <code>isInRange</code> method.
	 * mstockbridge
	 * 15-May-10
	 * @return	the current bounding surface.
	 */
	public Shape[] getBoundingSurface() {
		return boundingSurface;
	}
	
	/**
	 * Sets the bounding surface to the supplied <code>Shape</code>(s), to be used
	 * in the <code>isInRange</code> method.
	 * mstockbridge
	 * 15-May-10
	 * @param	boundingSurface	one or more <code>Shape</code>s to be used as the
	 * 							bounding surface.
	 */
	public void setBoundingSurface(Shape... boundingSurface) {
		this.boundingSurface = boundingSurface;
	}

}
