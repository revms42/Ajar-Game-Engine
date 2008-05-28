package org.interaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ConditionPalette<C,A,K> extends HashMap<C,ICondition<A,K>>{
	private static final long serialVersionUID = 9032125363869929153L;

	public ConditionPalette(){
		super();
	}
	
	public ConditionPalette(C[] keys, ICondition<A,K>[] actions){
		super();
		
		for(int i = 0; i < keys.length; i++){
			if(actions.length > i){
				this.put(keys[i], actions[i]);
			}else{
				break;
			}
		}
	}

	public ConditionPalette(Collection<C> keys, Collection<ICondition<A,K>> actions){
		super();
		
		Iterator<ICondition<A,K>> j = actions.iterator();
		Iterator<C> 		i = keys.iterator();
		for( ;i.hasNext() && j.hasNext(); ){
			this.put(i.next(), j.next());
		}
	}
	
	public boolean isFullfilled(C key, IEntity<K> subject, IEntity<K>... objects){
		return this.get(key).isFullfilled(subject, objects);
	}
	
	public void performAction(C key, IEntity<K> subject, IEntity<K>... objects){
		this.get(key).performAction(subject, objects);
	}
}
