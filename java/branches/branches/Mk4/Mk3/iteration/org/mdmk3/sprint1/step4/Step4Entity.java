package org.mdmk3.sprint1.step4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.DefaultEntity;

public class Step4Entity extends DefaultEntity<Step4Attributes> implements KeyListener {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	public Step4Entity(Node<Step4Attributes> node) {
		super(node);
		Step4BounceState bounce = new Step4BounceState();
		Step4GameState state = new Step4GameState(bounce);
		bounce.put(new Step4MoveEffect(Step4ActionType.MOVE,state));
		bounce.put(new Step4MoveEffect(null,state));
		setState(state);
	}
	
	public void updateState(){
		super.updateState();
		//if(up || down || right || left) addAction(Step4ActionType.MOVE);
		if(up) addAction(Step4ActionType.ACCELL_YNEG);
		if(down)addAction(Step4ActionType.ACCELL_YPOS);
		if(right)addAction(Step4ActionType.ACCELL_XPOS);
		if(left) addAction(Step4ActionType.ACCELL_XNEG);
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
