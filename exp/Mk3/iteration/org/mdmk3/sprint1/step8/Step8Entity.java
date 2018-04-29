package org.mdmk3.sprint1.step8;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultEntity;

public class Step8Entity extends DefaultEntity<Step8Attributes> implements KeyListener {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	@SuppressWarnings("unchecked")
	public Step8Entity(Node<Step8Attributes> node) {
		super(node);
		Step8DBounceState d = new Step8DBounceState();
		Step8HBounceState h = new Step8HBounceState(d);
		Step8VBounceState v = new Step8VBounceState(d);
		Step8GameState state = new Step8GameState(h,v,d);
		
		d.put(new Step8MoveEffect(Step8ActionType.MOVE,state));
		d.put(new CompoundEffect<Step8Attributes>(
				null,
				state,
				new Step8MoveEffect(null,state),
				new Step8AnimateEffect(Step8ActionType.ANIMATE,state)
		));
		
		h.put(new Step8MoveEffect(Step8ActionType.MOVE,state));
		h.put(new CompoundEffect<Step8Attributes>(
				null,
				state,
				new Step8MoveEffect(null,state),
				new Step8AnimateEffect(Step8ActionType.ANIMATE,state)
		));
		
		v.put(new Step8MoveEffect(Step8ActionType.MOVE,state));
		v.put(new CompoundEffect<Step8Attributes>(
				null,
				state,
				new Step8MoveEffect(null,state),
				new Step8AnimateEffect(Step8ActionType.ANIMATE,state)
		));
		
		setState(state);
	}
	
	public void updateState(){
		//if(up || down || right || left) addAction(Step8ActionType.MOVE);
		super.updateState();
		if(up) addAction(Step8ActionType.ACCELL_YNEG);
		if(down)addAction(Step8ActionType.ACCELL_YPOS);
		if(right)addAction(Step8ActionType.ACCELL_XPOS);
		if(left) addAction(Step8ActionType.ACCELL_XNEG);
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
