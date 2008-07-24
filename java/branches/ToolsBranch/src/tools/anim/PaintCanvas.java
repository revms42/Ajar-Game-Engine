package tools.anim;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

import org.display.ImageBoard;

public class PaintCanvas extends Canvas {
	private static final long serialVersionUID = -5152687597594926515L;

	private Paint penForeColor;
	private Paint penBackColor;
	
	private ImageBoard board;
	private BufferedImage image;
	
	private Point frame;
	private Dimension size;
	private Dimension tilesize;
	
	private final Stack<BufferedImage> undo;
	private final Stack<BufferedImage> redo;
	
	private final BufferedImage overlay;
	public Graphics2D penGraphics;
	
	public PaintCanvas(BufferedImage image, Dimension tilesize){
		this.image = image;
		this.tilesize = tilesize;
		board = new ImageBoard(image,tilesize);
		
		frame = new Point(0,0);
		size = new Dimension(image.getWidth(),image.getHeight());
		
		undo = new Stack<BufferedImage>();
		redo = new Stack<BufferedImage>();
		
		overlay = new BufferedImage(tilesize.width,tilesize.height,image.getType());
		penGraphics = overlay.createGraphics();
	}
	
	public Paint getPenForeColor() {
		return penForeColor;
	}

	public void setPenForeColor(Paint penForeColor) {
		this.penForeColor = penForeColor;
	}

	public Paint getPenBackColor() {
		return penBackColor;
	}

	public void setPenBackColor(Paint penBackColor) {
		this.penBackColor = penBackColor;
	}
	
	public Color getTransColor() {
		return this.getBackground();
	}
	
	public void setTransColor(Color transColor) {
		this.setBackground(transColor);
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
	
	public void pushChange(){
		undo.push(image.getSubimage(0, 0, image.getWidth(), image.getHeight()));
		
		Graphics2D g = image.createGraphics();
		penGraphics.finalize();
		penGraphics.dispose();
		
		g.drawImage(overlay, null, frame.x*tilesize.width, frame.y*tilesize.height);
		g.finalize();
		g.dispose();
		
		penGraphics = overlay.createGraphics();
		penGraphics.clearRect(0, 0, overlay.getWidth(), overlay.getHeight());
	}
	
	public void undo(){
		redo.push(image.getSubimage(0, 0, image.getWidth(), image.getHeight()));
		
		Graphics2D g = image.createGraphics();
		g.drawImage(undo.pop(), null, 0, 0);
		g.finalize();
		g.dispose();
		
		penGraphics.clearRect(0, 0, overlay.getWidth(), overlay.getHeight());
	}
	
	public void redo(){
		undo.push(image.getSubimage(0, 0, image.getWidth(), image.getHeight()));
		
		Graphics2D g = image.createGraphics();
		g.drawImage(redo.pop(), null, 0, 0);
		g.finalize();
		g.dispose();
		
		penGraphics.clearRect(0, 0, overlay.getWidth(), overlay.getHeight());
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

	//TODO: Need to composite the two images.
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(
				image, 
				frame.x*tilesize.width, 
				frame.y*tilesize.height, 
				tilesize.width, 
				tilesize.height, 
				this);
		g.drawImage(
				overlay, 
				0, 
				0, 
				tilesize.width, 
				tilesize.height, 
				this);
	}
	
	public void update(Graphics g){
		super.update(g);
		paint(g);
	}
	
	public ImageBoard getBoard(){
		return board;
	}
}
