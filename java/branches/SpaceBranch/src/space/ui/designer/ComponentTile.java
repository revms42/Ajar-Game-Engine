package space.ui.designer;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import space.model.ships.IComponent;

public class ComponentTile<I> extends JPanel implements IComponentTransferer<I> {
	private static final long serialVersionUID = 434787520854392137L;
	
	private IComponent<I> component;

	public ComponentTile(ComponentTransferHandler<I> handler){
		this.setTransferHandler(handler);
	}
	
	@Override
	public IComponent<I> getSelectedComponent() {
		return component;
	}

	@Override
	public boolean isFinite() {
		return true;
	}

	@Override
	public void removeComponent(IComponent<I> component) {
		component = null;
	}

	@Override
	public void setSelectedComponent(IComponent<I> component) {
		this.component = component;
	}

	public void paintComponent(Graphics g){
		if(component != null){
			component.getDisplayFactory().display(component, (Graphics2D)g);
		}else{
			super.paintComponent(g);
		}
	}
}
