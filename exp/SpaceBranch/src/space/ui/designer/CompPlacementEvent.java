package space.ui.designer;

import java.awt.AWTEvent;

import space.model.component.IComponent;

public class CompPlacementEvent extends AWTEvent {
	private static final long serialVersionUID = 5006793784441394078L;
	
	public final IComponent<?> component;
	
	public CompPlacementEvent(Object arg0, int arg1, IComponent<?> component) {
		super(arg0, arg1);
		
		this.component = component;
	}

}
