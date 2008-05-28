package org.display;

import java.awt.Graphics2D;

public interface IDisplayFactory<I,K> {

	public ImagePalette<I> getImagePalette();
	public void setImagePalette(ImagePalette<I> palette);
	
	public void display(IDisplayable<I,K> character, Graphics2D stage);
}
