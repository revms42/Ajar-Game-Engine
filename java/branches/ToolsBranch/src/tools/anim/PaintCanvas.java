package tools.anim;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
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
	
	private final BufferedImage display;
	private final Graphics2D displayGraphics;
	
	public PaintCanvas(BufferedImage image, Dimension tilesize){
		this.image = image;
		this.tilesize = tilesize;
		board = new ImageBoard(image,tilesize);
		
		frame = new Point(0,0);
		size = new Dimension(image.getWidth(),image.getHeight());
		
		undo = new Stack<BufferedImage>();
		redo = new Stack<BufferedImage>();
		
		display = new BufferedImage(size.width,size.height,image.getType());
		display.setData(image.getData());
		displayGraphics = display.createGraphics();
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

	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(display, frame.x, frame.y, tilesize.width, tilesize.height, this);
	}
	
	public void update(Graphics g){
		super.update(g);
		paint(g);
	}
	
	public ImageBoard getBoard(){
		return board;
	}
}
