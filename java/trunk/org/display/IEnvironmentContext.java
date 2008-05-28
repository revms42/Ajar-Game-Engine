package org.display;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public interface IEnvironmentContext<I> {
	
	public I getBoard(IEnvironment<I,?> subject, int depth);
	
	public BufferedImageOp getPointTransform(IEnvironment<I,?> subject, Point p, int depth);
	public BufferedImageOp[] getRangeTransform(IEnvironment<I,?> subject, Rectangle r, int depth);
	public BufferedImageOp[] getAllTransform(IEnvironment<I,?> subject, int depth);
	
	public Point getPoint(IEnvironment<I,?> subject, Point p, int depth);
	public Point[] getRange(IEnvironment<I,?> subject, Rectangle r, int depth);
	public Point[] getAll(IEnvironment<I,?> subject, int depth);
	
	public ImageOpPalette<?> getImageOpPalette();
	public void setImageOpPalette(ImageOpPalette<?> palette);
	
	public Dimension getTilesInWindow(Rectangle r);
}
