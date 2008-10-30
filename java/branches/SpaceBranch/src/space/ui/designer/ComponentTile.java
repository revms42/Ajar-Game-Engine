package space.ui.designer;

import javax.swing.JPanel;

import space.model.ships.IComponent;

public class ComponentTile<I,K> extends JPanel implements IComponentTransferer {
	private static final long serialVersionUID = 434787520854392137L;
	
	private IComponent<?> component;

	public ComponentTile(ComponentTransferHandler handler){
		this.setTransferHandler(handler);
	}
	
	@Override
	public IComponent<?> getSelectedComponent() {
		return component;
	}

	@Override
	public boolean isFinite() {
		return true;
	}

	@Override
	public void removeComponent(IComponent<?> component) {
		component = null;
	}

	@Override
	public void setSelectedComponent(IComponent<?> component) {
		this.component = component;
	}

}
