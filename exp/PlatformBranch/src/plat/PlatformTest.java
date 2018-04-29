package plat;

import javax.swing.JFrame;

public class PlatformTest extends JFrame {
	private static final long serialVersionUID = 1282410970986044905L;
	
	private PlatformTest(){
		//TODO: Setup.
	}

	private void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TODO: Setup.
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new PlatformTest()).init();
	}

}
