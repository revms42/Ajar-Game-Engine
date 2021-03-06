package org.mdmk3.sprint1.step3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.AbstractDisplayable;

public class Step3DisplayDecorator extends AbstractDisplayable<Step3Attributes> {

	public Step3DisplayDecorator(Node<Step3Attributes> node) {
		super(node);
	}

	@Override
	public void updateDisplay(Graphics2D g2) {
		Color foreground = g2.getColor();
		g2.setColor(getAttributes().getColor());
		g2.fill(getTransform().createTransformedShape(getAttributes().getShape()));
		g2.setColor(foreground);
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

}
