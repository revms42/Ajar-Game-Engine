package org.mdmk3.sprint1.step10;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mdmk3.core.logic.Controller;
import org.mdmk3.core.logic.Entity;

public class Step10PlayerController implements KeyListener, Controller<Step10Attributes> {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	private boolean shoot;

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
		case KeyEvent.VK_SPACE:
			shoot = true;
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
		case KeyEvent.VK_SPACE:
			shoot = false;
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void pollForInput(Entity<Step10Attributes> entity) {
		if(up) entity.addAction(Step10ActionType.ACCELL_YNEG);
		if(down)entity.addAction(Step10ActionType.ACCELL_YPOS);
		if(right)entity.addAction(Step10ActionType.ACCELL_XPOS);
		if(left) entity.addAction(Step10ActionType.ACCELL_XNEG);
		if(shoot){
			entity.addAction(Step10ActionType.SHOOT);
			shoot = false;
		}
	}

}
