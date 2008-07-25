package tools.anim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MDAnimator extends JFrame {
	private static final long serialVersionUID = 5927794594829398799L;
	
	private final PaintCanvas canvas;
	private final ToolPalette tools;
	
	public MDAnimator(){
		BufferedImage start = new BufferedImage(400,400,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = start.createGraphics();
		g.setBackground(new Color(255,255,255,255));
		g.clearRect(0, 0, 400, 400);
		g.finalize();
		g.dispose();
		
		canvas = new PaintCanvas(
				start,
				new Dimension(100,100)
		);
		tools = new ToolPalette(canvas);
	}
	
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TODO: Setup.
		this.getRootPane().setLayout(new BorderLayout());
		this.getRootPane().add(canvas,BorderLayout.CENTER);
		this.getRootPane().add(tools,BorderLayout.WEST);
		
		this.pack();
		this.setVisible(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new MDAnimator()).init();
	}

}
