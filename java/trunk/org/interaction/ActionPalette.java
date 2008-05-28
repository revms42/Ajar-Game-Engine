package org.interaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ActionPalette<A,K> extends HashMap<A,IAction<K>> {
	private static final long serialVersionUID = 5310994262314419389L;
	
	public ActionPalette(){
		super();
	}
	
	public ActionPalette(A[] keys, IAction<K>[] actions){
		super();
		
		for(int i = 0; i < keys.length; i++){
			if(actions.length > i){
				this.put(keys[i], actions[i]);
			}else{
				break;
			}
		}
	}

	public ActionPalette(Collection<A> keys, Collection<IAction<K>> actions){
		super();
		
		Iterator<IAction<K>> j = actions.iterator();
		Iterator<A> 		i = keys.iterator();
		for( ;i.hasNext() && j.hasNext(); ){
			this.put(i.next(), j.next());
		}
	}
	
	public synchronized void performAction(A action, IEntity<K> subject, IEntity<K>... objects){
		this.get(action).performAction(subject, objects);
	}
}
