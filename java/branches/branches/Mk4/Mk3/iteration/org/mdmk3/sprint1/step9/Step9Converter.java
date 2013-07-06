package org.mdmk3.sprint1.step9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.image.TilePalette;
import org.mdmk3.core.loader.Converter;
import org.mdmk3.core.loader.NullTileNode;

public class Step9Converter implements Converter<Integer, Step9Attributes> {

	private final TilePalette<Integer> palette;
	private Dimension tileSize;
	
	public Step9Converter(TilePalette<Integer> palette, Dimension tileSize){
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
	public Node<Step9Attributes> toNode(Integer value) {
		Step9Attributes atts = null;
		
		if(value != null){
			
			if(value == Color.BLUE.getRGB()){
				atts = new Step9Attributes(new Rectangle(tileSize), Step9ObjectType.POWER_UP);
			}else{
				atts = new Step9Attributes(new Rectangle(tileSize), Step9ObjectType.BOX);
			}
			
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
				return new NullTileNode<Step9Attributes>(atts);
			}
			
			Step9DisplayDecorator dd = new Step9DisplayDecorator(new Step9BouncingDecorator(new DefaultNode<Step9Attributes>(atts)));
			dd.setProvider(palette);
			return dd;
		}else{
			return null;
		}
	}

}
