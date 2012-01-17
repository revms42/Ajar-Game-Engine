package org.mdmk3.sprint1.step9;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.CompoundEffect;

public class Step9PlayerEntity extends Step9Entity implements KeyListener {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	
	@SuppressWarnings("unchecked")
	public Step9PlayerEntity(Node<Step9Attributes> node) {
		super(node);
		Step9DBounceState d = new Step9DBounceState();
		Step9HBounceState h = new Step9HBounceState(d);
		Step9VBounceState v = new Step9VBounceState(d);
		Step9GameState state = new Step9GameState(h,v,d);
		
		d.put(new Step9MoveEffect(Step9ActionType.MOVE,state));
		d.put(new CompoundEffect<Step9Attributes>(
				null,
				state,
				new Step9MoveEffect(null,state),
				new Step9AnimateEffect(Step9ActionType.ANIMATE,state)
		));
		
		h.put(new Step9MoveEffect(Step9ActionType.MOVE,state));
		h.put(new CompoundEffect<Step9Attributes>(
				null,
				state,
				new Step9MoveEffect(null,state),
				new Step9AnimateEffect(Step9ActionType.ANIMATE,state)
		));
		
		v.put(new Step9MoveEffect(Step9ActionType.MOVE,state));
		v.put(new CompoundEffect<Step9Attributes>(
				null,
				state,
				new Step9MoveEffect(null,state),
				new Step9AnimateEffect(Step9ActionType.ANIMATE,state)
		));
		
		setState(state);
	}
	
	public void updateState(){
		//if(up || down || right || left) addAction(Step9ActionType.MOVE);
		super.updateState();
		if(up) addAction(Step9ActionType.ACCELL_YNEG);
		if(down)addAction(Step9ActionType.ACCELL_YPOS);
		if(right)addAction(Step9ActionType.ACCELL_XPOS);
		if(left) addAction(Step9ActionType.ACCELL_XNEG);
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
