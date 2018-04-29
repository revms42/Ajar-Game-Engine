package org.mdmk3.sprint1.step8;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.mdmk3.core.disp2d.image.TilePalette;

public class Step8TilePalette extends TilePalette<Integer> {
	
	private final static Dimension d = new Dimension(64,64);
	
	private final static String mapTilePath = "iteration/org/mdmk3/sprint1/step8/res/Blocks.png";
	private final static String unitTilePath = "iteration/org/mdmk3/sprint1/step8/res/BallAnim.png";
	
	public Step8TilePalette() throws IOException{
		super();
		File mapTileFile = new File(mapTilePath);
		
		BufferedImage mapTileImage = ImageIO.read(mapTileFile);
		Step8TileSource mapTileSource = new Step8TileSource(mapTileImage,d);
		
		this.addSource(0, mapTileSource);
		
		File unitTileFile = new File(unitTilePath);
		
		BufferedImage unitTileImage = ImageIO.read(unitTileFile);
		Step8TileSource unitTileSource = new Step8TileSource(unitTileImage,d);
		
		this.addSource(1, unitTileSource);
		
	}
	
	@Override
	public Integer getSourceCodeFromImageCode(Integer imageCode) {
		return imageCode < 10 ? 0 : 1;
	}

}
