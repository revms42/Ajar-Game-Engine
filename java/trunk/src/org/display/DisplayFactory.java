package org.display;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImageOp;

public class DisplayFactory<I,K> implements IDisplayFactory<I,K> {

	private volatile ImagePalette<I> palette;
	
	private IDisplayContext<I,K> context;
	private I board;
	private BufferedImageOp op;
	private Point tile;
	private Point position;
	
	public DisplayFactory(ImagePalette<I> palette){
		this.palette = palette;
	}
	
	@Override
	public synchronized void display(IDisplayable<I,K> subject, Graphics2D stage) {
		if(stage != null){
			context = subject.getDisplayContext();
			
			board = context.getBoard(subject);
			op = context.getTransform(subject);
			tile = context.getTile(subject);
			position = context.getPosition(subject);
			
			stage.drawImage(
					palette.get(board).getSubImage(tile.x, tile.y), 
					op, 
					position.x, 
					position.y
			);
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
