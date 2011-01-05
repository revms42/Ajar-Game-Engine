package org.mdmk3.sprint1.step6;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.ImageMapLoader;
import org.mdmk3.core.loader.NullTileNode;

public class Step6MapLoader extends ImageMapLoader<Step6Attributes> {

	@Override
	public void initializePosition(Node<Step6Attributes> node, int x, int y) {
		Dimension d = ((Step6Converter)this.getConverter()).getTileSize();
		
		node.getAttributes().setPosition(x * d.width, y * d.height);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Node<Step6Attributes> createDomainNode(
			Node<Step6Attributes> first, 
			Node<Step6Attributes> second, 
			Node<Step6Attributes> third,
			Node<Step6Attributes> fourth)
	{
		if(first == null && second == null && third == null && fourth == null) return null;
		
		boolean emptyFirst = first instanceof NullTileNode;
		boolean emptySecond = second instanceof NullTileNode;
		boolean emptyThird = third instanceof NullTileNode;
		boolean emptyFourth = fourth instanceof NullTileNode;
		
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
		
		if(emptyFirst && emptySecond && emptyThird && emptyFourth){
			return new NullTileNode<Step6Attributes>(atts);
		}else{
			DefaultNode<Step6Attributes> node = new DefaultNode<Step6Attributes>(atts);
			if(!emptyFirst)node.addChild(first);
			if(second != null && !emptySecond) node.addChild(second);
			if(third != null && !emptyThird) node.addChild(third);
			if(fourth != null && !emptyFourth) node.addChild(fourth);
			
			if(!(emptyFirst || emptySecond || emptyThird || emptyFourth)){
				boolean mergeFirst = first.hasCapability(Step6BouncingDecorator.class);
				boolean mergeSecond = second.hasCapability(Step6BouncingDecorator.class);
				boolean mergeThird = third.hasCapability(Step6BouncingDecorator.class);
				boolean mergeFourth = fourth.hasCapability(Step6BouncingDecorator.class);
				
				if(mergeFirst && mergeSecond && mergeThird && mergeFourth){
					Step6BouncingDecorator bFirst = first.getDecorator(Step6BouncingDecorator.class);
					Step6BouncingDecorator bSecond = second.getDecorator(Step6BouncingDecorator.class);
					Step6BouncingDecorator bThird = third.getDecorator(Step6BouncingDecorator.class);
					Step6BouncingDecorator bFourth = fourth.getDecorator(Step6BouncingDecorator.class);
					
					bFirst.merge(node, bSecond, bThird, bFourth);
				}
			}
			
			return node;
		}
	}

}
