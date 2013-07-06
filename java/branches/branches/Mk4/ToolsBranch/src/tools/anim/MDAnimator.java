package tools.anim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;

public class MDAnimator extends JFrame {
	private static final long serialVersionUID = 5927794594829398799L;
	
	private final JMenuBar menuBar;
	private final PaintCanvas canvas;
	private final ToolPalette tools;
	private final JFileChooser chooser;
	
	private File holder;
	
	private AbstractAction resetEditMenus;
	private AbstractAction resetViewMenus;
	
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
		
		resetMenus();
		
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
				JOptionPane pane = new JOptionPane();
				pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
				
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(4,2));
				
				//Width x Height of Frame.
				panel.add(new JLabel("Width"));
				panel.add(new JLabel("Height"));
				JSpinner width = new JSpinner(
						new SpinnerNumberModel(0,Integer.MIN_VALUE,Integer.MAX_VALUE,1)
				);
				panel.add(width);
				JSpinner height = new JSpinner(
						new SpinnerNumberModel(0,Integer.MIN_VALUE,Integer.MAX_VALUE,1)
				);
				panel.add(height);
				
				//Number of actions, Number of frames.
				panel.add(new JLabel("Num. Actions"));
				panel.add(new JLabel("Num. Frames"));
				JSpinner actions = new JSpinner(
						new SpinnerNumberModel(0,Integer.MIN_VALUE,Integer.MAX_VALUE,1)
				);
				panel.add(actions);
				JSpinner frames = new JSpinner(
						new SpinnerNumberModel(0,Integer.MIN_VALUE,Integer.MAX_VALUE,1)
				);
				panel.add(frames);
				
				pane.add(panel);
				
				JDialog dialog = pane.createDialog("New Animation");//new JDialog();
				dialog.setVisible(true);
				
				if((Integer)pane.getValue() == JOptionPane.OK_OPTION){
					int w = (Integer)width.getValue();
					int h = (Integer)height.getValue();
					int a = (Integer)actions.getValue();
					int f = (Integer)frames.getValue();
					
					canvas.newImage(new Dimension(w,h), f, a);
					
					canvas.flushStacks();
					resetMenus();
				}
			}
		});
		file.add(new AbstractAction("Open"){
			private static final long serialVersionUID = 5L;

			public void actionPerformed(ActionEvent arg0) {
				try{
					int choice = chooser.showOpenDialog(canvas);
					
					if(choice == JFileChooser.APPROVE_OPTION){
						holder = chooser.getSelectedFile();
						
						canvas.setImage(ImageIO.read(holder));
					} 
				}catch(HeadlessException he){
					he.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		file.add(new AbstractAction("Save"){
			private static final long serialVersionUID = 6L;

			public void actionPerformed(ActionEvent arg0) {
				try{
					if(holder == null){
						int choice = chooser.showSaveDialog(canvas);
						
						if(choice == JFileChooser.APPROVE_OPTION){
							holder = chooser.getSelectedFile();
						}else{
							return;
						}
					}
					
					ImageIO.write(canvas.getImage(), "png", holder);
				}catch(HeadlessException he){
					he.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		file.add(new AbstractAction("Save As..."){
			private static final long serialVersionUID = 7L;

			public void actionPerformed(ActionEvent arg0) {
				try{
					int choice = chooser.showSaveDialog(canvas);
					
					if(choice == JFileChooser.APPROVE_OPTION){
						holder = chooser.getSelectedFile();
						ImageIO.write(canvas.getImage(), "png", holder);
					}
				}catch(HeadlessException he){
					he.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
				System.exit(0);
			}
		});
		
		return file;
	}
	
	private JMenu createEditMenu(){
		JMenu edit = new JMenu("Edit");
		
		final JMenuItem undo = new JMenuItem("Undo");
		final JMenuItem redo = new JMenuItem("Redo");
		final JMenuItem fframe = new JMenuItem("Frame Forward");
		final JMenuItem bframe = new JMenuItem("Frame Back");
		final JMenuItem uframe = new JMenuItem("Frame Up");
		final JMenuItem dframe = new JMenuItem("Frame Down");
		
		undo.setAction(new AbstractAction("Undo"){
			private static final long serialVersionUID = 0L;

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.undo();
				
				undo.setEnabled(canvas.canUndo());
				redo.setEnabled(canvas.canRedo());
			}
		});
		redo.setAction(new AbstractAction("Redo"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.redo();
				
				undo.setEnabled(canvas.canUndo());
				redo.setEnabled(canvas.canRedo());
			}
		});
		fframe.setAction(new AbstractAction("Frame Forward"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = canvas.getFrame();
				p.x++;
				canvas.setFrame(p);
				
				fframe.setEnabled(canvas.canAdvanceXFrame());
				bframe.setEnabled(canvas.canRetreatXFrame());
			}
		});
		bframe.setAction(new AbstractAction("Frame Back"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = canvas.getFrame();
				p.x--;
				canvas.setFrame(p);
				
				fframe.setEnabled(canvas.canAdvanceXFrame());
				bframe.setEnabled(canvas.canRetreatXFrame());
			}
		});
		dframe.setAction(new AbstractAction("Frame Down"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = canvas.getFrame();
				p.y++;
				canvas.setFrame(p);
				
				dframe.setEnabled(canvas.canAdvanceYFrame());
				uframe.setEnabled(canvas.canRetreatYFrame());
			}
		});
		uframe.setAction(new AbstractAction("Frame Up"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = canvas.getFrame();
				p.y--;
				canvas.setFrame(p);
				
				dframe.setEnabled(canvas.canAdvanceYFrame());
				uframe.setEnabled(canvas.canRetreatYFrame());
			}
		});
		
		edit.add(undo);
		edit.add(redo);
		edit.add(fframe);
		edit.add(bframe);
		edit.add(dframe);
		edit.add(uframe);
		
		resetEditMenus = new AbstractAction("Reset Edit Menus"){
			private static final long serialVersionUID = 3077585976934400669L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				undo.setEnabled(canvas.canUndo());
				redo.setEnabled(canvas.canRedo());
				fframe.setEnabled(canvas.canAdvanceXFrame());
				bframe.setEnabled(canvas.canRetreatXFrame());
				dframe.setEnabled(canvas.canAdvanceYFrame());
				uframe.setEnabled(canvas.canRetreatYFrame());
			}
			
		};
		
		return edit;
	}
	
	private JMenu createViewMenu(){
		JMenu view = new JMenu("View");
		
		final JMenuItem in = new  JMenuItem("Zoom-in");
		final JMenuItem out = new  JMenuItem("Zoom-out");
		final JMenuItem preview = new  JMenuItem("Preview");
		
		in.setAction(new AbstractAction("Zoom-in"){
			private static final long serialVersionUID = 2L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.zoomIn();
				
				in.setEnabled(canvas.canZoomIn());
				out.setEnabled(canvas.canZoomOut());
			}
			
		});
		out.setAction(new AbstractAction("Zoom-out"){
			private static final long serialVersionUID = 3L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.zoomOut();
				
				in.setEnabled(canvas.canZoomIn());
				out.setEnabled(canvas.canZoomOut());
			}
			
		});
		preview.setAction(new AbstractAction("Preview"){
			private static final long serialVersionUID = 4L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.preview();
			}
			
		});
		
		view.add(in);
		view.add(out);
		view.add(preview);
		
		resetViewMenus = new AbstractAction("Reset View Menus"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				in.setEnabled(canvas.canZoomIn());
				out.setEnabled(canvas.canZoomOut());
			}
		};
		
		return view;
	}
	
	private void resetMenus(){
		if(resetEditMenus != null){
			resetEditMenus.actionPerformed(null);
		}
		if(resetViewMenus != null){
			resetViewMenus.actionPerformed(null);
		}
	}

}
