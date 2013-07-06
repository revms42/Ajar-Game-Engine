package org.mdmk3.sprint1.step9;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import org.mdmk3.core.disp2d.image.BufferedImageTileSource;

public class Step9TileSource extends BufferedImageTileSource<Integer> {

	public Step9TileSource(BufferedImage image, Dimension tileSize) {
		super(image, tileSize);
	}

	@Override
	protected Point tileIndexForCode(Integer imageCode) {
		int row = 0;
		if(imageCode >= 100){
			row = 1;
			imageCode = imageCode - 100;
		}
		int xcode = imageCode >= 10 ? imageCode - 10 : imageCode;
		return new Point(xcode,row);
	}

}
