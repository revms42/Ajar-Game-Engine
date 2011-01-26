package org.mdmk3.sprint1.step7;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.mdmk3.core.disp2d.image.TilePalette;

public class Step7TilePalette extends TilePalette<Integer> {
	
	private final static Dimension d = new Dimension(64,64);
	
	private final static String mapTilePath = "iteration/org/mdmk3/sprint1/step7/res/Blocks.png";
	private final static String unitTilePath = "iteration/org/mdmk3/sprint1/step7/res/Unit.png";
	
	public Step7TilePalette() throws IOException{
		super();
		File mapTileFile = new File(mapTilePath);
		
		BufferedImage mapTileImage = ImageIO.read(mapTileFile);
		Step7TileSource mapTileSource = new Step7TileSource(mapTileImage,d);
		
		this.addSource(0, mapTileSource);
		
		File unitTileFile = new File(unitTilePath);
		
		BufferedImage unitTileImage = ImageIO.read(unitTileFile);
		Step7TileSource unitTileSource = new Step7TileSource(unitTileImage,d);
		
		this.addSource(1, unitTileSource);
		
	}
	
	@Override
	public Integer getSourceCodeFromImageCode(Integer imageCode) {
		return imageCode < 10 ? 0 : 1;
	}

}
