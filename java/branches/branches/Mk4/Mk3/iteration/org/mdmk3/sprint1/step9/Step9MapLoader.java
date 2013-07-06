package org.mdmk3.sprint1.step9;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.ImageMapLoader;
import org.mdmk3.core.loader.NullTileNode;

public class Step9MapLoader extends ImageMapLoader<Step9Attributes> {

	@Override
	public void initializePosition(Node<Step9Attributes> node, int x, int y) {
		Dimension d = ((Step9Converter)this.getConverter()).getTileSize();
		
		node.getAttributes().setPosition(x * d.width, y * d.height);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Node<Step9Attributes> createDomainNode(
			Node<Step9Attributes> first, 
			Node<Step9Attributes> second, 
			Node<Step9Attributes> third,
			Node<Step9Attributes> fourth)
	{
		if(first == null && second == null && third == null && fourth == null) return null;
		
		boolean emptyFirst = first == null || first instanceof NullTileNode;
		boolean emptySecond = second == null || second instanceof NullTileNode;
		boolean emptyThird = third == null || third instanceof NullTileNode;
		boolean emptyFourth = fourth == null || fourth instanceof NullTileNode;
		
		double width = first.getAttributes().getBounds().getWidth();
		double height = first.getAttributes().getBounds().getHeight();
		
		width = second != null ? second.getAttributes().getBounds().getWidth() + width : width;
		height = third != null ? third.getAttributes().getBounds().getHeight() + height: height;
		
		Rectangle2D.Double bounds = 
			new Rectangle2D.Double(
					first.getAttributes().getXPos(),
					first.getAttributes().getYPos(),
					width,
					height
			);
		
		Step9Attributes atts = new Step9Attributes(null,Step9ObjectType.DOMAIN);
		atts.setBounds(bounds);
		
		if(emptyFirst && emptySecond && emptyThird && emptyFourth){
			return new NullTileNode<Step9Attributes>(atts);
		}else{
			DefaultNode<Step9Attributes> node = new DefaultNode<Step9Attributes>(atts);
			if(!emptyFirst)node.addChild(first);
			if(!emptySecond) node.addChild(second);
			if(!emptyThird) node.addChild(third);
			if(!emptyFourth) node.addChild(fourth);
			
			if(!(emptyFirst || emptySecond || emptyThird || emptyFourth)){
				boolean mergeFirst = first.hasCapability(Step9BouncingDecorator.class);
				boolean mergeSecond = second != null && second.hasCapability(Step9BouncingDecorator.class);
				boolean mergeThird = third != null && third.hasCapability(Step9BouncingDecorator.class);
				boolean mergeFourth = fourth != null && fourth.hasCapability(Step9BouncingDecorator.class);
				
				if(mergeFirst && mergeSecond && mergeThird && mergeFourth){
					Step9BouncingDecorator bFirst = first.getDecorator(Step9BouncingDecorator.class);
					Step9BouncingDecorator bSecond = second.getDecorator(Step9BouncingDecorator.class);
					Step9BouncingDecorator bThird = third.getDecorator(Step9BouncingDecorator.class);
					Step9BouncingDecorator bFourth = fourth.getDecorator(Step9BouncingDecorator.class);
					
					bFirst.merge(node, bSecond, bThird, bFourth);
				}
			}
			
			return node;
		}
	}

}
