package tools.anim.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LineTool extends AbstractTool<Integer> {
	private ImageIcon icon;
	
	@Override
	protected void draw(Paint t, Graphics2D g, Integer... p) {
		g.setPaint(t);
		g.drawLine(p[0],p[1],p[2],p[3]);
	}

	@Override
	protected void fill(Paint t, Graphics2D g, Integer... p) {
		draw(t,g,p);
	}
	
	@Override
	public Icon getButtonIcon() {
		if(icon == null){
			BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			fill(Color.LIGHT_GRAY,g,3,21,21,3);
			g.finalize();
			g.dispose();
			
			icon = new ImageIcon(image,"Line");
		}
		
		return icon;
	}
	
	@Override
	public JPanel parametricPanel() {
		// TODO Create Parametric Panel
		return null;
	}
}
