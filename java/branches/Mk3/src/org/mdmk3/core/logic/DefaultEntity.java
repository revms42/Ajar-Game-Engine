package org.mdmk3.core.logic;

import java.util.List;
import java.util.Vector;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

public class DefaultEntity<A extends Attributes> extends AbstractEntity<A> {
	
	private final Vector<Action> actions;
	private State<A> state;
	
	public DefaultEntity(Node<A> node) {
		super(node);
		actions = new Vector<Action>();
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#addAction(org.mdmk2.core.logic.Action)
	 */
	public void addAction(Action action) {
		actions.add(action);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#getActions()
	 */
	public List<Action> getActions() {
		return actions;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#getState()
	 */
	public State<A> getState() {
		return state;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#needsStateUpdate()
	 */
	public boolean needsStateUpdate() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#setState(org.mdmk2.core.logic.State)
	 */
	public void setState(State<A> s) {
		this.state = s;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.StatedImp#updateState()
	 */
	public void updateState() {
		if(actions.size() > 0){
			for(Action a : actions){
				state = state.perform(this,a);
			}
			actions.removeAllElements();
		}
		state = state.perform(this, null);
		requestInput();
		
	}

	@Override
	public void requestInput() {
		for(Controller<A> controller : getControllers()){
			controller.pollForInput(this);
		}
	}
}
