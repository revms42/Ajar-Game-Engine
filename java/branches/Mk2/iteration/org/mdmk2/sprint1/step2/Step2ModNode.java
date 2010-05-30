/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 26-May-10 Matthew Stockbridge
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
 * org.mdmk2.sprint1.step2
 * Step2ModNode.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step2;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Vector;

import org.mdmk2.core.AbstractAttributeNode;
import org.mdmk2.core.logic.AttributeEventType;
import org.mdmk2.core.logic.AttributeListener;
import org.mdmk2.core.logic.AttributeType;
import org.mdmk2.core.logic.Updatable;

/**
 * @author mstockbridge
 * 26-May-10
 */
public class Step2ModNode extends AbstractAttributeNode<Rectangle,Integer> implements Updatable {

	public static enum ModEvent implements AttributeEventType{
		MOD_COUNT;
	};
	
	public static class ModType implements AttributeType<Integer>{};
	
	private final int mod;
	
	public Step2ModNode(int mod){
		this.mod = mod;
		this.value = 0;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Updatable#readyForUpdate()
	 */
	public boolean readyForUpdate() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Updatable#update()
	 */
	public void update() {
		if(setValue(this.getValue() + 1).intValue() > mod){
			for(AttributeListener listener : listeners.get(ModEvent.MOD_COUNT)){
				listener.attributeChanged(ModEvent.MOD_COUNT, this, null);
			}
			setValue(0);
		}
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.Node#isInRange(java.lang.Object)
	 */
	public boolean isInRange(Rectangle range) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Attribute#registeredEvents()
	 */
	public AttributeEventType[] registeredEvents() {
		return ModEvent.values();
	}

}
