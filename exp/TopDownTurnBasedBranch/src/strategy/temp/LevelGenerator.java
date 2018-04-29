package strategy.temp;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import strategy.display.map.BattleMapContext;

public class LevelGenerator {

	/**
	 * @deprecated
	 * @param path
	 * @param size
	 * @param tileChoices
	 */
	public static void generateLevel(String path, Dimension size, Dimension tileChoices){
		BufferedImage img = new BufferedImage(size.width,size.height,BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int x = 0; x < size.width; x++){
			for(int y = 0; y < size.height; y++){
				//No red part unless there's multiple tilemaps.
				int gpart = ((int)Math.floor(Math.random() * tileChoices.width)) <<  BattleMapContext.greenShift;
				int bpart = ((int)Math.floor(Math.random() * tileChoices.height)) << BattleMapContext.blueShift;
				
				img.setRGB(x, y, (gpart + bpart));
			}
		}
		
		try {
			ImageIO.write(img,"png",new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
