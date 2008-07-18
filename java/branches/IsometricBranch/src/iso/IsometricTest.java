package iso;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.display.AnimationPanel;

public class IsometricTest extends JFrame {
	private static final long serialVersionUID = -3930651603616172263L;

	private final Dimension size;
	
	private final AnimationPanel stage;
	private IsometricManifest<String,String> manifest;
	
	private IsometricTest(){
		manifest = new IsometricManifest<String,String>();
		stage = new AnimationPanel(manifest);
		size = new Dimension(800,500);
	}
	
	private void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		stage.setSize(size);
		stage.setPreferredSize(size);
		
		this.getContentPane().add(stage);
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new IsometricTest()).start();
	}

}
