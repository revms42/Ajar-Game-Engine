package org.control;

import java.awt.AWTEvent;
import java.util.Collection;
import java.util.Vector;

import org.interaction.ConditionPalette;

public interface IController<C,K> {

	public ConditionPalette<C,?,K> getPalette();
	public void setPalette(ConditionPalette<C,?,K> palette);
	
	public void mapTarget(IControllable<C,K> target);
	public boolean unMapTarget(IControllable<C,K> target);
	
	public Collection<IControllable<C,K>> getTargets();
	
	public Vector<AWTEvent> getEvents();
	public void flushEvents();
	
	public void evaluateNextEvent();
	public void evaluateAllEvents();
}
