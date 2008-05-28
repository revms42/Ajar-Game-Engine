package org.display;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.interaction.IEntity;

public interface IDisplayable<I,K> extends IEntity<K>{
	
	public boolean isOnScreen(Rectangle screen);
	public void draw(Graphics2D g2);
	
	public IDisplayFactory<I,K> getDisplayFactory();
	public void setDisplayFactory(IDisplayFactory<I,K> factory);
	
	public IDisplayContext<I,K> getDisplayContext();
	public void setDisplayContext(IDisplayContext<I,K> context);
	
	public int getDisplayDepth();
}
