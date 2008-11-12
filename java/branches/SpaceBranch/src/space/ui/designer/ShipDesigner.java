package space.ui.designer;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;

import space.model.component.IComponent;

public class ShipDesigner<I> extends JFrame {
	private static final long serialVersionUID = 9207357928566998058L;
	
	public static DataFlavor COMP_FLAVOR;
	
	private final DesignPanel<I> designPanel;
	private final CostPanel costPanel;
	private final LibraryPanel<I> componentPanel;
	private final Vector<IComponent<I>> components;
	
	private final ComponentTransferHandler<I> transferer;
	
	protected ShipDesigner(Vector<IComponent<I>> components){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		
		this.components = components;
		
		transferer = new ComponentTransferHandler<I>();
		
		designPanel = new DesignPanel<I>(transferer);
		costPanel = new CostPanel();
		componentPanel = new LibraryPanel<I>(transferer,components);
		
		JScrollPane dscroll = new JScrollPane(designPanel);
		JScrollPane cscroll = new JScrollPane(componentPanel);
		
		this.add(dscroll);
		this.add(cscroll);
		
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.NORTH, cscroll, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, cscroll, -5, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, cscroll, -5, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH, dscroll, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, dscroll, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, dscroll, -5, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, dscroll, -10, SpringLayout.WEST, cscroll);
		
		this.pack();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			COMP_FLAVOR = new DataFlavor(
					DataFlavor.javaJVMLocalObjectMimeType + 
					";class=\"" + IComponent.class.getName() + "\"");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		(new ShipDesigner<String>(ComponentLoader.loadComponents(null))).setVisible(true);
	}

}
