package org.mdmk3.core.loader;

import java.util.Vector;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

public abstract class TileMapLoader<D,A extends Attributes> {

	private Converter<D,A> converter;
	
	public Converter<D, A> getConverter() {
		return converter;
	}

	public void setConverter(Converter<D, A> converter) {
		this.converter = converter;
	}

	public Node<A> loadFromArray(D[] array, int scansize){
		Vector<Node<A>> arrayNodes = new Vector<Node<A>>();
		for(int y = 0; y < array.length/scansize; y++){
			for(int x = 0; x < scansize; x++){
				D i = array[(scansize * y) + x];
				
				Node<A> node = converter.toNode(i);
				
				initializePosition(node,x,y);
				
				arrayNodes.add(node);
			}
		}
		
		return structureMap(arrayNodes,scansize);
	}
	
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
	
	
	public abstract void initializePosition(Node<A> node, int x, int y);
	
	/*
	 * [1][2]
	 * [3][4]
	 */
	public abstract Node<A> createDomainNode(Node<A> first, Node<A> second, Node<A> third, Node<A> fourth);
}
