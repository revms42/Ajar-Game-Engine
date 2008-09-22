package space.ui.designer;

import javax.swing.JFrame;

public class ShipDesigner extends JFrame {
	private static final long serialVersionUID = 9207357928566998058L;
	
	private final DesignPanel designPanel;
	private final ComponentPanel componentPanel;

	protected ShipDesigner(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		designPanel = new DesignPanel();
		componentPanel = new ComponentPanel();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
