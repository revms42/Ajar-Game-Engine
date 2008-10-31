package space.ui.designer;

import space.model.ships.IComponent;

public interface IComponentTransferer<I> {

	public IComponent<I> getSelectedComponent();
	public void setSelectedComponent(IComponent<I> component);
	
	public boolean isFinite();
	public void removeComponent(IComponent<I> component);
}
