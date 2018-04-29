/*
 * This file is part of Macchiato Doppio Java Game Framework.
 * Copyright (C) Jan 5, 2011 Matthew Stockbridge
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
 * MDMk3
 * org.mdmk3.core.loader
 * TileMapLoader.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the Mark 3 effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.mdmk3.core.loader;

import java.util.Vector;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

/**
 * This class provides useful logic for creating optimized maps from an array of data.
 * <p>
 * Currently, this is implemented in such a way that makes it only practically useful to 2D games.
 * More specifically, those that use tile maps for their terrain.
 * <p>
 * The general way in which this is accomplished is through {@link #createDomainNode(Node, Node, Node, Node)}, which 
 * takes four nodes that share a corner and generates a parent node to contain them. Although there are no restrictions
 * on how the parent, or "domain", node is created, the general purpose is to have a node that can be used for culling (see
 * {@link #createDomainNode(Node, Node, Node, Node) createDomainNode} for more info).
 * @author revms
 * @since 0.0.0.156
 * @TODO Determine if this should be part of a utility package.
 */
public abstract class TileMapLoader<D,A extends Attributes> {
	private Converter<D,A> converter;
	
	/**
	 * Gets the <code>Converter</code> being used to convert tile data into nodes.
	 * @return the converter currently being used to convert tile data.
	 */
	public Converter<D, A> getConverter() {
		return converter;
	}

	/**
	 * Sets the <code>Converter</code> used to convert tile data into nodes.
	 * @param converter the converter to use to create nodes.
	 */
	public void setConverter(Converter<D, A> converter) {
		this.converter = converter;
	}

	/**
	 * Takes an array of convertible data and the width of the map (in tiles) that it represents
	 * and returns a single domain node with all of the nodes specified by the data housed within
	 * it in a 2D binary tree arrangement.
	 * @param array an array of convertible data representing a map.
	 * @param scansize the width of the map in tiles.
	 * @return the root of the domain node binary tree representing the map.
	 * @see #structureMap(Vector, int)
	 */
	public Node<A> loadFromArray(D[] array, int scansize){
		Vector<Node<A>> arrayNodes = new Vector<Node<A>>();
		for(int y = 0; y < array.length/scansize; y++){
			for(int x = 0; x < scansize; x++){
				D i = array[(scansize * y) + x];
				
				Node<A> node = converter.toNode(i);
				
				if(node != null){
					if(!(node instanceof NullTileNode)){
						initializePosition(node,x,y);
					}
					arrayNodes.add(node);
				}
			}
		}
		
		return structureMap(arrayNodes,scansize);
	}
	
	/**
	 * Structures the nodes provided into a tree hierarchy with one root node
	 * that will cause {@link org.mdmk3.core.cull.CullingSurface#isInRange(Node)} to
	 * return <code>true</code> if any of the supplied nodes in the <code>Vector</code>
	 * would cause it to return <code>true</code>.
	 * @param arrayNodes the array of nodes representing the tile map.
	 * @param scansize the width of a single row of the map, in tiles.
	 * @return a root domain node containing a 2D binary tree of all the supplied nodes.
	 * @see #createDomainNode(Node, Node, Node, Node)
	 * @see #initializePosition(Node, int, int)
	 */
	protected Node<A> structureMap(Vector<Node<A>> arrayNodes, int scansize){
		/*
		 * ------+------+---
		 * 0  1  | 2  3 |4
		 * 5  6  | 7  8 |9
		 * ------+------+---
		 * 10 11 |12 13 |14
		 * 15 16 |17 18 |19
		 * ------+------+---
		 * 20 21 |22 23 |24
		 * 
		 * Group 0: (5*0)+0= 0, (5*0)+1= 1, (5*1)+0= 5, (5*1)+1= 6
		 * Group 1: (5*0)+2= 2, (5*0)+3= 3, (5*1)+2= 7, (5*1)+3= 8
		 * Group 2: (5*0)+4= 4, !x= null, (5*1)+4= 9, !x= null
		 * Group 3: (5*2)+0= 10, (5*2)+1= 11, (5*3)+0= 15, (5*3)+1= 16
		 * Group 4: (5*2)+2= 12, (5*2)+3= 13, (5*3)+2= 17, (5*3)+3= 18
		 * Group 5: (5*2)+4= 14, !x= null, (5*2)+4= 19, !x= null
		 * Group 6: (5*4)+0= 20, (5*4)+1= 21, !y= null, !y= null
		 * Group 7: (5*4)+2= 22, (5*4)+3= 23, !y= null, !y= null
		 * Group 8: (5*4)+4= 24, !x= null, !y= null, !y&!x= null
		 * 
		 * G0 G1 G2
		 * G3 G4 G5
		 * G6 G7 G8
		 */
		Vector<Node<A>> domainNodes = new Vector<Node<A>>();
		for(int y = 0; y < arrayNodes.size()/scansize; y = y + 2){
			for(int x = 0; x < scansize; x = x + 2){
				Node<A> first = arrayNodes.get((scansize * y) + x);
				Node<A> second = x < (scansize-1) ? 
						arrayNodes.get((scansize * y) + (x+1)) : 
						null;
				Node<A> third = y < ((arrayNodes.size()/scansize) - 1) ? 
						arrayNodes.get((scansize * (y+1)) + x) : 
						null;
				Node<A> fourth = (x < (scansize-1)) && (y < ((arrayNodes.size()/scansize) - 1)) ? 
						arrayNodes.get((scansize * (y+1)) + (x+1)) : 
						null;
				domainNodes.add(createDomainNode(first,second,third,fourth));
			}
		}
		
		if(domainNodes.size() == 1) return domainNodes.firstElement();
		
		int newScanSize = scansize/2;
		newScanSize = scansize % 2 > 0 ? newScanSize+1 : newScanSize;
		
		return structureMap(domainNodes,newScanSize);
	}
	
	/**
	 * Initializes the position of the given node to the specified x and y coordinates.
	 * @param node the node whose position is to be initialized.
	 * @param x the x coordinate of the node.
	 * @param y the y coordinate of the node.
	 */
	public abstract void initializePosition(Node<A> node, int x, int y);
	
	/**
	 * Creates a domain node containing the four specified nodes that share a common corner.
	 * <p>
	 * In general this method should return a node who's area, from a culling point of view, 
	 * covers all of the nodes "underneath" it.
	 * Specifically whatever bounds that it has should fulfill the following:
	 * <p>
	 * <code>if(CullingSurface.isInRange(first) || CullingSurface.isInRange(second) || CullingSurface.isInRange(third)
	 * || CullingSurface.isInRange(fourth)) return true;</code>
	 * <p>
	 * For example, if the locations of the nodes are {0,0}, {1,0}, {0,1}, and {1,1} (and each has a width and height of 1),
	 * than the domain node would have a location of {0,0} and a width and height of 2.
	 * <p>
	 * The nodes are expected to be passed in with the relative positions:
	 * <p>
	 * <code>[1][2]</code><p>
	 * <code>[3][4]</code>
	 * <p>
	 * Note that the typical use of this method is to set up node trees, so that culling can rapidly exclude any nodes that
	 * are not relevant given the current game state.
	 * @TODO figure out what the big-o notation time is for the culling and execution of these trees vs asking all nodes.
	 * @param first the node in the relative top-left position.
	 * @param second the node in the relative top-right position.
	 * @param third the node in the relative bottom-left position.
	 * @param fourth the node in the relative bottom-right position.
	 * @return a domain node containing all four of the supplied nodes.
	 */
	public abstract Node<A> createDomainNode(Node<A> first, Node<A> second, Node<A> third, Node<A> fourth);
}
