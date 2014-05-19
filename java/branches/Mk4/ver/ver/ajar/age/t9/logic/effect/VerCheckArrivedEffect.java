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
package ver.ajar.age.t9.logic.effect;

import org.ajar.age.logic.AbstractCondition;
import org.ajar.age.logic.Effect;
import org.ajar.age.logic.Entity;

import ver.ajar.age.t9.VerAttribute;
import ver.ajar.age.t9.VerAttributes;

/**
 * @author reverend
 *
 */
public class VerCheckArrivedEffect extends AbstractCondition<VerAttributes> {
	
	/**
	 * @param result
	 */
	public VerCheckArrivedEffect(Effect<VerAttributes> done, Effect<VerAttributes> notDone) {
		super(done,notDone);
	}

	/* (non-Javadoc)
	 * @see ver.ajar.age.t0.Condition#meetsCondition(org.ajar.age.logic.Entity)
	 */
	@Override
	public boolean meetsCondition(Entity<VerAttributes> entity) {
		//System.out.println("StartMove " + entity.hashCode());
		int xdest = entity.getAttributes().getAttribute(VerAttribute.X_TILE_DEST).intValue();
		int xtile = entity.getAttributes().getAttribute(VerAttribute.X_TILE_POS).intValue();
		int ydest = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_DEST).intValue();
		int ytile = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_POS).intValue();
		
		return xdest == xtile && ydest == ytile;
	}

}
