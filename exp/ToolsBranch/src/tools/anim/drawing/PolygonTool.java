package tools.anim.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.anim.IPaintMode;
import tools.anim.PaintCanvas;

public class PolygonTool extends AbstractPolyClickTool<Polygon> {

	private Polygon pgon;
	
	public PolygonTool(PaintCanvas p, IPaintMode t) {
		super(p, t);
		pgon = new Polygon();
	}

	private ImageIcon icon;
	
	@Override
	protected void draw(Paint t, Graphics2D g, Polygon... p) {
		g.setPaint(t);
		g.drawPolygon(p[0]);
	}

	@Override
	protected void fill(Paint t, Graphics2D g, Polygon... p) {
		g.setPaint(t);
		g.fillPolygon(p[0]);
	}

	@Override
	public Icon getButtonIcon() {
		if(icon == null){
			BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			Polygon p = new Polygon();
			p.addPoint(11, 21);
			p.addPoint(13, 21);
			p.addPoint(21, 12);
			p.addPoint(13, 3);
			p.addPoint(11, 3);
			p.addPoint(3, 12);
			fill(Color.LIGHT_GRAY,g,p);
			draw(Color.BLACK,g,p);
			g.finalize();
			g.dispose();
			
			icon = new ImageIcon(image,"Polygon");
		}
		
		return icon;
	}

	@Override
	public JPanel parametricPanel() {
		// TODO Create Parametric Panel
		return null;
	}

	@Override
	public void handleMouse() {
		if(points != null && !points.isEmpty()){
			pgon = new Polygon();
			
			for(Point p : points){
				pgon.addPoint(
						p.x / canvas.getZoomLevel(), 
						p.y / canvas.getZoomLevel()
				);
			}
			pgon.addPoint(
					endPoint.x / canvas.getZoomLevel(), 
					endPoint.y / canvas.getZoomLevel()
			);
			
			paint(
					this.palette,
					canvas.getDrawingGraphics(),
					pgon
			);
			
			if(!inProgress){
				canvas.pushChange();
				points.clear();
			}
		}
	}
}
