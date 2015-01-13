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
package ver.ajar.age.t8.logic;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.ajar.age.logic.Action;
import org.ajar.age.logic.Attribute;
import org.ajar.age.logic.Controller;
import org.ajar.age.logic.DefaultEvent;
import org.ajar.age.logic.Entity;

import ver.ajar.age.t8.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerController implements Controller<VerAttributes>, MouseListener {
	
	private Entity<VerAttributes> target;
	private Attribute<Number> destX;
	private Attribute<Number> destY;
	private boolean pressed;
	private boolean process;
	
	
	public VerController(Entity<VerAttributes> target, Attribute<Number> destX, Attribute<Number> destY){
		this.target = target;
		this.destX = destX;
		this.destY = destY;
		this.process = true;
	}
	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Controller#pollForInput(org.ajar.age.logic.Entity)
	 */
	@Override
	public void pollForInput(Entity<VerAttributes> entity) {
		//TODO: Only one entity takes input right now, otherwise there could be more to this.
		if(pressed){
			entity.addEvent(new DefaultEvent<VerAttributes>(getAction(),null));
			pressed = false;
		}
	}

	public void done(){
		process = true;
	}
	
	private Action getAction(){
		return VerAction.START_MOVE;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(process){
			Point p = e.getPoint();
			target.getAttributes().setAttribute(destX,VerAttributes.coordinateToTile(p.x));
			target.getAttributes().setAttribute(destY,VerAttributes.coordinateToTile(p.y));
			
			process = false;
			pressed = true;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
