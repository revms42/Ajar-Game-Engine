package space.ui.designer;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import space.model.ships.ComponentType;
import space.model.ships.IComponent;
import space.model.ships.IComponentType;

public class ComponentPanel extends JPanel {
	private static final long serialVersionUID = -6642456222626622731L;

	private final JTree componentTree;
	private final InfoPanel infoPanel;
	
	private final HashMap<IComponent<?>,ImageIcon> components;
	
	@SuppressWarnings("unchecked")
	public ComponentPanel(List<IComponent<?>> components){
		super();
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Components");
		HashMap<IComponentType,DefaultMutableTreeNode> map = 
			new HashMap<IComponentType,DefaultMutableTreeNode>();
		
		for(ComponentType type : ComponentType.COMPONENTTYPES){
			DefaultMutableTreeNode a = new DefaultMutableTreeNode(type.getName());
			map.put(type,a);
			
			if(type.hasSubTypes()){
				for(ComponentType.SubType sub : type.getSubTypes()){
					DefaultMutableTreeNode b = new DefaultMutableTreeNode(sub.getName());
					map.put(sub, b);
					a.add(b);
				}
			}
			
			top.add(a);
		}
		
		componentTree = new JTree(top);
		componentTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		componentTree.setCellRenderer(new ComponentRenderer());
		
		this.components = new HashMap<IComponent<?>,ImageIcon>();
		
		for(IComponent component : components){
			BufferedImage bi = new BufferedImage(48,48,BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = bi.createGraphics();
			component.getDisplayFactory().display(component, g2);
			g2.finalize();
			g2.dispose();
			
			ImageIcon icon = new ImageIcon(bi);
			this.components.put(component, icon);
			
			ComponentNode node = new ComponentNode(component);
			
			map.get(component.getType()).add(node);
		}
		
		infoPanel = new InfoPanel();
		
		componentTree.addTreeSelectionListener(infoPanel);
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		split.setTopComponent(new JScrollPane(componentTree));
		split.setBottomComponent(new JScrollPane(infoPanel));
	}
	
	private class ComponentNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -4014451241239223022L;

		private ComponentNode(IComponent<?> component){
			super(component);
		}
		
	}
	
	private class ComponentRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 2497884517861401000L;
		
		public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean sel,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus
		) {
			
			super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
			
			if (leaf) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
				IComponent<?> nodeInfo = (IComponent<?>)(node.getUserObject());
				
				if(nodeInfo.getType() != null){
					setText(nodeInfo.getName());
				}
				
				if(components.containsKey(nodeInfo)){
					setIcon(components.get(nodeInfo));
				}
			}
			
			return this;
		}
	}

}
