package strategy.display.map;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import org.display.IEnvironment;
import org.display.IEnvironmentContext;
import org.display.ImageOpPalette;

public class BattleMapContext implements IEnvironmentContext<Integer> {

	public static final int alphaMask = 0xFF000000;//>6
	public static final int alphaShift = 24;
	
	//Red is Z
	public static final int redMask = 0x00FF0000;//>>4
	public static final int redShift = 16;
	
	//Green is X
	public static final int greenMask = 0x0000FF00;//>>2
	public static final int greenShift = 8;
	
	//Blue is Y
	public static final int blueMask = 0x000000FF;//>>0
	public static final int blueShift = 0;
	
	private final Dimension tilesize;
	//Time savers.
	private Rectangle lastSubMap;
	private Rectangle lastScreen;//Passed from getTilesInWindow.
	private Dimension lastDimension;//The number of tiles in the window.
	private Point[] lastRange;//The addresses for the tiles to be displayed in the window.
	private int lastDepth;//Depth of the last board
	private BufferedImage lastBoard;//The last board
	
	public BattleMapContext(Dimension basetilesize){
		this.tilesize = basetilesize;
	}
	
	private int getPixel(IEnvironment subject, int x, int y, int z){
		return ((BufferedImage) subject.getMaps().get(z)).getRGB(x,y);
	}
	
	private int[] getPixels(IEnvironment subject, int x, int y, int z, int w, int h){
		return ((BufferedImage) subject.getMaps().get(z)).getRGB(x,y,w,h,null,0,w);
	}
	
	private Point pixelToTile(int pixel){
		int x = (pixel & greenMask) >> greenShift;
		int y = (pixel & blueMask) >> blueShift;
		
		return new Point(x,y);
	}
	
	private Point translate(Point p, boolean max){
		if(max){
			p.setLocation((int)Math.floor(p.x / tilesize.width),(int)Math.floor(p.y / tilesize.height));
		}else{
			p.setLocation((int)Math.ceil(p.x / tilesize.width),(int)Math.ceil(p.y / tilesize.height));
		}
		return p;
	}
	
	public Point[] getAll(IEnvironment subject, int depth) {
		// TODO Auto-generated method stub
		return null;
	}

	public BufferedImageOp[] getAllTransform(IEnvironment subject, int depth) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getBoard(IEnvironment subject, int depth) {
		return (getPixel(subject,0,0,depth) & redMask) >> redShift; 
	}

	public ImageOpPalette<?> getImageOpPalette() {
		// TODO Auto-generated method stub
		return null;
	}

	public Point getPoint(IEnvironment subject, Point p, int depth) {
		int pixel = getPixel(subject,p.x,p.y,depth);
		
		return pixelToTile(pixel);
	}

	public BufferedImageOp getPointTransform(IEnvironment subject, Point p, int depth) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Point[] getRange(IEnvironment<Integer, ?> subject, Rectangle r, int depth) {
		if(lastSubMap == null || !lastSubMap.equals(r) || lastDepth != depth){
			Point start = translate(new Point(r.x, r.y),false);
			Dimension dim = getTilesInWindow(subject,r,depth);
			
			if(lastDepth != depth){
				lastBoard = (BufferedImage) subject.getMaps().get(depth);
			}
			
			int w = lastBoard.getWidth();
			int h = lastBoard.getHeight();
			
			int[] pixels = getPixels(
					subject,
					start.x,
					start.y,
					depth,
					dim.width+start.x > w ? w-start.x : dim.width,
					dim.height+start.y > h ? h-start.y : dim.height);
			lastRange = new Point[dim.width*dim.height];
			
			for(int i = 0; i < pixels.length; i++){
				lastRange[i] = pixelToTile(pixels[i]);
			}
			
			lastSubMap = (Rectangle)r.clone();
			lastDepth = depth;
		}
		
		return lastRange;
	}

	public BufferedImageOp[] getRangeTransform(IEnvironment<Integer, ?> subject, Rectangle r, int depth) {
		return null;
	}

	/**
	 * 
	 */
	public Dimension getTilesInWindow(IEnvironment<Integer, ?> subject, Rectangle r, int depth) {
		if(lastScreen == null || !lastScreen.equals(r) || lastDimension == null){
			if(lastDepth != depth || lastBoard == null){
				lastBoard = (BufferedImage) subject.getMaps().get(depth);
			}
			lastDimension = new Dimension(
					(int)(Math.ceil(Math.ceil((r.width - r.x))/tilesize.width)),
					(int)(Math.ceil(Math.ceil((r.height - r.y))/tilesize.height))
			);
			
			lastDimension.setSize(
					lastDimension.width > lastBoard.getWidth() ? lastBoard.getWidth() : lastDimension.width,
					lastDimension.height > lastBoard.getHeight() ? lastBoard.getHeight() : lastDimension.height		
			);
			
			lastScreen = (Rectangle)r.clone();
		}
		
		return lastDimension; 
	}

	public void setImageOpPalette(ImageOpPalette palette) {
		// TODO Auto-generated method stub

	}

}
