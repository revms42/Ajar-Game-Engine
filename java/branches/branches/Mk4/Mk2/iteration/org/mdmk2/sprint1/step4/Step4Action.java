/**
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) 11-Sep-10 Matthew Stockbridge
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
 * org.mdmk2.sprint1.step2
 * Step2BounceAction.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 2 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk2.sprint1.step4;

import org.mdmk2.core.logic.Action;

/**
 * @author reverend
 * 11-Sep-10
 */
public class Step4Action implements Action {
	
	public final Step4ActionType type;
	public final double xBump;
	public final double yBump;
	
	public Step4Action(Step4ActionType type){
		this.type = type;
		this.xBump = 0;
		this.yBump = 0;
	}
	
	public Step4Action(Step4ActionType type, double xBump, double yBump){
		this.type = type;
		this.xBump = xBump;
		this.yBump = yBump;
	}
}
