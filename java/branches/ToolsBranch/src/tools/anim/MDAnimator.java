package tools.anim;

import javax.swing.JFrame;

public class MDAnimator extends JFrame {
	private static final long serialVersionUID = 5927794594829398799L;
	
	public MDAnimator(){
		//TODO: Setup.
	}
	
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TODO: Setup.
		
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
