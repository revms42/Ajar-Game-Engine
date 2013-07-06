package org.mdmk3.sprint1.step9;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultEntity;

public class Step9Entity extends DefaultEntity<Step9Attributes>{

	@SuppressWarnings("unchecked")
	public Step9Entity(Node<Step9Attributes> node) {
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

}
