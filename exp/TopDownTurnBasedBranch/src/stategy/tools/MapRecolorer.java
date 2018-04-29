package stategy.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MapRecolorer extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final Vector<ColorPanel> colors;
	private final JPanel colorPane;
	private final JFileChooser filechooser;
	
	private BufferedImage image;
	
	private MapRecolorer(){
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		colors = new Vector<ColorPanel>();
		filechooser = new JFileChooser();
		filechooser.setFileFilter(new FileNameExtensionFilter("PNG","png"));
		
		colorPane = new JPanel();
		colorPane.setLayout(new GridLayout(0,1));
		this.add(new JScrollPane(colorPane),BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,1));
		JButton add = new JButton(new AbstractAction("Add Color"){
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				colors.add(new ColorPanel());
				colorPane.add(colors.lastElement());
				colorPane.revalidate();
			}
		});
		JButton remove = new JButton(new AbstractAction("Remove Color(s)"){
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				Vector<ColorPanel> remove = new Vector<ColorPanel>();
				for(ColorPanel panel: colors){
					if(panel.checkbox.isSelected()){
						colorPane.remove(panel);
						remove.add(panel);
					}
				}
				colors.removeAll(remove);
				colorPane.revalidate();
			}
		});
		buttons.add(add);buttons.add(remove);
		this.add(buttons,BorderLayout.NORTH);
		
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(new AbstractAction("Load"){
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				int selection = filechooser.showOpenDialog(colorPane);
				
				if(selection == JFileChooser.APPROVE_OPTION){
					try {
						image = ImageIO.read(filechooser.getSelectedFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}));
		file.add(new JMenuItem(new AbstractAction("Convert and Save"){
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				int selection = filechooser.showSaveDialog(colorPane);
				
				if(selection == JFileChooser.APPROVE_OPTION){
					try {
						convert();
						ImageIO.write(image, "png", filechooser.getSelectedFile());
						System.exit(0);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}));
		menubar.add(file);
		this.setJMenuBar(menubar);
		
		this.pack();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new MapRecolorer()).start(args);
	}

	private void start(String[] args) {
		this.setVisible(true);
	}
	
	private class ColorPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public final JCheckBox checkbox;
		public final JButton fromColor;
		public final JButton toColor;
		
		public ColorPanel(){
			super();
			this.setLayout(new GridLayout(1,3));
			checkbox = new JCheckBox();
			fromColor = new JButton(new AbstractAction(){
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					Color c = JColorChooser.showDialog(fromColor, "From...", Color.BLACK);
					fromColor.setBackground(c);
					int antiRed = 255 - c.getRed();
					int antiGreen = 255 - c.getGreen();
					int antiBlue = 255 - c.getBlue();
					Color opposite = new Color(antiRed,antiGreen,antiBlue);
					fromColor.setForeground(opposite);
					fromColor.setText(c.getAlpha() + "," + c.getRed() + "," + c.getBlue() + "," + c.getGreen());
					
					ColorPanel[] all = colors.toArray(new ColorPanel[colors.size()]);
					Arrays.sort(all,ColorPanelComparator.comp);
					colors.removeAllElements();
					for(ColorPanel panel : all){
						colors.add(panel);
					}
				}
			});
			toColor = new JButton(new AbstractAction(){
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					Color c = JColorChooser.showDialog(fromColor, "To...", Color.BLACK);
					toColor.setBackground(c);
					int antiRed = 255 - c.getRed();
					int antiGreen = 255 - c.getGreen();
					int antiBlue = 255 - c.getBlue();
					Color opposite = new Color(antiRed,antiGreen,antiBlue);
					toColor.setForeground(opposite);
					toColor.setText(c.getAlpha() + "," + c.getRed() + "," + c.getBlue() + "," + c.getGreen());
				}
			});
			this.add(checkbox);
			this.add(fromColor);
			this.add(toColor);
		}
	}
	
	private static class ColorPanelComparator implements Comparator<ColorPanel> {

		public static ColorPanelComparator comp;
		
		static {
			comp = new ColorPanelComparator();
		}
		
		public int compare(ColorPanel o1, ColorPanel o2) {
			return o1.fromColor.getBackground().getRGB() - o2.fromColor.getBackground().getRGB();
		}
		
	}

	private void convert(){
		int[] fromArray = new int[colors.size()];
		for(int i = 0; i < colors.size(); i++){
			fromArray[i] = colors.get(i).fromColor.getBackground().getRGB();
		}
		
		int[] array = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		
		for(int i = 0; i < array.length; i++){
			int index = Arrays.binarySearch(fromArray, array[i]);
			
			if(index > 0){
				array[i] = colors.get(index).toColor.getBackground().getRGB();
			}
		}
		
		image.setRGB(0, 0, image.getWidth(), image.getHeight(), array, 0, image.getWidth());
	}
}
