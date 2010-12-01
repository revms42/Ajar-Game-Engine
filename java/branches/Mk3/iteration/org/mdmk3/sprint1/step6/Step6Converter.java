package org.mdmk3.sprint1.step6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.Converter;

public class Step6Converter implements Converter<Color, Step6Attributes> {

	private Dimension tileSize;
	
	public Dimension getTileSize() {
		return tileSize;
	}
	public void setTileSize(Dimension tileSize) {
		this.tileSize = tileSize;
	}
	@Override
	public Node<Step6Attributes> toNode(Color value) {
		Step6Attributes atts = null;
		if(value != null){
			atts = new Step6Attributes(new Rectangle(tileSize), Step6ObjectType.BOX,value);
			
			return new Step6DisplayDecorator(new Step6BouncingDecorator(new DefaultNode<Step6Attributes>(atts)));
		}else{
			atts = new Step6Attributes(new Rectangle(tileSize), Step6ObjectType.DOMAIN,null);
			
			return new DefaultNode<Step6Attributes>(atts);
		}
	}

}
