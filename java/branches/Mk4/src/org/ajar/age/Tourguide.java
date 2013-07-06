/*
 * This file is part of Ajar Game Engine
 * Copyright (C) Jun 26, 2013 Matthew Stockbridge
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
 * org.ajar.age
 * Tourguide.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 4 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */

/**
 * @author mstockbr
 */
package org.ajar.age;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;


/**
 * @author mstockbr
 *
 */

public class Tourguide<A extends Attributes> implements Visitor<A> {

	private Visitor<A> leader;
	private final Vector<Visitor<A>> visitors;
	
	//@SafeVarargs
	public Tourguide(Visitor<A> leader, Visitor<A>... visitors){
		this.leader = leader;
		this.visitors = new Vector<Visitor<A>>();
		this.visitors.addAll(Arrays.asList(visitors));
	}
	
	@Override
	public boolean isInView(Node<A> node) {
		return leader.isInView(node);
	}

	@Override
	public void visit(Node<A> node) {
		leader.visit(node);
		for(Visitor<A> visitor : visitors){
			visitor.visit(node);
		}
	}

	@Override
	public void process() {
		leader.process();
		for(Visitor<A> visitor : visitors){
			visitor.process();
		}
	}

	public int size() {
		return visitors.size();
	}

	public Enumeration<Visitor<A>> elements() {
		return visitors.elements();
	}

	public boolean contains(Object o) {
		return visitors.contains(o);
	}

	public int indexOf(Object o) {
		return visitors.indexOf(o);
	}

	public Visitor<A> get(int index) {
		return visitors.get(index);
	}

	public boolean add(Visitor<A> e) {
		return visitors.add(e);
	}

	public boolean remove(Object o) {
		return visitors.remove(o);
	}

	public Visitor<A> remove(int index) {
		return visitors.remove(index);
	}

	public void clear() {
		visitors.clear();
	}

	public Visitor<A> getLeader() {
		return leader;
	}

	public void setLeader(Visitor<A> leader) {
		this.leader = leader;
	}

}

