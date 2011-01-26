package org.mdmk3.sprint1.step7;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Node;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.loader.MergingCollidable;
import org.mdmk3.core.logic.Action;

public class Step7BouncingDecorator extends MergingCollidable<Step7Attributes> {

	protected AffineTransform trans;
	
	public Step7BouncingDecorator(Node<Step7Attributes> node) {
		super(node);
		trans = AffineTransform.getTranslateInstance(0, 0);
	}
	
	@Override
	public Action collideWith(Collidable<Step7Attributes> s) {
		if(hasCapability(Step7Entity.class)){
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

	private Step7ActionType outcodeBounce(Rectangle2D bounds, Rectangle2D ball){
		int outcode = bounds.outcode(ball.getCenterX(),ball.getCenterY());
		switch(outcode){
			case Rectangle2D.OUT_LEFT: //1
				return Step7ActionType.BOUNCE_H;
			case Rectangle2D.OUT_TOP: //2
				return Step7ActionType.BOUNCE_V;
			case Rectangle2D.OUT_LEFT + Rectangle2D.OUT_TOP: //3
				return Step7ActionType.BOUNCE_D;
			case Rectangle2D.OUT_RIGHT: //4
				return Step7ActionType.BOUNCE_H;
			case Rectangle2D.OUT_RIGHT + Rectangle2D.OUT_TOP: //6
				return Step7ActionType.BOUNCE_D;
			case Rectangle2D.OUT_BOTTOM: //8
				return Step7ActionType.BOUNCE_V;
			default: //9+
				return Step7ActionType.BOUNCE_D;
		}
	}
	
	private Step7ActionType boundryBounce(Rectangle2D bounds, Rectangle2D ball){
		if(ball.getMaxX() > bounds.getMaxX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step7ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step7ActionType.BOUNCE_D;
			}else{
				return Step7ActionType.BOUNCE_H;
			}
		}else if(ball.getMinX() < bounds.getMinX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step7ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step7ActionType.BOUNCE_D;
			}else{
				return Step7ActionType.BOUNCE_H;
			}
		}else{
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step7ActionType.BOUNCE_V;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step7ActionType.BOUNCE_V;
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
	public boolean canMergeWith(MergingCollidable<Step7Attributes> target) {
		return target.getAttributes().getType() == this.getAttributes().getType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.mdmk3.core.loader.MergingCollidable#merge(org.mdmk3.core.Node, org.mdmk3.core.loader.MergingCollidable<A>[])
	 * 
	 * This assumes if A.canMergeWith(B) == true and A.canMergeWith(C) == true then B.canMergeWith(C) can never be false.
	 */
	@Override
	public void merge(Node<Step7Attributes> parent, MergingCollidable<Step7Attributes>... children) {
		for(MergingCollidable<Step7Attributes> child : children){
			if(!canMergeWith(child)){
				return;
			}
		}
		
		/*Step7Attributes atts = parent.getAttributes();
		Area a = new Area(atts.getCollisionSurface());*/
		Area a = new Area();
		
		for(MergingCollidable<Step7Attributes> child : children){
			a.add(new Area(child.getAttributes().getCollisionSurface()));
			child.removeDecorator(Step7BouncingDecorator.class);
		}
		
		Step7Attributes atts = parent.getAttributes();
		atts.setShape(a);
		new Step7BouncingDecorator(parent);
	}

}
