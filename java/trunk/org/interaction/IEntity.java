package org.interaction;

import org.model.IStats;

public interface IEntity<K> extends IStats<K> {
	
	public IStats<K> getStats();
	public void setStats(IStats<K> stats);

}
