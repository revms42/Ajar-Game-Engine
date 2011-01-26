package org.mdmk3.core.disp2d.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImageOp;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.AbstractDisplayable;

public abstract class AbstractTileDisplayable<I,A extends Attributes> extends AbstractDisplayable<A> {

	protected TileProvider<I> provider;
	
	public AbstractTileDisplayable(Node<A> node) {
		super(node);
	}

	@Override
	public void updateDisplay(Graphics2D g2) {
		provider.drawImage(getImageCode(), g2, getTransform(), getFilter());
	}
	
	public abstract I getImageCode();
	
	public abstract BufferedImageOp getFilter();

	public TileProvider<I> getProvider() {
		return provider;
	}

	public void setProvider(TileProvider<I> provider) {
		this.provider = provider;
	}
}
