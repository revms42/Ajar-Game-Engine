package space.ui.designer;

import java.awt.datatransfer.Transferable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;

import space.model.component.IComponent;

public class ComponentTransferHandler<I> extends TransferHandler {
	private static final long serialVersionUID = -6955368264160235901L;
	
	private IComponent<I> component;
	
	private Vector<ICompPlacementEventListener> placementListener;
	
	public void addPlacementEventListener(ICompPlacementEventListener listener){
		if(placementListener == null){
			placementListener = new Vector<ICompPlacementEventListener>();
		}
		
		placementListener.add(listener);
	}
	
	public void removePlacementEventListener(ICompPlacementEventListener listener){
		if(placementListener != null){
			placementListener.remove(listener);
		}
	}
	
	public void removeAllPlacementEventListeners(){
		if(placementListener != null){
			placementListener.removeAllElements();
		}
	}
	
	public boolean canImport(TransferHandler.TransferSupport info) {
		return info.isDataFlavorSupported(TransferableComponent.COMP_FLAVOR);
	}
	
	@SuppressWarnings("unchecked")
	protected Transferable createTransferable(JComponent c) {
		if(c instanceof IComponentTransferer){
			IComponentTransferer<I> source = (IComponentTransferer<I>)c;
			component = source.getSelectedComponent();
			
			return new TransferableComponent(component);
		}else if(c instanceof JTree){
			JTree t = (JTree)c;
			
			Object o = t.getLastSelectedPathComponent();
			if(o instanceof LibraryPanel.ComponentNode){
				LibraryPanel.ComponentNode n = (LibraryPanel.ComponentNode)o;
				return new TransferableComponent((IComponent)n.getUserObject());
			}
		}
		System.out.println("Retrieval failed for + " + c);
		return null;
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
		
		if(placementListener != null){
			CompPlacementEvent event = new CompPlacementEvent(info.getComponent(),0,data);
			
			for(ICompPlacementEventListener i : placementListener){
				i.componentPlaced(event);
			}
		}
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
