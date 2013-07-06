package tools.anim.drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;

import tools.anim.IPaintMode;

public class ColorPalette extends AbstractAction {
	private static final long serialVersionUID = 4869504088675248101L;
	private Component pc;
	private IPaintMode tp;
	private ImageIcon icon;
	
	public ColorPalette(Component pc, IPaintMode tp){
		this.pc = pc;
		this.tp = tp;
		
		BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		
		g.setPaint(Color.RED);
		g.fillArc(3,3,18,18,0,60);
		g.setPaint(Color.ORANGE);
		g.fillArc(3,3,18,18,60,60);
		g.setPaint(Color.YELLOW);
		g.fillArc(3,3,18,18,120,60);
		g.setPaint(Color.GREEN);
		g.fillArc(3,3,18,18,180,60);
		g.setPaint(Color.CYAN);
		g.fillArc(3,3,18,18,240,60);
		g.setPaint(Color.BLUE);
		g.fillArc(3,3,18,18,300,60);
		
		g.finalize();
		g.dispose();
		
		icon = new ImageIcon(image,"Arc");
	}
	
	public ImageIcon getIcon(){
		return icon;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		tp.setForePaint(JColorChooser.showDialog(pc, "Color Chooser", Color.BLACK));
	}
}
