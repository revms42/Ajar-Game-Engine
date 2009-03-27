package test.ai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import org.display.IDisplayContext;
import org.display.IDisplayable;
import org.display.ImageBoard;
import org.display.ImageOpPalette;
import org.display.ImagePalette;
import org.interaction.IEntity;

public class ShipContext implements FieldConstants, IDisplayContext<String,String> {
	private Point pos;
	private Point tile;
	private AffineTransformOp op;
	private AffineTransform rot;
	
	public final static String BOARD = "board";
	
	public ShipContext(){
		pos = new Point(0,0);
		tile = new Point(0,0);
		
		rot = new AffineTransform();
		op = new AffineTransformOp(rot,AffineTransformOp.TYPE_BILINEAR);
	}
	
	public static ImagePalette<String> createPalette(){
		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(16, 8);
		p.addPoint(0, 16);
		p.addPoint(4, 8);
		p.addPoint(0, 0);
		
		BufferedImage img = new BufferedImage(16,16,BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics2D g = img.createGraphics();
		g.setColor(Color.BLACK);
		g.fill(p);
		g.finalize();
		g.dispose();
		
		ImageBoard board = new ImageBoard(img, new Dimension(16,16));
		
		ImagePalette<String> palette = new ImagePalette<String>();
		palette.put(BOARD, board);
		
		return palette;
	}
	
	@Override
	public String getBoard(IDisplayable<String,String> subject) {
		return BOARD;
	}

	@Override
	public ImageOpPalette<String> getImageOpPalette() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPosition(IDisplayable<String,String> subject) {
		pos.setLocation(subject.value(X).intValue(), subject.value(Y).intValue());
		return pos;
	}

	@Override
	public Point getTile(IDisplayable<String,String> subject) {
		return tile;
	}

	@Override
	public BufferedImageOp getTransform(IDisplayable<String,String> subject) {
		rot = AffineTransform.getRotateInstance(subject.value(HEADING).doubleValue());
		op = new AffineTransformOp(rot,AffineTransformOp.TYPE_BILINEAR);
		return op;
	}

	@Override
	public void setImageOpPalette(ImageOpPalette<String> palette) {
		// TODO Auto-generated method stub

	}

}
