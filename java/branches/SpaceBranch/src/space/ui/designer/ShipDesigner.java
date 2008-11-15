package space.ui.designer;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import space.model.component.IComponent;
import space.model.tech.XMLTechTreeLoader;

public class ShipDesigner<I> extends JFrame {
	private static final long serialVersionUID = 9207357928566998058L;
	
	private static String tree = "C:\\private\\SpaceBranch\\src\\space\\model\\tech\\xml\\DefaultTechTree.xml";
	private static String schema = "C:\\private\\SpaceBranch\\src\\space\\model\\tech\\schema\\TechTree.xsd";
	
	public static DataFlavor COMP_FLAVOR;
	
	private final DesignPanel<I> designPanel;
	//private final CostPanel costPanel;
	private final LibraryPanel<I> componentPanel;
	//private final Vector<IComponent<I>> components;
	
	private final ComponentTransferHandler<I> transferer;
	private final SpringLayout layout;
	
	protected ShipDesigner(Vector<IComponent<I>> components){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		
		//this.components = components;
		
		transferer = new ComponentTransferHandler<I>();
		
		designPanel = new DesignPanel<I>(transferer);
		//costPanel = new CostPanel();
		componentPanel = new LibraryPanel<I>(transferer,components);
		
		JScrollPane dscroll = new JScrollPane(designPanel);
		
		this.add(dscroll);
		this.add(componentPanel);
		
		layout = new SpringLayout();
		this.setLayout(layout);
		
		layout.putConstraint(SpringLayout.NORTH, componentPanel, 5, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, componentPanel, -5, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, componentPanel, -5, SpringLayout.EAST, this.getContentPane());
		
		layout.putConstraint(SpringLayout.NORTH, dscroll, 5, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, dscroll, 5, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, dscroll, -5, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, dscroll, -5, SpringLayout.WEST, componentPanel);
		
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
			System.exit(-1);
		}
		File schemafile = new File(schema);
		File treefile = new File(tree);
		
		try {
			XMLTechTreeLoader loader = new XMLTechTreeLoader(schemafile);
			
			(new ShipDesigner<String>(loader.loadTree(treefile))).setVisible(true);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		//(new ShipDesigner<String>(ComponentLoader.loadComponents(null))).setVisible(true);
	}
}
