package space.ui.designer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import space.model.component.IComponent;

public class TransferableComponent implements Transferable{

	private final IComponent<?> component;
	
	public final static DataFlavor COMP_FLAVOR = ShipDesigner.COMP_FLAVOR;
	
	private final static DataFlavor[] flavors = {COMP_FLAVOR};
	
	public TransferableComponent(IComponent<?> component){
		this.component = component;
	}

	@Override
	public IComponent<?> getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException {
		return component;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor arg0) {
		return arg0 == COMP_FLAVOR ? true : false;
	}
}
