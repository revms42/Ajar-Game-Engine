package org.mdmk3.sprint1.step6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.loader.Converter;
import org.mdmk3.core.loader.NullTileNode;

public class Step6Converter implements Converter<Integer, Step6Attributes> {

	private Dimension tileSize;
	
	public Dimension getTileSize() {
		return tileSize;
	}
	public void setTileSize(Dimension tileSize) {
		this.tileSize = tileSize;
	}
	
	@Override
	public Node<Step6Attributes> toNode(Integer value) {
		Step6Attributes atts = null;
		
		if(value != null){
			atts = new Step6Attributes(new Rectangle(tileSize), Step6ObjectType.BOX,new Color(value));
			
			if(value != Color.BLACK.getRGB()){
				return new Step6DisplayDecorator(new Step6BouncingDecorator(new DefaultNode<Step6Attributes>(atts)));
			}else{
				return new NullTileNode<Step6Attributes>(atts);
			}
		}else{
			return null;
		}
	}

}
