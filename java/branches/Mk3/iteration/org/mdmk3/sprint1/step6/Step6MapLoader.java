package org.mdmk3.sprint1.step6;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.ImageMapLoader;

public class Step6MapLoader extends ImageMapLoader<Step6Attributes> {

	@Override
	public void initializePosition(Node<Step6Attributes> node, int x, int y) {
		Dimension d = ((Step6Converter)this.getConverter()).getTileSize();
		
		node.getAttributes().setPosition(x * d.width, y * d.height);
	}

	@Override
	public Node<Step6Attributes> createDomainNode(
			Node<Step6Attributes> first, 
			Node<Step6Attributes> second, 
			Node<Step6Attributes> third,
			Node<Step6Attributes> fourth)
	{
		double width = first.getAttributes().getShape().getBounds().getWidth();
		double height = first.getAttributes().getShape().getBounds().getHeight();
		
		width = second != null ? second.getAttributes().getShape().getBounds().getWidth() + width : width;
		height = third != null ? third.getAttributes().getShape().getBounds().getHeight() + height: height;
		
		Rectangle2D.Double shape = 
			new Rectangle2D.Double(
					first.getAttributes().getXPos(),
					first.getAttributes().getYPos(),
					width,
					height
			);
		
		Step6Attributes atts = new Step6Attributes(shape,Step6ObjectType.DOMAIN,null);
		DefaultNode<Step6Attributes> node = new DefaultNode<Step6Attributes>(atts);
		
		node.addChild(first);
		if(second != null) node.addChild(second);
		if(third != null) node.addChild(third);
		if(fourth != null) node.addChild(fourth);
		
		return node;
	}

}
