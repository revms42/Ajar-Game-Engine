package org.mdmk3.sprint1.step6;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.DefaultEntity;

public class Step6Entity extends DefaultEntity<Step6Attributes> implements KeyListener {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	public Step6Entity(Node<Step6Attributes> node) {
		super(node);
		Step6BounceState bounce = new Step6BounceState();
		Step6GameState state = new Step6GameState(bounce);
		bounce.put(new Step6MoveEffect(Step6ActionType.MOVE,state));
		bounce.put(new Step6MoveEffect(null,state));
		setState(state);
	}
	
	public void updateState(){
		if(up || down || right || left) addAction(Step6ActionType.MOVE);
		if(up) addAction(Step6ActionType.ACCELL_YNEG);
		if(down)addAction(Step6ActionType.ACCELL_YPOS);
		if(right)addAction(Step6ActionType.ACCELL_XPOS);
		if(left) addAction(Step6ActionType.ACCELL_XNEG);
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
