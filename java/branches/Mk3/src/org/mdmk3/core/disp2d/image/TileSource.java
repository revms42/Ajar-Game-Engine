package org.mdmk3.core.disp2d.image;

public interface TileSource<I> extends TileProvider<I>{
	
	/**
	 * The width of a tile on this TileSource.
	 * @return
	 */
	public int tileWidth();
	
	/**
	 * The height of a tile on this TileSource.
	 * @return
	 */
	public int tileHeight();
	
	/**
	 * The number of tiles in the X direction for this TileSource.
	 * @return
	 */
	public int numXTiles();
	
	/**
	 * The number of tiles in the Y direction for this TileSource.
	 * @return
	 */
	public int numYTiles();
}
