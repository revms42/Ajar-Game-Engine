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
 * ver.ajar.age.t0
 * ChainableEffect.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package ver.ajar.age.t0;

import org.ajar.age.Attributes;
import org.ajar.age.logic.Effect;

/**
 * @author reverend
 *
 */
public interface ChainableEffect<A extends Attributes> extends Effect<A> {
	
	/**
	 * 
	 * @param child
	 * @return this chainable effect.
	 */
	public ChainableEffect<A> addToChain(ChainableEffect<A> child);
	public boolean hasChild();
	public ChainableEffect<A> getChild();
	public void setChild(ChainableEffect<A> child);
	public ChainableEffect<A> removeLastFromChain();
	public ChainableEffect<A> removeFromChain(ChainableEffect<A> effect);
}
