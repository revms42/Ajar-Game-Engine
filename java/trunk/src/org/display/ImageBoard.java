package org.display;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class ImageBoard {

	private final BufferedImage board;
	private final Dimension tilesize;
	
	public ImageBoard(BufferedImage board, Dimension tilesize){
		this.board = board;
		this.tilesize = tilesize;
	}
	
	public BufferedImage getSubImage(int x, int y){
		return board.getSubimage(x*tilesize.width, y*tilesize.height, tilesize.width, tilesize.height);
	}
	
	public Dimension getTileSize(){
		return tilesize;
	}
}
