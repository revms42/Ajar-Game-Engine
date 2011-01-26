package org.mdmk3.core.disp2d.image;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public abstract class BufferedImageTileSource<I> implements TileSource<I> {
	private final BufferedImage image;
	private final Dimension tileSize;
	
	public BufferedImageTileSource(BufferedImage image, Dimension tileSize){
		this.image = image;
		this.tileSize = tileSize;
	}
	
	@Override
	public void drawImage(I imageCode, Graphics2D g2, AffineTransform at, BufferedImageOp op) {
		if(op != null){
			g2.drawRenderedImage(op.filter(getTile(imageCode),null), at);
		}else{
			g2.drawRenderedImage(getTile(imageCode), at);
		}
		
	}

	@Override
	public BufferedImage getTile(I imageCode) {
		Point p = tileIndexForCode(imageCode);
		return image.getSubimage(p.x*tileWidth(), p.y*tileHeight(), tileWidth(), tileHeight());
	}
	
	protected abstract Point tileIndexForCode(I imageCode);

	@Override
	public int tileWidth() {
		return tileSize.width;
	}

	@Override
	public int tileHeight() {
		return tileSize.height;
	}

	@Override
	public int numXTiles() {
		return image.getWidth() / tileSize.width;
	}

	@Override
	public int numYTiles() {
		return image.getHeight() / tileSize.height;
	}

}
