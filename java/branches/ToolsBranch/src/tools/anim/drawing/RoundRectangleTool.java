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

public class RoundRectangleTool extends AbstractTwoModeTool<Integer> {

	public RoundRectangleTool(PaintCanvas p, IPaintMode t) {
		super(p, t);
		// TODO Auto-generated constructor stub
	}

	private ImageIcon icon;
	
	@Override
	protected void draw(Paint t, Graphics2D g, Integer... p) {
		g.setPaint(t);
		g.drawRoundRect(p[0],p[1],p[2],p[3],p[4],p[5]);
	}

	@Override
	protected void fill(Paint t, Graphics2D g, Integer... p) {
		g.setPaint(t);
		g.fillRoundRect(p[0],p[1],p[2],p[3],p[4],p[5]);
	}

	@Override
	public Icon getButtonIcon() {
		if(icon == null){
			BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			fill(Color.LIGHT_GRAY,g,3,3,18,18,3,3);
			draw(Color.BLACK,g,3,3,18,18,3,3);
			g.finalize();
			g.dispose();
			
			icon = new ImageIcon(image,"Round Rectangle");
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
		if(!mode2 || startPoint2 == null || endPoint2 == null){
			paint(
					this.palette,
					canvas.getDrawingGraphics(),
					startPoint.x,
					startPoint.y,
					endPoint.x - startPoint.x,
					endPoint.y - startPoint.y,
					0,
					0
			);
		}else{
			paint(
					this.palette,
					canvas.getDrawingGraphics(),
					startPoint.x,
					startPoint.y,
					endPoint.x - startPoint.x,
					endPoint.y - startPoint.y,
					(int)Math.abs(endPoint2.x-((startPoint.x + endPoint.x)/2)),
					(int)Math.abs(endPoint2.y-((startPoint.y + endPoint.y)/2))
	
			);
		}
		
		if(!inProgress){
			canvas.pushChange();
			mode2 = false;
		}
	}
}
