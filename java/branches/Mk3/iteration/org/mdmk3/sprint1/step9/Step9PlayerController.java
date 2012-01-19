package org.mdmk3.sprint1.step9;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mdmk3.core.logic.Controller;
import org.mdmk3.core.logic.Entity;

public class Step9PlayerController implements KeyListener, Controller<Step9Attributes> {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pollForInput(Entity<Step9Attributes> entity) {
		if(up) entity.addAction(Step9ActionType.ACCELL_YNEG);
		if(down)entity.addAction(Step9ActionType.ACCELL_YPOS);
		if(right)entity.addAction(Step9ActionType.ACCELL_XPOS);
		if(left) entity.addAction(Step9ActionType.ACCELL_XNEG);
	}

}
