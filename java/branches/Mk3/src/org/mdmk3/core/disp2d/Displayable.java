package org.mdmk3.core.disp2d;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Decorator;

public interface Displayable<A extends Attributes> extends Decorator<A> {
	
	/**
	 * Calls on this object to display itself.
	 * mstockbridge
	 * May 4, 2010
	 * @param 	g2	the {@link Graphics2D} context on which to display.
	 */
	public void updateDisplay(Graphics2D g2);
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public AffineTransform getTransform();
	
	/**
	 * 
	 * mstockbridge
	 * 13-Jun-10
	 * @return
	 */
	public boolean needsDisplayUpdate();
}
