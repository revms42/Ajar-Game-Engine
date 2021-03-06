package org.mdmk3.sprint1.step7;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import org.mdmk3.core.disp2d.image.BufferedImageTileSource;

public class Step7TileSource extends BufferedImageTileSource<Integer> {

	public Step7TileSource(BufferedImage image, Dimension tileSize) {
		super(image, tileSize);
	}

	@Override
	protected Point tileIndexForCode(Integer imageCode) {
		int xcode = imageCode >= 10 ? imageCode - 10 : imageCode;
		return new Point(xcode,0);
	}

}
