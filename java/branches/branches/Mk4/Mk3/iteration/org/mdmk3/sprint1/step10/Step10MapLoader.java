package org.mdmk3.sprint1.step10;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.ImageMapLoader;
import org.mdmk3.core.loader.NullTileNode;

public class Step10MapLoader extends ImageMapLoader<Step10Attributes> {

	@Override
	public void initializePosition(Node<Step10Attributes> node, int x, int y) {
		Dimension d = ((Step10Converter)this.getConverter()).getTileSize();
		
		node.getAttributes().setPosition(x * d.width, y * d.height);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Node<Step10Attributes> createDomainNode(
			Node<Step10Attributes> first, 
			Node<Step10Attributes> second, 
			Node<Step10Attributes> third,
			Node<Step10Attributes> fourth)
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
		
		Step10Attributes atts = new Step10Attributes(null,Step10ObjectType.DOMAIN);
		atts.setBounds(bounds);
		
		if(emptyFirst && emptySecond && emptyThird && emptyFourth){
			return new NullTileNode<Step10Attributes>(atts);
		}else{
			DefaultNode<Step10Attributes> node = new DefaultNode<Step10Attributes>(atts);
			if(!emptyFirst)node.addChild(first);
			if(!emptySecond) node.addChild(second);
			if(!emptyThird) node.addChild(third);
			if(!emptyFourth) node.addChild(fourth);
			
			if(!(emptyFirst || emptySecond || emptyThird || emptyFourth)){
				boolean mergeFirst = first.hasCapability(Step10BouncingDecorator.class);
				boolean mergeSecond = second != null && second.hasCapability(Step10BouncingDecorator.class);
				boolean mergeThird = third != null && third.hasCapability(Step10BouncingDecorator.class);
				boolean mergeFourth = fourth != null && fourth.hasCapability(Step10BouncingDecorator.class);
				
				if(mergeFirst && mergeSecond && mergeThird && mergeFourth){
					Step10BouncingDecorator bFirst = first.getDecorator(Step10BouncingDecorator.class);
					Step10BouncingDecorator bSecond = second.getDecorator(Step10BouncingDecorator.class);
					Step10BouncingDecorator bThird = third.getDecorator(Step10BouncingDecorator.class);
					Step10BouncingDecorator bFourth = fourth.getDecorator(Step10BouncingDecorator.class);
					
					bFirst.merge(node, bSecond, bThird, bFourth);
				}
			}
			
			return node;
		}
	}

}
