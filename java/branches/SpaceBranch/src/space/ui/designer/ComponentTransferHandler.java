package space.ui.designer;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import space.model.ships.IComponent;

public class ComponentTransferHandler<I> extends TransferHandler {
	private static final long serialVersionUID = -6955368264160235901L;
	
	private IComponent<I> component;
	
	public boolean canImport(TransferHandler.TransferSupport info) {
		if (!info.isDataFlavorSupported(TransferableComponent.COMP_FLAVOR)) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	protected Transferable createTransferable(JComponent c) {
		IComponentTransferer<I> source = (IComponentTransferer<I>)c;
		component = source.getSelectedComponent();
		
		return new TransferableComponent(component);
	}
	
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY_OR_MOVE;
	}
	
	@SuppressWarnings("unchecked")
	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop() || !(info.getComponent() instanceof IComponentTransferer)) {
			return false;
		}

		IComponentTransferer<I> destination = (IComponentTransferer<I>)info.getComponent();
		
		// Get the string that is being dropped.
		Transferable t = info.getTransferable();
		IComponent<I> data;
		try {
			data = (IComponent<I>)t.getTransferData(TransferableComponent.COMP_FLAVOR);
		} 
		catch (Exception e) { return false; }
		
		destination.setSelectedComponent(data);
		
		return true;
    }
	
	@SuppressWarnings("unchecked")
	protected void exportDone(JComponent c, Transferable data, int action) {
		if (c instanceof IComponentTransferer) {
			IComponentTransferer<I> source = (IComponentTransferer<I>)c;
			
			if (action == TransferHandler.MOVE && source.isFinite()) {
				source.removeComponent(component);
			}
		}
	}


}
