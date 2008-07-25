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
	
	private BufferedImage buffer;
	private Graphics2D bufferGraphics;
	
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
		penGraphics.setBackground(CLEAR);
		penGraphics.clearRect(0, 0, 400, 400);
		penGraphics.finalize();
		penGraphics.dispose();
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
		penGraphics.setBackground(CLEAR);
		penGraphics.clearRect(0, 0, overlay.getWidth(), overlay.getHeight());
		revalidate();
	}
	
	public void undo(){
		redo.push(image.getSubimage(0, 0, image.getWidth(), image.getHeight()));
		
		Graphics2D g = image.createGraphics();
		g.drawImage(undo.pop(), null, 0, 0);
		g.finalize();
		g.dispose();
		
		penGraphics.setBackground(CLEAR);
		penGraphics.clearRect(0, 0, overlay.getWidth(), overlay.getHeight());
	}
	
	public void redo(){
		undo.push(image.getSubimage(0, 0, image.getWidth(), image.getHeight()));
		
		Graphics2D g = image.createGraphics();
		g.drawImage(redo.pop(), null, 0, 0);
		g.finalize();
		g.dispose();
		
		penGraphics.setBackground(CLEAR);
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
	//TODO: Figure out why this doesn't display between pushChanges.
	protected void paintComponent(Graphics g){
		if(buffer == null){
			buffer = new BufferedImage(
					tilesize.width,
					tilesize.height,
					BufferedImage.TYPE_4BYTE_ABGR
			);
		}
		bufferGraphics = buffer.createGraphics();
		
		bufferGraphics.setBackground(CLEAR);
		bufferGraphics.clearRect(0, 0, tilesize.width, tilesize.height);
		bufferGraphics.drawImage(
				image.getSubimage(frame.x, frame.y, tilesize.width, tilesize.height), 
				null,
				0, 
				0);
		
		penGraphics.finalize();
		penGraphics.dispose();
		bufferGraphics.drawImage(
				overlay,
				null,
				0, 
				0);
		
		bufferGraphics.finalize();
		bufferGraphics.dispose();
		g.drawImage(
				buffer,
				0, 
				0,
				this);
		
		penGraphics = overlay.createGraphics();
		penGraphics.setBackground(CLEAR);
		penGraphics.clearRect(0, 0, overlay.getWidth(), overlay.getHeight());
	}
	
	public void update(Graphics g){
		super.update(g);
		paint(g);
	}
	
	public ImageBoard getBoard(){
		return board;
	}
}
