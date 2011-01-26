package org.mdmk3.core.disp2d.image;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Vector;

public abstract class CachingImageTileSource<I> extends BufferedImageTileSource<I> {

	private final Vector<I> indicies;
	private final Vector<BufferedImage> images;
	private final int cacheSize;
	
	public CachingImageTileSource(BufferedImage image, Dimension tileSize, int cacheSize) {
		super(image, tileSize);
		this.cacheSize = cacheSize;
		indicies = new Vector<I>(cacheSize);
		images = new Vector<BufferedImage>(cacheSize);
	}

	@Override
	public BufferedImage getTile(I imageCode) {
		BufferedImage bi = null;
		int i = indicies.indexOf(imageCode);
		
		if(i > 0){
			bi = images.get(i);
			
			if(bi != null){
				indicies.remove(i);
				images.remove(i);
			}else{
				return null;
			}
		}else{
			bi = super.getTile(imageCode);
			
			if(bi != null){
				if(indicies.size() > cacheSize){
					indicies.remove(cacheSize - 1);
					images.remove(cacheSize - 1); 
				}
			}else{
				return null;
			}
		}
		
		indicies.insertElementAt(imageCode, 0);
		images.insertElementAt(bi, 0);
		
		return bi;
	}
}
