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

public class ArcTool extends AbstractTwoModeTool<Integer> {

	public ArcTool(PaintCanvas p, IPaintMode t) {
		super(p, t);
		// TODO Auto-generated constructor stub
	}

	private ImageIcon icon;

	@Override
	protected void draw(Paint t, Graphics2D g, Integer... p) {
		g.setPaint(t);
		g.drawArc(p[0],p[1],p[2],p[3],p[4],p[5]);
		
		if(inProgress){
			if(!mode2){
				g.drawRect(p[0],p[1],p[2],p[3]);
			}else{
				if(endPoint2 != null){
					g.drawLine(p[0]+(p[2]/2),p[1]+(p[3]/2),endPoint2.x,endPoint2.y);
				}
			}
		}
	}

	@Override
	protected void fill(Paint t, Graphics2D g, Integer... p) {
		g.setPaint(t);
		g.fillArc(p[0],p[1],p[2],p[3],p[4],p[5]);
	}

	@Override
	public Icon getButtonIcon() {
		if(icon == null){
			BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			fill(Color.LIGHT_GRAY,g,3,3,18,18,0,270);
			draw(Color.BLACK,g,3,3,18,18,0,270);
			g.finalize();
			g.dispose();
			
			icon = new ImageIcon(image,"Arc");
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
					360
			);
		}else{
			double startNumer = Math.abs((startPoint2.y - ((startPoint.y + endPoint.y)/2)));
			double startDenom = Math.abs((startPoint2.x - ((startPoint.x + endPoint.x)/2)));
			
			startDenom = startDenom==0 ? 1 : startDenom;
			
			double angle = Math.toDegrees(Math.atan(startNumer/startDenom));
			
			
			if(	startPoint2.x < ((startPoint.x + endPoint.x)/2) && 
				startPoint2.y > ((startPoint.y + endPoint.y)/2))
			{
				//Second Quad.
				angle = angle + 180.0d;
			}else if(	startPoint2.x < ((startPoint.x + endPoint.x)/2) && 
						startPoint2.y <= ((startPoint.y + endPoint.y)/2))
			{
				//Third Quad.
				angle = 180.0d - angle;
			}else if(	startPoint2.x >= ((startPoint.x + endPoint.x)/2) && 
						startPoint2.y <= ((startPoint.y + endPoint.y)/2))
			{
				//Fourth Quad.
				//angle = angle;
			}else{
				angle = -angle;
			}
			
			int extent = (Math.hypot(
						(endPoint2.x-((startPoint.x + endPoint.x)/2)),
						(endPoint2.y-((startPoint.y + endPoint.y)/2))
					) > 360) ? 
					360 :
					(int)Math.hypot(
							(endPoint2.x-((startPoint.x + endPoint.x)/2)),
							(endPoint2.y-((startPoint.y + endPoint.y)/2))
					);
			
			paint(
					this.palette,
					canvas.getDrawingGraphics(),
					startPoint.x,
					startPoint.y,
					endPoint.x - startPoint.x,
					endPoint.y - startPoint.y,
					(int)angle,
					extent

			);
		}

		if(!inProgress){
			canvas.pushChange();
			mode2 = false;
		}
	}
}
