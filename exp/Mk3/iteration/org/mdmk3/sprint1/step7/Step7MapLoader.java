package org.mdmk3.sprint1.step7;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.ImageMapLoader;
import org.mdmk3.core.loader.NullTileNode;

public class Step7MapLoader extends ImageMapLoader<Step7Attributes> {

	@Override
	public void initializePosition(Node<Step7Attributes> node, int x, int y) {
		Dimension d = ((Step7Converter)this.getConverter()).getTileSize();
		
		node.getAttributes().setPosition(x * d.width, y * d.height);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Node<Step7Attributes> createDomainNode(
			Node<Step7Attributes> first, 
			Node<Step7Attributes> second, 
			Node<Step7Attributes> third,
			Node<Step7Attributes> fourth)
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
		
		Step7Attributes atts = new Step7Attributes(null,Step7ObjectType.DOMAIN);
		atts.setBounds(bounds);
		
		if(emptyFirst && emptySecond && emptyThird && emptyFourth){
			return new NullTileNode<Step7Attributes>(atts);
		}else{
			DefaultNode<Step7Attributes> node = new DefaultNode<Step7Attributes>(atts);
			if(!emptyFirst)node.addChild(first);
			if(!emptySecond) node.addChild(second);
			if(!emptyThird) node.addChild(third);
			if(!emptyFourth) node.addChild(fourth);
			
			if(!(emptyFirst || emptySecond || emptyThird || emptyFourth)){
				boolean mergeFirst = first.hasCapability(Step7BouncingDecorator.class);
				boolean mergeSecond = second != null && second.hasCapability(Step7BouncingDecorator.class);
				boolean mergeThird = third != null && third.hasCapability(Step7BouncingDecorator.class);
				boolean mergeFourth = fourth != null && fourth.hasCapability(Step7BouncingDecorator.class);
				
				if(mergeFirst && mergeSecond && mergeThird && mergeFourth){
					Step7BouncingDecorator bFirst = first.getDecorator(Step7BouncingDecorator.class);
					Step7BouncingDecorator bSecond = second.getDecorator(Step7BouncingDecorator.class);
					Step7BouncingDecorator bThird = third.getDecorator(Step7BouncingDecorator.class);
					Step7BouncingDecorator bFourth = fourth.getDecorator(Step7BouncingDecorator.class);
					
					bFirst.merge(node, bSecond, bThird, bFourth);
				}
			}
			
			return node;
		}
	}

}
