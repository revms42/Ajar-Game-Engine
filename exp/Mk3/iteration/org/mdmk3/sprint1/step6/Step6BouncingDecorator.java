package org.mdmk3.sprint1.step6;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Node;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.loader.MergingCollidable;
import org.mdmk3.core.logic.Action;

public class Step6BouncingDecorator extends MergingCollidable<Step6Attributes> {

	protected AffineTransform trans;
	
	public Step6BouncingDecorator(Node<Step6Attributes> node) {
		super(node);
		trans = AffineTransform.getTranslateInstance(0, 0);
	}
	
	@Override
	public Action collideWith(Collidable<Step6Attributes> s) {
		if(hasCapability(Step6Entity.class)){
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

	private Step6ActionType outcodeBounce(Rectangle2D bounds, Rectangle2D ball){
		int outcode = bounds.outcode(ball.getCenterX(),ball.getCenterY());
		switch(outcode){
			case Rectangle2D.OUT_LEFT: //1
				return Step6ActionType.BOUNCE_H;
			case Rectangle2D.OUT_TOP: //2
				return Step6ActionType.BOUNCE_V;
			case Rectangle2D.OUT_LEFT + Rectangle2D.OUT_TOP: //3
				return Step6ActionType.BOUNCE_D;
			case Rectangle2D.OUT_RIGHT: //4
				return Step6ActionType.BOUNCE_H;
			case Rectangle2D.OUT_RIGHT + Rectangle2D.OUT_TOP: //6
				return Step6ActionType.BOUNCE_D;
			case Rectangle2D.OUT_BOTTOM: //8
				return Step6ActionType.BOUNCE_V;
			default: //9+
				return Step6ActionType.BOUNCE_D;
		}
	}
	
	private Step6ActionType boundryBounce(Rectangle2D bounds, Rectangle2D ball){
		if(ball.getMaxX() > bounds.getMaxX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step6ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step6ActionType.BOUNCE_D;
			}else{
				return Step6ActionType.BOUNCE_H;
			}
		}else if(ball.getMinX() < bounds.getMinX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step6ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step6ActionType.BOUNCE_D;
			}else{
				return Step6ActionType.BOUNCE_H;
			}
		}else{
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step6ActionType.BOUNCE_V;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step6ActionType.BOUNCE_V;
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
	public boolean canMergeWith(MergingCollidable<Step6Attributes> target) {
		return target.getAttributes().getType() == this.getAttributes().getType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.mdmk3.core.loader.MergingCollidable#merge(org.mdmk3.core.Node, org.mdmk3.core.loader.MergingCollidable<A>[])
	 * 
	 * This assumes if A.canMergeWith(B) == true and A.canMergeWith(C) == true then B.canMergeWith(C) can never be false.
	 */
	@Override
	public void merge(Node<Step6Attributes> parent, MergingCollidable<Step6Attributes>... children) {
		for(MergingCollidable<Step6Attributes> child : children){
			if(!canMergeWith(child)){
				return;
			}
		}
		
		Step6Attributes atts = parent.getAttributes();
		Area a = new Area(atts.getCollisionSurface());
		
		for(MergingCollidable<Step6Attributes> child : children){
			a.add(new Area(child.getAttributes().getCollisionSurface()));
			child.removeDecorator(Step6BouncingDecorator.class);
		}
		
		atts.setShape(a);
		new Step6BouncingDecorator(parent);
	}

}
