package org.display;

import java.awt.Point;
import java.awt.image.BufferedImageOp;

import org.interaction.IEntity;

public interface IDisplayContext<I,K> {

	public I getBoard(IEntity<K> subject);
	public Point getTile(IEntity<K> subject);
	public Point getPosition(IEntity<K> subject);
	public BufferedImageOp getTransform(IEntity<K> subject);
	
	public ImageOpPalette<?> getImageOpPalette();
	public void setImageOpPalette(ImageOpPalette<K> palette);
}
