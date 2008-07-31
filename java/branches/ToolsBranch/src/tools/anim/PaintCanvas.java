package tools.anim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JPanel;

import org.display.ImageBoard;

public class PaintCanvas extends JPanel {
	private static final long serialVersionUID = -5152687597594926515L;
	
	private static final Color CLEAR = new Color(255,255,255,0);
	
	private ImageBoard board;
	private BufferedImage image;
	
	private Point frame;
	private Dimension size;
	private Dimension tilesize;
	
	private final Stack<BufferedImage> undo;
	private final Stack<BufferedImage> redo;
	
	private volatile BufferedImage buffer;
	private Graphics2D bufferGraphics;
	
	public PaintCanvas(BufferedImage image, Dimension tilesize){
		this.image = image;
		this.tilesize = tilesize;
		board = new ImageBoard(image,tilesize);
		
		frame = new Point(0,0);
		size = new Dimension(image.getWidth(),image.getHeight());
		
		undo = new Stack<BufferedImage>();
		redo = new Stack<BufferedImage>();
		
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
	}
	
	public Graphics2D getDrawingGraphics(){
		if(buffer == null){
			buffer = new BufferedImage(
					tilesize.width,
					tilesize.height,
					BufferedImage.TYPE_4BYTE_ABGR
			);
		}else{
			bufferGraphics.finalize();
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
		
		return bufferGraphics;
	}
	
	public void pushChange(){
		undo.push(
				image.getSubimage(
						0, 
						0, 
						image.getWidth(), 
						image.getHeight()
				)
		);
		
		Graphics2D g = image.createGraphics();
		bufferGraphics.finalize();
		bufferGraphics.dispose();
		
		g.drawImage(buffer, null, frame.x*tilesize.width, frame.y*tilesize.height);
		
		revalidate();
	}
	
	public void undo(){
		redo.push(image.getSubimage(0, 0, image.getWidth(), image.getHeight()));
		
		Graphics2D g = image.createGraphics();
		g.drawImage(undo.pop(), null, 0, 0);
		g.finalize();
		g.dispose();
		
		revalidate();
	}
	
	public void redo(){
		undo.push(image.getSubimage(0, 0, image.getWidth(), image.getHeight()));
		
		Graphics2D g = image.createGraphics();
		g.drawImage(redo.pop(), null, 0, 0);
		g.finalize();
		g.dispose();
		
		revalidate();
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
		bufferGraphics.finalize();
		bufferGraphics.dispose();
		
		g.drawImage(buffer, 0, 0, this);
	}
	
	public ImageBoard getBoard(){
		return board;
	}
}
