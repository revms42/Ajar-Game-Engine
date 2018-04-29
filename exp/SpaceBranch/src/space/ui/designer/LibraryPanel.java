package space.ui.designer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import space.model.component.ComponentType;
import space.model.component.IComponent;
import space.model.component.IComponentType;

public class LibraryPanel<I> extends JPanel implements IComponentTransferer<I> {
	private static final long serialVersionUID = -6642456222626622731L;

	private final JTree componentTree;
	private final InfoPanel<I> infoPanel;
	
	private final HashMap<IComponent<I>,ImageIcon> components;
	
	public LibraryPanel(ComponentTransferHandler<I> handler, List<IComponent<I>> components){
		super();
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Components");
		HashMap<IComponentType,DefaultMutableTreeNode> map = 
			new HashMap<IComponentType,DefaultMutableTreeNode>();
		
		for(ComponentType type : ComponentType.COMPONENTTYPES){
			DefaultMutableTreeNode a = new DefaultMutableTreeNode(type.getCatagoryName());
			map.put(type,a);
			
			if(type.hasSubTypes()){
				for(ComponentType.SubType sub : type.getSubTypes()){
					DefaultMutableTreeNode b = new DefaultMutableTreeNode(sub.getCatagoryName());
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
		
		this.components = new HashMap<IComponent<I>,ImageIcon>();
		
		for(IComponent<I> component : components){
			BufferedImage bi = new BufferedImage(48,48,BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = bi.createGraphics();
			component.getDisplayFactory().display(component, g2);
			g2.finalize();
			g2.dispose();
			
			ImageIcon icon = new ImageIcon(bi);
			this.components.put(component, icon);
			
			ComponentNode node = new ComponentNode(component);
			
			map.get(component.getPrimaryType()).add(node);
		}
		
		infoPanel = new InfoPanel<I>();
		
		componentTree.addTreeSelectionListener(infoPanel);
		componentTree.setTransferHandler(handler);
		componentTree.setDragEnabled(true);
		this.setTransferHandler(handler);
		
		JScrollPane cscroll = new JScrollPane(componentTree);
		JScrollPane iscroll = new JScrollPane(infoPanel);
		
		SpringLayout manager = new SpringLayout();
		this.setLayout(manager);
		
		this.add(iscroll);
		this.add(cscroll);
		
		manager.putConstraint(SpringLayout.NORTH, cscroll, 0, SpringLayout.NORTH, this);
		manager.putConstraint(SpringLayout.EAST, cscroll, 0, SpringLayout.EAST, this);
		manager.putConstraint(SpringLayout.WEST, cscroll, 0, SpringLayout.WEST, this);
		manager.putConstraint(SpringLayout.SOUTH, cscroll, -5, SpringLayout.NORTH, iscroll);
		
		manager.putConstraint(SpringLayout.WEST, iscroll, 0, SpringLayout.WEST, this);
		manager.putConstraint(SpringLayout.EAST, iscroll, 0, SpringLayout.EAST, this);
		manager.putConstraint(SpringLayout.SOUTH, iscroll, 0, SpringLayout.SOUTH, this);
	}
	
	public class ComponentNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -4014451241239223022L;

		private ComponentNode(IComponent<I> component){
			super(component);
		}
		
	}
	
	public class ComponentRenderer extends DefaultTreeCellRenderer {
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
			
			if (leaf && ((DefaultMutableTreeNode)value).getUserObject() instanceof IComponent){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
				IComponent<?> nodeInfo = (IComponent<?>)(node.getUserObject());
				
				if(nodeInfo.getPrimaryType() != null){
					setText(nodeInfo.getName());
				}
				
				if(components.containsKey(nodeInfo)){
					setIcon(components.get(nodeInfo));
				}
			}
			
			return this;
		}
	}
	
	@Override
	public IComponent<I> getSelectedComponent() {
		return infoPanel.getSelectedComponent();
	}

	@Override
	public boolean isFinite() {
		return infoPanel.isFinite();
	}

	@Override
	public void removeComponent(IComponent<I> component) {
		infoPanel.removeComponent(component);
	}

	@Override
	public void setSelectedComponent(IComponent<I> component) {
		infoPanel.setSelectedComponent(component);
	}

	public Dimension getPreferredSize(){
		return new Dimension(
				componentTree.getPreferredSize().width + 3,
				componentTree.getPreferredSize().height + 
				infoPanel.getPreferredSize().height + 3
		);
	}
}
