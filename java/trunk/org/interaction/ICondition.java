package org.interaction;

import java.util.Vector;

public interface ICondition<A,K> extends IAction<K>{

	public ActionPalette<A,K> getActionPalette();
	public void setActionPalette(ActionPalette<A,K> palette);
	
	public Vector<A> getActions();
	public void setActions(Vector<A> actions);
	public A getAction(int index);
	public void setAction(int index, A action);
	public boolean addAction(A action);
	public A removeAction(A action);
	public void removeAllActions();
	
	public boolean isFullfilled(IEntity<K> subject, IEntity<K>... objects);
}
