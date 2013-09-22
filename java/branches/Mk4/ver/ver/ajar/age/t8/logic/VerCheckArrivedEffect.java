/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 22, 2013 Matthew Stockbridge
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * (but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * AGE
 * org.ajar.age.logic
 * VerCheckArrivedEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t8.logic;

import org.ajar.age.logic.Effect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;

import ver.ajar.age.t8.VerAttribute;
import ver.ajar.age.t8.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerCheckArrivedEffect implements Effect<VerAttributes> {

	private final State<VerAttributes> done;
	private final State<VerAttributes> notDone;
	
	/**
	 * @param result
	 */
	public VerCheckArrivedEffect(State<VerAttributes> done, State<VerAttributes> notDone) {
		this.done = done;
		this.notDone = notDone;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.Effect#perform(org.ajar.age.logic.Entity)
	 */
	@Override
	public State<VerAttributes> perform(Entity<VerAttributes> entity) {
		int xdest = entity.getAttributes().getAttribute(VerAttribute.X_TILE_DEST).intValue();
		int xtile = entity.getAttributes().getAttribute(VerAttribute.X_TILE_POS).intValue();
		int ydest = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_DEST).intValue();
		int ytile = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_POS).intValue();
		
		This is where I left off.
		I need to refactor this to be chainable, but refactor both this class and
		set range to extend a binary state effect, that has a true state and a
		false state specified at the start, and it runs chained effects only on success.
		
		if(xdest == xtile && ydest == ytile){
			return done;
		}else{
			return notDone;
		}
	}

}
