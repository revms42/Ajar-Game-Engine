package org.display;

import java.awt.Dimension;
import java.util.HashMap;

public class ImagePalette<I> extends HashMap<I,ImageBoard> {
	private static final long serialVersionUID = 2994088437176668197L;

	public Dimension getTileSize(I key){
		if(this.containsKey(key)){
			return this.get(key).getTileSize();
		}else{
			return null;
		}
	}
}
