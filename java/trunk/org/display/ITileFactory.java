package org.display;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface ITileFactory<I> {


	public ImagePalette<I> getImagePalette();
	public void setImagePalette(ImagePalette<I> palette);
	
	public void displayAll(
			IEnvironment<I,?> environment, 
			Rectangle window, 
			Graphics2D stage
	);
	
	public void displayDepth(
			IEnvironment<I,?> environment, 
			int depth,
			Rectangle window, 
			Graphics2D stage
	);
}
