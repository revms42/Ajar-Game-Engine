/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 8, 2013 Matthew Stockbridge
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
 * ver.ajar.age.t2
 * VerLogicVisitor.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t4.logic;

import org.ajar.age.logic.Entity;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.LogicVisitor;


/**
 * @author reverend
 *
 */
public class VerLogicVisitor extends LogicVisitor<HashAttributes> {

	/**
	 * @param entityClass
	 */
	public VerLogicVisitor() {
		super(VerEntity.class);
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#isInView(org.ajar.age.Decorator)
	 */
	@Override
	protected boolean isInView(Entity<HashAttributes> decorator) {
		//Note: This normally would need to be more complex.
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#startProcess()
	 */
	@Override
	protected void startProcess() {
		//If we were having concurrent action required this would be where we start it.
	}

	/* (non-Javadoc)
	 * @see org.ajar.age.AbstractVisitor#finishProcess()
	 */
	@Override
	protected void finishProcess() {
		//If we were having concurrent action required this would be where we'd set everything.
	}

}
