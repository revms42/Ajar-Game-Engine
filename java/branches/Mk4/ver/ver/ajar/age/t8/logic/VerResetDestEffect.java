/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 23, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t8.logic
 * VerResetDestEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t8.logic;

import org.ajar.age.logic.AbstractChainableEffect;
import org.ajar.age.logic.Entity;
import org.ajar.age.logic.State;

import ver.ajar.age.t8.VerAttribute;
import ver.ajar.age.t8.VerAttributes;

/**
 * @author mstockbr
 *
 */
public class VerResetDestEffect extends AbstractChainableEffect<VerAttributes> {

	/**
	 * @param result
	 */
	public VerResetDestEffect(State<VerAttributes> result) {
		super(result);
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.logic.AbstractEffect#doAction(org.ajar.age.logic.Entity)
	 */
	@Override
	protected void doAction(Entity<VerAttributes> entity) {
		int xtile = entity.getAttributes().getAttribute(VerAttribute.X_TILE_POS).intValue();
		int ytile = entity.getAttributes().getAttribute(VerAttribute.Y_TILE_POS).intValue();
		entity.getAttributes().setAttribute(VerAttribute.X_TILE_DEST, xtile);
		entity.getAttributes().setAttribute(VerAttribute.Y_TILE_DEST, ytile);
	}

}
