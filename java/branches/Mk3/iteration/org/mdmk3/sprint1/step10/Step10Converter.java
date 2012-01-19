package org.mdmk3.sprint1.step10;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
import org.mdmk3.core.disp2d.image.TilePalette;
import org.mdmk3.core.loader.Converter;
import org.mdmk3.core.loader.NullTileNode;

public class Step10Converter implements Converter<Integer, Step10Attributes> {

	private final TilePalette<Integer> palette;
	private Dimension tileSize;
	
	public Step10Converter(TilePalette<Integer> palette, Dimension tileSize){
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
	public Node<Step10Attributes> toNode(Integer value) {
		Step10Attributes atts = null;
		
		if(value != null){
			
			if(value == Color.BLUE.getRGB()){
				atts = new Step10Attributes(new Rectangle(tileSize), Step10ObjectType.POWER_UP);
			}else{
				atts = new Step10Attributes(new Rectangle(tileSize), Step10ObjectType.BOX);
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
				return new NullTileNode<Step10Attributes>(atts);
			}
			
			Step10DisplayDecorator dd = new Step10DisplayDecorator(new Step10BouncingDecorator(new DefaultNode<Step10Attributes>(atts)));
			dd.setProvider(palette);
			return dd;
		}else{
			return null;
		}
	}

}
