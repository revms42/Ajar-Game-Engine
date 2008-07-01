package space.ui;

import javax.swing.JFrame;

public class Space extends JFrame {
	private static final long serialVersionUID = 8872158514385315895L;

	private void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new Space()).init();
	}

}
