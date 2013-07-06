package org.mdmk3.sprint1.step7;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.image.TilePalette;
import org.mdmk3.core.loader.Converter;
import org.mdmk3.core.loader.NullTileNode;

public class Step7Converter implements Converter<Integer, Step7Attributes> {

	private final TilePalette<Integer> palette;
	private Dimension tileSize;
	
	public Step7Converter(TilePalette<Integer> palette, Dimension tileSize){
		this.palette = palette;
		this.tileSize = tileSize;
	}
	
	public Dimension getTileSize() {
		return tileSize;
	}
	public void setTileSize(Dimension tileSize) {
		this.tileSize = tileSize;
	}
	
	@Override
	public Node<Step7Attributes> toNode(Integer value) {
		Step7Attributes atts = null;
		
		if(value != null){
			atts = new Step7Attributes(new Rectangle(tileSize), Step7ObjectType.BOX);
			
			if(value == Color.RED.getRGB()){
				atts.setCurrentFrame(0);
			}else if(value == Color.YELLOW.getRGB()){
				atts.setCurrentFrame(1);
			}else if(value == Color.GREEN.getRGB()){
				atts.setCurrentFrame(2);
			}else if(value == Color.CYAN.getRGB()){
				atts.setCurrentFrame(3);
			}else if(value == Color.BLUE.getRGB()){
				atts.setCurrentFrame(4);
			}else if(value == Color.MAGENTA.getRGB()){
				atts.setCurrentFrame(5);
			}else{
				return new NullTileNode<Step7Attributes>(atts);
			}
			
			Step7DisplayDecorator dd = new Step7DisplayDecorator(new Step7BouncingDecorator(new DefaultNode<Step7Attributes>(atts)));
			dd.setProvider(palette);
			return dd;
		}else{
			return null;
		}
	}

}
