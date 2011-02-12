package org.mdmk3.sprint1.step8;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Node;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.loader.MergingCollidable;
import org.mdmk3.core.logic.Action;

public class Step8BouncingDecorator extends MergingCollidable<Step8Attributes> {

	protected AffineTransform trans;
	
	public Step8BouncingDecorator(Node<Step8Attributes> node) {
		super(node);
		trans = AffineTransform.getTranslateInstance(0, 0);
	}
	
	@Override
	public Action collideWith(Collidable<Step8Attributes> s) {
		if(hasCapability(Step8Entity.class)){
			trans.setToTranslation(getAttributes().getXVel(), getAttributes().getYVel());
			
			Rectangle2D bounds = s.getAttributes().getCollisionSurface().getBounds2D();
			Rectangle2D ball = trans.createTransformedShape(getAttributes().getCollisionSurface()).getBounds2D();
			
			//There are two object types to worry about: Walls and boxes.
			switch(s.getAttributes().getType()){
			case BOX:
				if(ball.intersects(bounds)){
					return outcodeBounce(bounds,ball);
				}else if(bounds.contains(ball)){
					return boundryBounce(bounds,ball);
				}else{
					return null;
				}
			case LEVEL:
				if(ball.intersects(bounds)){
					return boundryBounce(bounds,ball);
				}else if(!bounds.contains(ball)){
					return outcodeBounce(bounds,ball);
				}else{
					return null;
				}
			default:
				return null;
			}
		}else{
			return null;
		}
	}

	private Step8ActionType outcodeBounce(Rectangle2D bounds, Rectangle2D ball){
		int outcode = bounds.outcode(ball.getCenterX(),ball.getCenterY());
		switch(outcode){
			case Rectangle2D.OUT_LEFT: //1
				return Step8ActionType.BOUNCE_H;
			case Rectangle2D.OUT_TOP: //2
				return Step8ActionType.BOUNCE_V;
			case Rectangle2D.OUT_LEFT + Rectangle2D.OUT_TOP: //3
				return Step8ActionType.BOUNCE_D;
			case Rectangle2D.OUT_RIGHT: //4
				return Step8ActionType.BOUNCE_H;
			case Rectangle2D.OUT_RIGHT + Rectangle2D.OUT_TOP: //6
				return Step8ActionType.BOUNCE_D;
			case Rectangle2D.OUT_BOTTOM: //8
				return Step8ActionType.BOUNCE_V;
			default: //9+
				return Step8ActionType.BOUNCE_D;
		}
	}
	
	private Step8ActionType boundryBounce(Rectangle2D bounds, Rectangle2D ball){
		if(ball.getMaxX() > bounds.getMaxX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step8ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step8ActionType.BOUNCE_D;
			}else{
				return Step8ActionType.BOUNCE_H;
			}
		}else if(ball.getMinX() < bounds.getMinX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step8ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step8ActionType.BOUNCE_D;
			}else{
				return Step8ActionType.BOUNCE_H;
			}
		}else{
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step8ActionType.BOUNCE_V;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step8ActionType.BOUNCE_V;
			}else{
				return null;
			}
		}
	}
	
	@Override
	public boolean needsCollisionCheck() {
		return true;
	}

	@Override
	public boolean canMergeWith(MergingCollidable<Step8Attributes> target) {
		return target.getAttributes().getType() == this.getAttributes().getType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.mdmk3.core.loader.MergingCollidable#merge(org.mdmk3.core.Node, org.mdmk3.core.loader.MergingCollidable<A>[])
	 * 
	 * This assumes if A.canMergeWith(B) == true and A.canMergeWith(C) == true then B.canMergeWith(C) can never be false.
	 */
	@Override
	public void merge(Node<Step8Attributes> parent, MergingCollidable<Step8Attributes>... children) {
		for(MergingCollidable<Step8Attributes> child : children){
			if(!canMergeWith(child)){
				return;
			}
		}
		
		/*Step8Attributes atts = parent.getAttributes();
		Area a = new Area(atts.getCollisionSurface());*/
		Area a = new Area();
		
		for(MergingCollidable<Step8Attributes> child : children){
			a.add(new Area(child.getAttributes().getCollisionSurface()));
			child.removeDecorator(Step8BouncingDecorator.class);
		}
		
		Step8Attributes atts = parent.getAttributes();
		atts.setShape(a);
		atts.setType(Step8ObjectType.BOX);
		new Step8BouncingDecorator(parent);
	}

}
