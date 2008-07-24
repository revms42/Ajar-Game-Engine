package tools.anim.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.anim.IPaintMode;
import tools.anim.PaintCanvas;

public class ShapeTool extends AbstractTool<Shape> {

	public ShapeTool(PaintCanvas p, IPaintMode t) {
		super(p, t);
		// TODO Auto-generated constructor stub
	}

	private ImageIcon icon;
	
	@Override
	protected void draw(Paint t, Graphics2D g, Shape... s) {
		g.setPaint(t);
		g.draw(s[0]);
	}

	@Override
	protected void fill(Paint t, Graphics2D g, Shape... s) {
		g.setPaint(t);
		g.fill(s[0]);
	}
	
	@Override
	public Icon getButtonIcon() {
		if(icon == null){
			BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			Polygon p = new Polygon();
			p.addPoint(3, 3);
			p.addPoint(3, 21);
			p.addPoint(21, 21);
			p.addPoint(12, 12);
			p.addPoint(21, 3);
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

}
