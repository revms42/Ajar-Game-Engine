package tools.anim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JPanel;

import org.display.ImageBoard;

import tools.anim.view.IZoom;

public class PaintCanvas extends JPanel implements IZoom {
	private static final long serialVersionUID = -5152687597594926515L;
	
	private ImageBoard board;
	private BufferedImage image;
	
	private Point frame;
	private Dimension size;
	private Dimension tilesize;
	
	private final Stack<int[]> undo;
	private final Stack<int[]> redo;
	
	private volatile BufferedImage buffer;
	private Graphics2D bufferGraphics;
	
	private int zoomLevel;
	private AffineTransformOp rescaler;
	
	public PaintCanvas(BufferedImage image, Dimension tilesize){
		this.image = image;
		this.tilesize = tilesize;
		board = new ImageBoard(image,tilesize);
		
		frame = new Point(0,0);
		size = new Dimension(image.getWidth(),image.getHeight());
		zoomLevel = 1;
		rescaler = new AffineTransformOp(
				AffineTransform.getScaleInstance(zoomLevel,zoomLevel),
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR
		);
		
		undo = new Stack<int[]>();
		redo = new Stack<int[]>();
		
		buffer = new BufferedImage(tilesize.width,tilesize.height,image.getType());
		bufferGraphics = buffer.createGraphics();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		this.board = new ImageBoard(image,tilesize);
		
		this.frame = new Point(0,0);
		this.size = new Dimension(image.getWidth(),image.getHeight());
		
		repaint();
	}
	
	public Graphics2D getDrawingGraphics(){
		rebuffer();
		
		return bufferGraphics;
	}
	
	private void rebuffer(){
		if(buffer == null){
			buffer = new BufferedImage(
					tilesize.width,
					tilesize.height,
					BufferedImage.TYPE_4BYTE_ABGR
			);
		}else{
			bufferGraphics.dispose();
		}
		
		bufferGraphics = buffer.createGraphics();
		bufferGraphics.setBackground(Color.WHITE);
		bufferGraphics.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
		
		bufferGraphics.drawImage(
				image.getSubimage(
						frame.x*tilesize.width, 
						frame.y*tilesize.height, 
						image.getWidth(), 
						image.getHeight()
				),null,0,0
		);
	}
	
	public void pushChange(){
		undo.push(
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth())
		);
		
		Graphics2D g = image.createGraphics();
		bufferGraphics.finalize();
		bufferGraphics.dispose();
		
		g.drawImage(buffer, null, frame.x*tilesize.width, frame.y*tilesize.height);
		
		rebuffer();
		updateUI();
	}
	
	public void undo(){
		if(!undo.isEmpty()){
			redo.push(
					image.getRGB(
						0, 
						0, 
						image.getWidth(), 
						image.getHeight(), 
						null, 
						0, 
						image.getWidth()
					)
				);
				
				image.setRGB(
						0, 
						0, 
						image.getWidth(), 
						image.getHeight(), 
						undo.pop(), 
						0, 
						image.getWidth()
				);
				
				rebuffer();
				updateUI();
		}
	}
	
	public void redo(){
		if(!redo.isEmpty()){
			undo.push(
					image.getRGB(
						0, 
						0, 
						image.getWidth(), 
						image.getHeight(), 
						null, 
						0, 
						image.getWidth()
					)
				);
				
				image.setRGB(
						0, 
						0, 
						image.getWidth(), 
						image.getHeight(), 
						redo.pop(), 
						0, 
						image.getWidth()
				);
				
				rebuffer();
				updateUI();
		}
	}

	public Point getFrame() {
		return frame;
	}

	public void setFrame(Point frame) {
		int x = this.frame.x;
		int y = this.frame.y;
		
		if(frame.x >= 0 && frame.x*tilesize.width <= size.width){
			x = frame.x;
		}
		if(frame.y >= 0 && frame.y*tilesize.height <= size.height){
			y = frame.y;
		}
		
		this.frame.x = x;
		this.frame.y = y;
	}
	//TODO: Figure out why this doesn't display between pushChanges.
	protected void paintComponent(Graphics g){
		bufferGraphics.dispose();
		
		if(rescaler.getTransform().getScaleX() != zoomLevel){
			rescaler = new AffineTransformOp(
					AffineTransform.getScaleInstance(zoomLevel,zoomLevel),
					AffineTransformOp.TYPE_NEAREST_NEIGHBOR
			);
		}
		
		Graphics2D g2 = (Graphics2D) g;
		//g.drawImage(buffer, 0, 0, this);
		g2.drawImage(buffer, rescaler, 0, 0);
	}
	
	public ImageBoard getBoard(){
		return board;
	}

	@Override
	public int getZoomLevel() {
		return zoomLevel;
	}

	@Override
	public void setZoomLevel(int level) {
		//TODO: Need more intelligent handling of this.
		if(level >= 1 && level <= 10){
			zoomLevel = level;
			repaint();
		}
	}

	@Override
	public void zoomIn() {
		//TODO: Need more intelligent handling of this.
		if(zoomLevel < 10){
			zoomLevel++;
			repaint();
		}
	}

	@Override
	public void zoomOut() {
		//TODO: Need more intelligent handling of this.
		if(zoomLevel > 2){
			zoomLevel--;
			repaint();
		}
	}
}
