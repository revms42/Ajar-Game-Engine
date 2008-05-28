package org.display;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public class TileFactory<I> implements ITileFactory<I> {

	private volatile ImagePalette<I> palette;
	
	private volatile IEnvironmentContext<I> context;
	private volatile Dimension dimtiles;
	private volatile Dimension tilesize;
	private volatile I board;
	private volatile BufferedImageOp[] ops;
	private volatile Point[] tiles;
	
	private volatile int refnum;
	
	public TileFactory(ImagePalette<I> palette){
		this.palette = palette;
	}
	
	@Override
	public synchronized void displayAll(IEnvironment<I,?> environment, Rectangle window,
			Graphics2D stage) {
		for(int depth = 0; depth < environment.getMaps().size(); depth++){
			displayDepth(environment,depth,window,stage);
		}
	}

	@Override
	public synchronized void displayDepth(IEnvironment<I,?> environment, int depth,
			Rectangle window, Graphics2D stage) {
		if(stage != null){
			context = environment.getEnvironmentContext();
			
			board = context.getBoard(environment,depth);
			tiles = context.getRange(environment, window, depth);
			dimtiles = context.getTilesInWindow(window);
			ops = context.getRangeTransform(environment, window, depth);
			
			tilesize = palette.getTileSize(board);
			
			for(int x = 0; x < dimtiles.width; x++){
				for(int y = 0; y < dimtiles.height; y++){
					
					refnum = x + (dimtiles.width * y);
					
					stage.drawImage(
							palette.get(board).getSubImage(tiles[refnum].x, tiles[refnum].y), 
							ops[refnum], 
							window.x + (tilesize.width * x), 
							window.y + (tilesize.height * y)
					);
				}
			}
		}
	}

	@Override
	public ImagePalette<I> getImagePalette() {
		return palette;
	}

	@Override
	public synchronized void setImagePalette(ImagePalette<I> palette) {
		if(palette != null){
			this.palette = palette;
		}
	}

}
