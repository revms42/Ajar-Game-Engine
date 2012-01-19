package org.mdmk3.sprint1.step10;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.CompoundEffect;
import org.mdmk3.core.logic.DefaultEntity;

public class Step10Entity extends DefaultEntity<Step10Attributes>{

	@SuppressWarnings("unchecked")
	public Step10Entity(Node<Step10Attributes> node) {
		super(node);
		Step10DBounceState d = new Step10DBounceState();
		Step10HBounceState h = new Step10HBounceState(d);
		Step10VBounceState v = new Step10VBounceState(d);
		Step10GameState state = new Step10GameState(h,v,d);
		
		d.put(new Step10MoveEffect(Step10ActionType.MOVE,state));
		d.put(new CompoundEffect<Step10Attributes>(
				null,
				state,
				new Step10MoveEffect(null,state),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,state)
		));
		
		h.put(new Step10MoveEffect(Step10ActionType.MOVE,state));
		h.put(new CompoundEffect<Step10Attributes>(
				null,
				state,
				new Step10MoveEffect(null,state),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,state)
		));
		
		v.put(new Step10MoveEffect(Step10ActionType.MOVE,state));
		v.put(new CompoundEffect<Step10Attributes>(
				null,
				state,
				new Step10MoveEffect(null,state),
				new Step10AnimateEffect(Step10ActionType.ANIMATE,state)
		));
		
		setState(state);
	}

}
