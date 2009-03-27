package org.display;

import java.awt.Point;
import java.awt.image.BufferedImageOp;

import org.interaction.IEntity;

public interface IDisplayContext<I,K> {

	public I getBoard(IDisplayable<I,K> subject);
	public Point getTile(IDisplayable<I,K> subject);
	public Point getPosition(IDisplayable<I,K> subject);
	public BufferedImageOp getTransform(IDisplayable<I,K> subject);
	
	public ImageOpPalette<?> getImageOpPalette();
	public void setImageOpPalette(ImageOpPalette<K> palette);
}
