/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 16, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t4.logic
 * VerController.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t4.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.ajar.age.logic.Controller;
import org.ajar.age.logic.DefaultEvent;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.Event;
import org.ajar.age.logic.HashAttributes;

/**
 * @author reverend
 *
 */
public class VerController implements Controller<HashAttributes>, KeyListener {

	private int held;
	private int typed;
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Controller#pollForInput(org.ajar.age.logic.Entity)
	 */
	@Override
	public void pollForInput(Entity<HashAttributes> entity) {
		//TODO: Only one entity takes input right now, otherwise there could be more to this.
		if(held != -1){
			entity.addEvent(getAction(held));
		}else if(typed != -1){
			entity.addEvent(getAction(typed));
			typed = -1;
		}
	}

	private Event<HashAttributes> getAction(int key){
		switch(key){
		case KeyEvent.VK_UP:
			return new DefaultEvent<HashAttributes>(VerAction.ACC_Y_NEG,null);
		case KeyEvent.VK_DOWN:
			return new DefaultEvent<HashAttributes>(VerAction.ACC_Y_POS,null);
		case KeyEvent.VK_RIGHT:
			return new DefaultEvent<HashAttributes>(VerAction.ACC_X_POS,null);
		case KeyEvent.VK_LEFT:
			return new DefaultEvent<HashAttributes>(VerAction.ACC_X_NEG,null);
		default:
			return null;
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		held = arg0.getKeyCode();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		held = -1;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		typed = arg0.getKeyCode();
	}

}
