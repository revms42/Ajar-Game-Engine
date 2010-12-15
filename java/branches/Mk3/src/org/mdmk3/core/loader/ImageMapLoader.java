package org.mdmk3.core.loader;

import java.awt.image.BufferedImage;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

public abstract class ImageMapLoader<A extends Attributes> extends TileMapLoader<Integer,A> {

	public Node<A> loadFromImage(BufferedImage bi){
		int scansize = bi.getWidth();
		int height = bi.getHeight();
		int[] data = bi.getRGB(0, 0, scansize, height, null, 0, scansize);
		Integer[] array = new Integer[data.length];
		
		for(int i = 0; i < data.length; i++) array[i] = data[i];
		
		return loadFromArray(array, scansize);
	}
}
