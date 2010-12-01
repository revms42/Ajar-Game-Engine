package org.mdmk3.core.logic;

import org.mdmk3.core.Attributes;

public abstract class AbstractEffect<A extends Attributes> implements Effect<A> {
	private final Action a;
	private final State<A> result;
	
	public AbstractEffect(Action a, State<A> result){
		this.a = a;
		this.result = result;
	}
	
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#getAction()
	 */
	public Action getAction() {
		return a;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#getResultState()
	 */
	public State<A> getResultState() {
		return result;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.Effect#perform(org.mdmk2.core.logic.StatedImp)
	 */
	public State<A> perform(Entity<A> state) {
		doAction(state);
		return result;
	}

	protected abstract void doAction(Entity<A> state);
}
