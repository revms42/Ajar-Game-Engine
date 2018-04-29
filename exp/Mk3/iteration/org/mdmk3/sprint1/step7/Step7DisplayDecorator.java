package org.mdmk3.sprint1.step7;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImageOp;

import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.image.AbstractTileDisplayable;

public class Step7DisplayDecorator extends AbstractTileDisplayable<Integer,Step7Attributes> {

	public Step7DisplayDecorator(Node<Step7Attributes> node) {
		super(node);
	}

	@Override
	public AffineTransform getTransform() {
		//Not used.
		return AffineTransform.getTranslateInstance(getAttributes().getXPos(),getAttributes().getYPos());
	}

	@Override
	public boolean needsDisplayUpdate() {
		return true;
	}

	@Override
	public Integer getImageCode() {
		return getAttributes().getCurrentFrame();
	}

	@Override
	public BufferedImageOp getFilter() {
		return null;
	}

}
