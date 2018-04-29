/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 14-Oct-10 Matthew Stockbridge
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
 * MDMk2
 * org.mdmk2.sprint1.Step4
 * Step4Box.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import org.mdmk2.core.collision.Collidable;
import org.mdmk2.core.collision.CollidableImp;
import org.mdmk2.core.disp2d.DefaultSprite;
import org.mdmk2.core.disp2d.DisplayableImp;
import org.mdmk2.core.logic.Action;

/**
 * @author reverend
 * 14-Oct-10
 */
public class Step4Box extends DefaultSprite<Step4Attributes> implements Collidable<Step4Attributes>{

	private final CollidableImp<Step4Attributes> cImp;
	/**
	 * @param attributes
	 * @param dImp
	 */
	public Step4Box(Step4Attributes attributes, DisplayableImp<Step4Attributes> dImp, CollidableImp<Step4Attributes> cImp) {
		super(attributes, dImp);
		this.cImp = cImp;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#needsCollisionCheck()
	 */
	public boolean needsCollisionCheck() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#collideWith(org.mdmk2.core.collision.Collidable)
	 */
	public Action collideWith(Collidable<Step4Attributes> s) {
		return cImp.collideWith(s);
	}

	/* (non-Javadoc)
	 * @see org.mdmk2.core.collision.Collidable#getImplementation()
	 */
	public CollidableImp<Step4Attributes> getImplementation() {
		// TODO Auto-generated method stub
		return cImp;
	}

}
