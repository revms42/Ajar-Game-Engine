package tools.anim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
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
	private final JFileChooser chooser;
	
	private File holder;
	
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
		chooser = new JFileChooser();
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
		
		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createViewMenu());
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
	
	private JMenu createFileMenu(){
		final JMenu file = new JMenu("File");
		
		file.add(new AbstractAction("New"){
			private static final long serialVersionUID = 4L;

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		file.add(new AbstractAction("Open"){
			private static final long serialVersionUID = 5L;

			public void actionPerformed(ActionEvent arg0) {
				try{
					int choice = chooser.showOpenDialog(canvas);
					
					if(choice == JFileChooser.APPROVE_OPTION){
						holder = chooser.getSelectedFile();
					}
					
					//TODO: Load the file.
				}catch(HeadlessException he){
					he.printStackTrace();
				}
			}
		});
		file.add(new AbstractAction("Save"){
			private static final long serialVersionUID = 6L;

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		file.add(new AbstractAction("Save As..."){
			private static final long serialVersionUID = 7L;

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		file.add(new AbstractAction("Close"){
			private static final long serialVersionUID = 8L;

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		file.add(new AbstractAction("Exit"){
			private static final long serialVersionUID = 9L;

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return file;
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
	
	private JMenu createViewMenu(){
		JMenu view = new JMenu("View");
		
		view.add(new AbstractAction("Zoom-in"){
			private static final long serialVersionUID = 2L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.zoomIn();
			}
			
		});
		view.add(new AbstractAction("Zoom-out"){
			private static final long serialVersionUID = 3L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.zoomOut();
			}
			
		});
		
		return view;
	}

}
