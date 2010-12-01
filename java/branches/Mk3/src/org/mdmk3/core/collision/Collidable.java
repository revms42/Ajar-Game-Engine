package org.mdmk3.core.collision;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Decorator;
import org.mdmk3.core.logic.Action;

public interface Collidable<A extends Attributes> extends Decorator<A> {

	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @param s
	 * @return
	 */
	public Action collideWith(Collidable<A> s);
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public boolean needsCollisionCheck();
}
