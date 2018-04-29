/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 30, 2014 Matthew Stockbridge
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
 * org.ajar.logic.loader.parser.test.type
 * DummyAction.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.logic.loader.parser.test.type;

import org.ajar.age.logic.Action;

/**
 * @author mstockbr
 *
 */
public class DummyAction implements Action {

	public final int a;
	public final long b;
	public final float c;
	public final double d;
//	public final short e;
	public final char f;
	public final boolean g;
	public final String h;
	
	public DummyAction(){
		this.a = -1;
		this.b = -1;
		this.c = -1;
		this.d = -1;
		//this.e = -1;
		this.f = '-';
		this.g = false;
		this.h = null;
	}
	
	public DummyAction(int a){
		this.a = a;
		this.b = -1;
		this.c = -1;
		this.d = -1;
		//this.e = -1;
		this.f = '-';
		this.g = false;
		this.h = null;
	}
	
	public DummyAction(long b){
		this.a = -1;
		this.b = b;
		this.c = -1;
		this.d = -1;
		//this.e = -1;
		this.f = '-';
		this.g = false;
		this.h = null;
	}
	
	public DummyAction(float c){
		this.a = -1;
		this.b = -1;
		this.c = c;
		this.d = -1;
		//this.e = -1;
		this.f = '-';
		this.g = false;
		this.h = null;
	}
	
	public DummyAction(double d){
		this.a = -1;
		this.b = -1;
		this.c = -1;
		this.d = d;
		//this.e = -1;
		this.f = '-';
		this.g = false;
		this.h = null;
	}
	
	public DummyAction(short e){
		this.a = -1;
		this.b = -1;
		this.c = -1;
		this.d = -1;
		//this.e = e;
		this.f = '-';
		this.g = false;
		this.h = null;
	}
	
	public DummyAction(char f){
		this.a = -1;
		this.b = -1;
		this.c = -1;
		this.d = -1;
		//this.e = -1;
		this.f = f;
		this.g = false;
		this.h = null;
	}
	
	public DummyAction(boolean g){
		this.a = -1;
		this.b = -1;
		this.c = -1;
		this.d = -1;
		//this.e = -1;
		this.f = '-';
		this.g = g;
		this.h = null;
	}
	
	public DummyAction(String h){
		this.a = -1;
		this.b = -1;
		this.c = -1;
		this.d = -1;
		//this.e = -1;
		this.f = '-';
		this.g = false;
		this.h = h;
	}
	
	public DummyAction(int a, long b, float c, double d, /*short e,*/ char f, boolean g, String h){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		//this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
	}
}
