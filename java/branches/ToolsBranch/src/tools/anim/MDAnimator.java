package tools.anim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class MDAnimator extends JFrame {
	private static final long serialVersionUID = 5927794594829398799L;
	
	private final JMenuBar menuBar;
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
		
		menuBar = new JMenuBar();
	}
	
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TODO: Setup.
		this.setLayout(new BorderLayout());
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sp.setRightComponent(new JScrollPane(canvas));
		sp.setLeftComponent(tools);
		this.add(sp,BorderLayout.CENTER);
		this.setMinimumSize(new Dimension(400,400));
		
		menuBar.add(createEditMenu());
		this.setJMenuBar(menuBar);
		
		this.pack();
		this.setVisible(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new MDAnimator()).init();
	}
	
	private JMenu createEditMenu(){
		JMenu edit = new JMenu("Edit");
		
		edit.add(new AbstractAction("Undo"){
			private static final long serialVersionUID = 0L;

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.undo();
			}
		});
		edit.add(new AbstractAction("Redo"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.redo();
			}
		});
		
		return edit;
	}

}
