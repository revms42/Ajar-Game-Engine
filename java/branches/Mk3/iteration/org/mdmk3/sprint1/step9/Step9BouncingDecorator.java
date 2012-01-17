package org.mdmk3.sprint1.step9;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Node;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.loader.MergingCollidable;
import org.mdmk3.core.logic.Action;

public class Step9BouncingDecorator extends MergingCollidable<Step9Attributes> {

	protected AffineTransform trans;
	
	public Step9BouncingDecorator(Node<Step9Attributes> node) {
		super(node);
		trans = AffineTransform.getTranslateInstance(0, 0);
	}
	
	@Override
	public Action collideWith(Collidable<Step9Attributes> s) {
		if(hasCapability(Step9PlayerEntity.class)){
			trans.setToTranslation(getAttributes().getXVel(), getAttributes().getYVel());
			
			Rectangle2D bounds = s.getAttributes().getCollisionSurface().getBounds2D();
			Rectangle2D ball = trans.createTransformedShape(getAttributes().getCollisionSurface()).getBounds2D();
			
			switch(s.getAttributes().getType()){
			case BOX:
				if(ball.intersects(bounds)){
					return outcodeBounce(bounds,ball,false);
				}else if(bounds.contains(ball)){
					return boundryBounce(bounds,ball,false);
				}else{
					return null;
				}
			case LEVEL:
				if(ball.intersects(bounds)){
					return boundryBounce(bounds,ball,false);
				}else if(!bounds.contains(ball)){
					return outcodeBounce(bounds,ball,false);
				}else{
					return null;
				}
			case POWER_UP:
				if(ball.intersects(bounds)){
					return boundryBounce(bounds,ball,true);
				}else if(bounds.contains(ball)){
					return outcodeBounce(bounds,ball,true);
				}else{
					return null;
				}
			case ENEMY:
				if(ball.intersects(bounds)){
					return Step9ActionType.GAME_OVER;
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

	private Step9ActionType outcodeBounce(Rectangle2D bounds, Rectangle2D ball, boolean powerUp){
		int outcode = bounds.outcode(ball.getCenterX(),ball.getCenterY());
		switch(outcode){
			case Rectangle2D.OUT_LEFT: //1
				return powerUp ? Step9ActionType.POWER_UP_H : Step9ActionType.BOUNCE_H;
			case Rectangle2D.OUT_TOP: //2
				return powerUp ?  Step9ActionType.POWER_UP_V : Step9ActionType.BOUNCE_V;
			case Rectangle2D.OUT_LEFT + Rectangle2D.OUT_TOP: //3
				return powerUp ? Step9ActionType.POWER_UP_D : Step9ActionType.BOUNCE_D;
			case Rectangle2D.OUT_RIGHT: //4
				return powerUp ? Step9ActionType.POWER_UP_H : Step9ActionType.BOUNCE_H;
			case Rectangle2D.OUT_RIGHT + Rectangle2D.OUT_TOP: //6
				return powerUp ? Step9ActionType.POWER_UP_D : Step9ActionType.BOUNCE_D;
			case Rectangle2D.OUT_BOTTOM: //8
				return powerUp ? Step9ActionType.POWER_UP_V : Step9ActionType.BOUNCE_V;
			default: //9+
				return powerUp ? Step9ActionType.POWER_UP_D : Step9ActionType.BOUNCE_D;
		}
	}
	
	private Step9ActionType boundryBounce(Rectangle2D bounds, Rectangle2D ball, boolean powerUp){
		if(ball.getMaxX() > bounds.getMaxX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return powerUp ? Step9ActionType.POWER_UP_D : Step9ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return powerUp ? Step9ActionType.POWER_UP_D : Step9ActionType.BOUNCE_D;
			}else{
				return powerUp ? Step9ActionType.POWER_UP_H : Step9ActionType.BOUNCE_H;
			}
		}else if(ball.getMinX() < bounds.getMinX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return powerUp ? Step9ActionType.POWER_UP_D : Step9ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return powerUp ? Step9ActionType.POWER_UP_D : Step9ActionType.BOUNCE_D;
			}else{
				return powerUp ? Step9ActionType.POWER_UP_H : Step9ActionType.BOUNCE_H;
			}
		}else{
			if(ball.getMaxY() > bounds.getMaxY()){
				return powerUp ? Step9ActionType.POWER_UP_V : Step9ActionType.BOUNCE_V;
			}else if(ball.getMinY() < bounds.getMinY()){
				return powerUp ? Step9ActionType.POWER_UP_V : Step9ActionType.BOUNCE_V;
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
	public boolean canMergeWith(MergingCollidable<Step9Attributes> target) {
		return target.getAttributes().getType() == this.getAttributes().getType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.mdmk3.core.loader.MergingCollidable#merge(org.mdmk3.core.Node, org.mdmk3.core.loader.MergingCollidable<A>[])
	 * 
	 * This assumes if A.canMergeWith(B) == true and A.canMergeWith(C) == true then B.canMergeWith(C) can never be false.
	 */
	@Override
	public void merge(Node<Step9Attributes> parent, MergingCollidable<Step9Attributes>... children) {
		for(MergingCollidable<Step9Attributes> child : children){
			if(!canMergeWith(child)){
				return;
			}
		}
		
		/*Step9Attributes atts = parent.getAttributes();
		Area a = new Area(atts.getCollisionSurface());*/
		Area a = new Area();
		
		for(MergingCollidable<Step9Attributes> child : children){
			a.add(new Area(child.getAttributes().getCollisionSurface()));
			child.removeDecorator(Step9BouncingDecorator.class);
		}
		
		Step9Attributes atts = parent.getAttributes();
		atts.setShape(a);
		atts.setType(Step9ObjectType.BOX);
		new Step9BouncingDecorator(parent);
	}

}
