package space.ui.designer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

public class ComponentPanel extends JPanel {
	private static final long serialVersionUID = -6642456222626622731L;

	private final JTree componentTree;
	private final InfoPanel infoPanel;
	
	public ComponentPanel(){
		super();
		
		componentTree = new JTree();
		infoPanel = new InfoPanel();
		
		componentTree.addTreeSelectionListener(infoPanel);
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		split.setTopComponent(new JScrollPane(componentTree));
		split.setBottomComponent(new JScrollPane(infoPanel));
	}
}
