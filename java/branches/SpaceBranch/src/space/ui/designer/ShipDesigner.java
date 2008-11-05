package space.ui.designer;

import java.util.Vector;

import javax.swing.JFrame;

import space.model.ships.IComponent;

public class ShipDesigner extends JFrame {
	private static final long serialVersionUID = 9207357928566998058L;
	
	private final DesignPanel designPanel;
	private final LibraryPanel componentPanel;
	private final Vector<IComponent<?>> components;
	
	protected ShipDesigner(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		components = new Vector<IComponent<?>>();
		
		designPanel = new DesignPanel();
		componentPanel = new LibraryPanel(components);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
