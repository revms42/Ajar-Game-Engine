/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Jun 5, 2015 Matthew Stockbridge
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
 * UpdateScrollable.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.disp.scroll;

import java.awt.Rectangle;

import org.ajar.age.Attributes;
import org.ajar.age.Decorator;
import org.ajar.age.logic.AbstractChainableEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;
/**
 * Anything that will be updated by this method will need to include:
 * <p>
 * <code>@DecoratesFor(types = {Scrollable.class})</code>
 * <p>
 * In their class definition (so that it can be found).
 * @author mstockbr
 *
 */
public class UpdateScrollable<A extends Attributes> extends AbstractChainableEffect<A> {

	private final Rectangle screenBounds;
	private final int offsetX;
	private final int offsetY;
	
	/**
	 * @param result
	 */
	public UpdateScrollable(State<A> result, Rectangle screenBounds) {
		super(result);
		this.screenBounds = screenBounds;
		this.offsetX = screenBounds.width / 2;
		this.offsetY = screenBounds.height / 2;
	}

	/* (non-Javadoc)
	 * TODO: This method may need optimizing. I suspect that it may result in slow-down.
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity, org.ajar.age.Attributes)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doAction(Entity<A> entity, A attributes) {
		if(entity.hasCapability((Class<? extends Decorator<A>>) Scrollable.class)){
			Scrollable<A> map = (Scrollable<A>)entity.getDecorator(Scrollable.class);
			
			int width = map.getTotalWidth();
			int height = map.getTotalHeight();
			
			ScrollTarget<A> target = map.getScrollTarget();
			
			double x = target.getPosX();
			double y = target.getPosY();
			
			if(map.getScrolledBounds() == null){
				map.setScrolledBounds((Rectangle)screenBounds.clone());
			}
			
			if(x > offsetX){
				if(x < width-offsetX){
					map.setScrollX((int)(x - offsetX));
				}else{
					map.setScrollX(width-(2*offsetX));
				}
			}else{
				map.setScrollX(0);
			}
			
			if(y > offsetY){
				if(y < height-offsetY){
					map.setScrollY((int)(y - offsetY));
				}else{
					map.setScrollY(height-(2*offsetY));
				}
			}else{
				map.setScrollY(0);
			}
		}
	}

}
