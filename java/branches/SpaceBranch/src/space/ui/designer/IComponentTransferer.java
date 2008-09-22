package space.ui.designer;

import space.model.ships.IComponent;

public interface IComponentTransferer {

	public IComponent getSelectedComponent();
	public void setSelectedComponent(IComponent component);
	
	public boolean isFinite();
	public void removeComponent(IComponent component);
}
