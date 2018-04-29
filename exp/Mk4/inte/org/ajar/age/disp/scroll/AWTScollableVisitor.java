/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 8, 2015 Matthew Stockbridge
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
 * org.ajar.age.disp.scroll
 * AWTScollableVisitor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.disp.scroll;

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.ajar.age.Attributes;
import org.ajar.age.disp.awt.AWTDisplayable;
import org.ajar.age.disp.awt.AWTGraphicsPanel;
import org.ajar.age.disp.awt.AWTGraphicsVisitor;

/**
 * @author mstockbr
 *
 */
public abstract class AWTScollableVisitor<A extends Attributes, U extends AWTDisplayable<A>> extends AWTGraphicsVisitor<A> {

	private Scrollable<A> scrollable;
	
	/**
	 * {@inheritDoc}
	 */
	public AWTScollableVisitor(AWTGraphicsPanel comp, Class<? extends U> dispClass) {
		super(comp,dispClass);
	}

	/**
	 * Sets the Scrollable target that this visitor will be displaying.
	 * @param s
	 */
	public void setScrollable(Scrollable<A> s){
		scrollable = s;
	}
	
	/**
	 * Gets the Scrollable target that this visitor will be displaying.
	 * @return
	 */
	public Scrollable<A> getScrollable(){
		return scrollable;
	}
	
	/**
	 * Determines the unscrolled screen position of the node being drawn 
	 * @param unit
	 * @return
	 */
	public abstract Point displayPoint(U unit);
	
	/**
	 * Retrieves the image that will be drawn.
	 * @param unit
	 * @return
	 */
	public abstract BufferedImage displayImage(U unit);
	
	/**
	 * Performs a cast from <code>AWTDisplayable</code> to the destination subclass.
	 * <p>
	 * This should be performed by <code>disp.getDecorator(U.class);</code>, where <code>U</code>
	 * is the class to cast to.
	 * @param disp
	 * @return
	 */
	protected abstract U cast(AWTDisplayable<A> disp);
	
	/* (non-Javadoc)
	 * @see org.ajar.age.disp.awt.AWTGraphicsVisitor#update(org.ajar.age.disp.awt.AWTDisplayable)
	 */
	@Override
	public void update(AWTDisplayable<A> disp) {
		U unit = cast(disp);
		
		if(unit != null){
			Point point = displayPoint(unit);
			BufferedImage bi = displayImage(unit);
			
			Scrollable<A> map = getScrollable();
			
			if(bi != null && map != null){
				int dx = map.getScrollX();
				int dy = map.getScrollY();
				this.getSurface().drawImage(bi, null, point.x-dx, point.y-dy);
			}
		}
	}

	protected void startProcess(){
		//Overriding for compositing operation.
	}
	
	protected void finishProcess(){
		//Overriding for compositing operation.
	}
}
