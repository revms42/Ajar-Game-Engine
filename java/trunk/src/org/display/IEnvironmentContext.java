package org.display;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public interface IEnvironmentContext<I> {
	
	/**
	 * Returns the key in the given environment's <code>ImagePalette</code> for the <code>ImageBoard</code> that contains
	 * the tile images for the tiles in the given environment at the given depth.
	 * @param subject The environment in question.
	 * @param depth The display depth.
	 * @return The key of the <code>ImageBoard</code>
	 * @see ImagePalette
	 * @see ImageBoard
	 * @see ITileFactory
	 */
	public I getBoard(IEnvironment<I,?> subject, int depth);
	
	/**
	 * Gets a <code>BufferedImageOp</code> representing any necessary transformation to be applied to the tile at the given position.
	 * @param subject The environment in question.
	 * @param p The position of the tile.
	 * @param depth The display depth of the tile.
	 * @return The <code>BufferedImageOp</code> to apply to the tile.
	 * @see ITileFactory
	 * @see IEnvironmentContext#getPoint(IEnvironment, Point, int);
	 */
	public BufferedImageOp getPointTransform(IEnvironment<I,?> subject, Point p, int depth);
	
	/**(determined by the <code>getBoard(IEnvironment, int)</code> method)
	 * Gets a series of <code>BufferedImageOp</code>s representing the necessary transformations to be applied to the tiles within the given window.
	 * <p>
	 * Note: The actual range of tiles returned is determined by the <code>getTilesInWindow(IEnvironment, Rectangle, int)</code> method.
	 * @param subject The environment in question.
	 * @param r The window onto the environment (in display space).
	 * @param depth The display depth for the range.
	 * @return An array of <code>BufferedImageOp</code>'s for the tiles in the window.
	 * @see ITileFactory
	 * @see IEnvironmentContext#getRange(IEnvironment, Rectangle, int)
	 * @see IEnvironmentContext#getTilesInWindow(IEnvironment, Rectangle, int)
	 */
	public BufferedImageOp[] getRangeTransform(IEnvironment<I,?> subject, Rectangle r, int depth);
	
	/**
	 * Gets all of the <code>BuffreedImageOp</code>s for the tiles in the environment at the given depth.
	 * @param subject The environment in question.
	 * @param depth The display depth for the map.
	 * @return An array of <code>BufferedImageOp</code>'s for all the tiles in the environment at the given depth.
	 * @see ITileFactory
	 * @see IEnvironmentContext#getAll(IEnvironment, int)
	 */
	public BufferedImageOp[] getAllTransform(IEnvironment<I,?> subject, int depth);
	
	/**
	 * Gets the position on the <code>ImageBoard</code> (determined by the <code>getBoard(IEnvironment, int)</code> method)
	 * of the tile at the position <code>p</code> in the environment at the given depth.
	 * @param subject The environment in question.
	 * @param p The position of the tile in the environment.
	 * @param depth The display depth of the tile.
	 * @return The position on the <code>ImageBoard</code> of the tile.
	 * @see IEnvironmentContext#getBoard(IEnvironment, int) 
	 */
	public Point getPoint(IEnvironment<I,?> subject, Point p, int depth);
	
	/**
	 * Gets a series of <code>Point</code>s representing the positions on the <code>ImageBoard</code> (determined by the <code>getBoard(IEnvironment, int)</code> method)
	 * of the tiles to be displayed within the given window.
	 * <p>
	 * Note: The actual range of tiles returned is determined by the <code>getTilesInWindow(IEnvironment, Rectangle, int)</code> method.
	 * @param subject The environment in question.
	 * @param r The window onto the environment (in display space).
	 * @param depth The display depth for the range.
	 * @return An array of <code>Point</code>s representing the positions of the tiles on the <code>ImageBoard</code> in display order.
	 * @see IEnvironmentContext#getRange(IEnvironment, Rectangle, int)
	 * @see IEnvironmentContext#getBoard(IEnvironment, int)
	 */
	public Point[] getRange(IEnvironment<I,?> subject, Rectangle r, int depth);
	
	/**
	 * Gets all of the <code>Point</code>s representing the positions of all of the tiles in the environment on the <code>ImageBoard</code> 
	 * (determined by the <code>getBoard(IEnvironment, int)</code> method).
	 * @param subject The environment in question.
	 * @param depth The display depth for the environment.
	 * @return An array of <code>Point</code>s representing the positions on the <code>ImageBoard</code> of all the tiles in the environment at the given depth.
	 * @see IEnvironmentContext#getBoard(IEnvironment, int)
	 */
	public Point[] getAll(IEnvironment<I,?> subject, int depth);
	
	/**
	 * Gets the <code>ImageOpPalette</code> used in this context.
	 * @return The <code>ImageOpPalette</code> of this context.
	 */
	public ImageOpPalette<?> getImageOpPalette();
	
	/**
	 * Sets the <code>ImageOpPalette</code> to be used in this context.
	 * @param palette The <code>ImageOpPalette</code> to be used in this context.
	 */
	public void setImageOpPalette(ImageOpPalette<?> palette);
	
	/**
	 * Returns the number of tiles that will fit in the window <code>r</code>.
	 * @param subject The environment in question.
	 * @param r The window onto the environment (in display space).
	 * @param depth The display depth for the environment.
	 * @return A <code>Dimension</code> representing the number of tiles that will be needed to fill the screen.
	 * @see javax.swing.JViewport#getViewRect()
	 */
	public Dimension getTilesInWindow(IEnvironment<I,?> subject, Rectangle r, int depth);
}
