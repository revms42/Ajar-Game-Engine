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
package ver.ajar.age.t5.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.ajar.age.logic.Action;
import org.ajar.age.logic.Controller;
import org.ajar.age.logic.DefaultEvent;
import org.ajar.age.logic.Entity;

import ver.ajar.age.t5.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerController implements Controller<VerAttributes>, KeyListener {
	
	private int typed;
	private boolean process;
	
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Controller#pollForInput(org.ajar.age.logic.Entity)
	 */
	@Override
	public void pollForInput(Entity<VerAttributes> entity) {
		//TODO: Only one entity takes input right now, otherwise there could be more to this.
		if(typed != -1){
			entity.addEvent(new DefaultEvent<VerAttributes>(getAction(typed),null));
			typed = -1;
		}
	}

	public void done(){
		process = true;
	}
	
	private Action getAction(int key){
		switch(key){
		case KeyEvent.VK_UP:
			return VerAction.MOVE_Y_NEG;
		case KeyEvent.VK_DOWN:
			return VerAction.MOVE_Y_POS;
		case KeyEvent.VK_RIGHT:
			return VerAction.MOVE_X_POS;
		case KeyEvent.VK_LEFT:
			return VerAction.MOVE_X_NEG;
		default:
			return null;
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(process){
			typed = arg0.getKeyCode();
			process = false;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		//held = -1;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		if(process){
			typed = arg0.getKeyCode();
			process = false;
		}
	}

}
