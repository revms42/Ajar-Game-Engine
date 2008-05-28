package org.display;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import org.interaction.IEntity;

public interface IEnvironment<I,K> extends IEntity<K>{
	
	public boolean isOnScreen(Rectangle screen, int depth);
	public void draw(Graphics2D g2, Rectangle window);
	public void draw(Graphics2D g2, Rectangle window, int depth);
	
	public ITileFactory<I> getTileFactory();
	public void setTileFactory(ITileFactory<I> factory);
	
	public IEnvironmentContext<I> getEnvironmentContext();
	public void setEnvironmentContext(IEnvironmentContext<I> context);	
	
	public Vector<BufferedImage> getMaps();
}
