package org.mdmk3.core.logic;

import java.util.HashMap;
import java.util.Set;

import org.mdmk3.core.Attributes;

public class DefaultState<A extends Attributes> implements State<A> {

	private final HashMap<Action,Effect<A>> effectMap;
	
	public DefaultState(){
		effectMap = new HashMap<Action,Effect<A>>();
	}
	/* (non-Javadoc)
	 * @see org.mdmk2.core.logic.State#perform(org.mdmk2.core.logic.StatedImp, org.mdmk2.core.logic.Action)
	 */
	public State<A> perform(Entity<A> subject, Action e) {
		return effectMap.get(e).perform(subject);
	}
	public Set<Action> getActions() {
		return effectMap.keySet();
	}
	public Effect<A> put(Effect<A> arg1) {
		return effectMap.put(arg1.getAction(), arg1);
	}
	
	public HashMap<Action,Effect<A>> getEffectMap() {
		return effectMap;
	}

}
