package org.mdmk3.sprint1.step1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.AbstractDisplayable;

public class Step1DisplayDecorator extends AbstractDisplayable<Step1Attributes> {

	public Step1DisplayDecorator(Node<Step1Attributes> node) {
		super(node);
	}

	@Override
	public void updateDisplay(Graphics2D g2) {
		Color foreground = g2.getColor();
		g2.setColor(getAttributes().getC());
		g2.fill(getTransform().createTransformedShape(getAttributes().getShape()));
		g2.setColor(foreground);
	}

	@Override
	public AffineTransform getTransform() {
		return AffineTransform.getTranslateInstance(10.0d, 10.0d);
	}

	@Override
	public boolean needsDisplayUpdate() {
		return true;
	}

}
