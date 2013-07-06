package space.ui.designer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import space.model.component.IComponent;

public class ComponentTile<I> extends JPanel implements IComponentTransferer<I> {
	private static final long serialVersionUID = 434787520854392137L;
	
	public static final Dimension TILE_SIZE = new Dimension(48,48);
	
	private IComponent<I> component;

	public ComponentTile(ComponentTransferHandler<I> handler){
		this.setTransferHandler(handler);
		this.setBackground(Color.GRAY);
		this.setPreferredSize(TILE_SIZE);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
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
