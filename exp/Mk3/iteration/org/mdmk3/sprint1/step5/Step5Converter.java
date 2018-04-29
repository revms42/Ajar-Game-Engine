package org.mdmk3.sprint1.step5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.Converter;

public class Step5Converter implements Converter<Color, Step5Attributes> {

	private Dimension tileSize;
	
	public Dimension getTileSize() {
		return tileSize;
	}
	public void setTileSize(Dimension tileSize) {
		this.tileSize = tileSize;
	}
	@Override
	public Node<Step5Attributes> toNode(Color value) {
		Step5Attributes atts = null;
		if(value != null){
			atts = new Step5Attributes(new Rectangle(tileSize), Step5ObjectType.BOX,value);
			
			return new Step5DisplayDecorator(new Step5BouncingDecorator(new DefaultNode<Step5Attributes>(atts)));
		}else{
			atts = new Step5Attributes(new Rectangle(tileSize), Step5ObjectType.DOMAIN,null);
			
			return new DefaultNode<Step5Attributes>(atts);
		}
	}

}
