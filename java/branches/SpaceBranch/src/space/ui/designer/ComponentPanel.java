package space.ui.designer;

import javax.swing.JPanel;

public class ComponentPanel extends JPanel {
	private static final long serialVersionUID = -6642456222626622731L;

	private final InfoPanel infoPanel;
	
	public ComponentPanel(){
		super();
		
		infoPanel = new InfoPanel();
	}
}
