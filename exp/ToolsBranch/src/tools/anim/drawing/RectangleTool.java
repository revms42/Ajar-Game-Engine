package tools.anim.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.anim.IPaintMode;
import tools.anim.PaintCanvas;

public class RectangleTool extends AbstractTool<Integer> {

	public RectangleTool(PaintCanvas p, IPaintMode t) {
		super(p, t);
		// TODO Auto-generated constructor stub
	}

	private ImageIcon icon;
	
	@Override
	protected void draw(Paint t, Graphics2D g, Integer... p) {
		g.setPaint(t);
		g.drawRect(p[0],p[1],p[2],p[3]);
	}

	@Override
	protected void fill(Paint t, Graphics2D g, Integer... p) {
		g.setPaint(t);
		g.fillRect(p[0],p[1],p[2],p[3]);
	}

	@Override
	public Icon getButtonIcon() {
		if(icon == null){
			BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			fill(Color.LIGHT_GRAY,g,3,3,18,18);
			draw(Color.BLACK,g,3,3,18,18);
			g.finalize();
			g.dispose();
			
			icon = new ImageIcon(image,"Rectangle");
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
		paint(
				this.palette,
				canvas.getDrawingGraphics(),
				startPoint.x / canvas.getZoomLevel(),
				startPoint.y / canvas.getZoomLevel(),
				(endPoint.x - startPoint.x) / canvas.getZoomLevel(),
				(endPoint.y - startPoint.y) / canvas.getZoomLevel()
		);
		if(!inProgress){
			canvas.pushChange();
		}
	}
}
