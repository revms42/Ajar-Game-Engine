package org.mdmk3.sprint1.step10;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImageOp;

import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.image.AbstractTileDisplayable;

public class Step10DisplayDecorator extends AbstractTileDisplayable<Integer,Step10Attributes> {

	public Step10DisplayDecorator(Node<Step10Attributes> node) {
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
