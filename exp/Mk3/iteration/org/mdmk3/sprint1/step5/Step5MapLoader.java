package org.mdmk3.sprint1.step5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.TileMapLoader;

public class Step5MapLoader extends TileMapLoader<Color,Step5Attributes> {

	@Override
	public void initializePosition(Node<Step5Attributes> node, int x, int y) {
		Dimension d = ((Step5Converter)this.getConverter()).getTileSize();
		
		node.getAttributes().setPosition(x * d.width, y * d.height);
	}

	@Override
	public Node<Step5Attributes> createDomainNode(
			Node<Step5Attributes> first, 
			Node<Step5Attributes> second, 
			Node<Step5Attributes> third,
			Node<Step5Attributes> fourth)
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
		
		Step5Attributes atts = new Step5Attributes(shape,Step5ObjectType.DOMAIN,null);
		DefaultNode<Step5Attributes> node = new DefaultNode<Step5Attributes>(atts);
		
		node.addChild(first);
		if(second != null) node.addChild(second);
		if(third != null) node.addChild(third);
		if(fourth != null) node.addChild(fourth);
		
		return node;
	}

}
