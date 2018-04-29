package org.mdmk3.sprint1.step8;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImageOp;

import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.image.AbstractTileDisplayable;

public class Step8DisplayDecorator extends AbstractTileDisplayable<Integer,Step8Attributes> {

	public Step8DisplayDecorator(Node<Step8Attributes> node) {
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
		if(getAttributes().isBlue()){
			return getAttributes().getCurrentFrame() + 100;
		}else{
			return getAttributes().getCurrentFrame();
		}
		
	}

	@Override
	public BufferedImageOp getFilter() {
		return null;
	}

}
