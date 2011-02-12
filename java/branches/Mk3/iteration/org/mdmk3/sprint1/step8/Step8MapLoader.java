package org.mdmk3.sprint1.step8;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.ImageMapLoader;
import org.mdmk3.core.loader.NullTileNode;

public class Step8MapLoader extends ImageMapLoader<Step8Attributes> {

	@Override
	public void initializePosition(Node<Step8Attributes> node, int x, int y) {
		Dimension d = ((Step8Converter)this.getConverter()).getTileSize();
		
		node.getAttributes().setPosition(x * d.width, y * d.height);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Node<Step8Attributes> createDomainNode(
			Node<Step8Attributes> first, 
			Node<Step8Attributes> second, 
			Node<Step8Attributes> third,
			Node<Step8Attributes> fourth)
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
		
		Step8Attributes atts = new Step8Attributes(null,Step8ObjectType.DOMAIN);
		atts.setBounds(bounds);
		
		if(emptyFirst && emptySecond && emptyThird && emptyFourth){
			return new NullTileNode<Step8Attributes>(atts);
		}else{
			DefaultNode<Step8Attributes> node = new DefaultNode<Step8Attributes>(atts);
			if(!emptyFirst)node.addChild(first);
			if(!emptySecond) node.addChild(second);
			if(!emptyThird) node.addChild(third);
			if(!emptyFourth) node.addChild(fourth);
			
			if(!(emptyFirst || emptySecond || emptyThird || emptyFourth)){
				boolean mergeFirst = first.hasCapability(Step8BouncingDecorator.class);
				boolean mergeSecond = second != null && second.hasCapability(Step8BouncingDecorator.class);
				boolean mergeThird = third != null && third.hasCapability(Step8BouncingDecorator.class);
				boolean mergeFourth = fourth != null && fourth.hasCapability(Step8BouncingDecorator.class);
				
				if(mergeFirst && mergeSecond && mergeThird && mergeFourth){
					Step8BouncingDecorator bFirst = first.getDecorator(Step8BouncingDecorator.class);
					Step8BouncingDecorator bSecond = second.getDecorator(Step8BouncingDecorator.class);
					Step8BouncingDecorator bThird = third.getDecorator(Step8BouncingDecorator.class);
					Step8BouncingDecorator bFourth = fourth.getDecorator(Step8BouncingDecorator.class);
					
					bFirst.merge(node, bSecond, bThird, bFourth);
				}
			}
			
			return node;
		}
	}

}
