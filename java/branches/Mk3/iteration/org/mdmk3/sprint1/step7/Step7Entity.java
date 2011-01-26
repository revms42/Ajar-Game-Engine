package org.mdmk3.sprint1.step7;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.DefaultEntity;

public class Step7Entity extends DefaultEntity<Step7Attributes> implements KeyListener {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	public Step7Entity(Node<Step7Attributes> node) {
		super(node);
		Step7BounceState bounce = new Step7BounceState();
		Step7GameState state = new Step7GameState(bounce);
		bounce.put(new Step7MoveEffect(Step7ActionType.MOVE,state));
		bounce.put(new Step7MoveEffect(null,state));
		setState(state);
	}
	
	public void updateState(){
		if(up || down || right || left) addAction(Step7ActionType.MOVE);
		if(up) addAction(Step7ActionType.ACCELL_YNEG);
		if(down)addAction(Step7ActionType.ACCELL_YPOS);
		if(right)addAction(Step7ActionType.ACCELL_XPOS);
		if(left) addAction(Step7ActionType.ACCELL_XNEG);
		super.updateState();
	}

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

}
