package org.mdmk3.sprint1.step10;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Node;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.loader.MergingCollidable;
import org.mdmk3.core.logic.Action;

public class Step10BouncingDecorator extends MergingCollidable<Step10Attributes> {

	protected AffineTransform trans;
	
	public Step10BouncingDecorator(Node<Step10Attributes> node) {
		super(node);
		trans = AffineTransform.getTranslateInstance(0, 0);
	}
	
	@Override
	public Action collideWith(Collidable<Step10Attributes> s) {
		if(hasCapability(Step10Entity.class)){
			
			Rectangle2D bounds = s.getAttributes().getCollisionSurface().getBounds2D();
			Rectangle2D ball = trans.createTransformedShape(getAttributes().getCollisionSurface()).getBounds2D();
			
			switch(getAttributes().getType()){
			case BALL:
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
						return Step10ActionType.GAME_OVER;
					}else{
						return null;
					}
				default:
					return null;
				}
			case ENEMY:
				switch(s.getAttributes().getType()){
				case BULLET:
					if(ball.intersects(bounds)){
						return Step10ActionType.DIE;
					}else{
						return null;
					}
				default:
					return null;
				}
			case BULLET:
				switch(s.getAttributes().getType()){
				case POWER_UP:
				case BOX:
					if(ball.intersects(bounds)){
						return Step10ActionType.DIE;
					}else{
						return null;
					}
				case LEVEL:
					if(!bounds.contains(ball)){
						return Step10ActionType.DIE;
					}else{
						return null;
					}
				default:
					return null;
				}
			default:
				return null;
			}
		}else{
			return null;
		}
	}

	private Step10ActionType outcodeBounce(Rectangle2D bounds, Rectangle2D ball, boolean powerUp){
		int outcode = bounds.outcode(ball.getCenterX(),ball.getCenterY());
		switch(outcode){
			case Rectangle2D.OUT_LEFT: //1
				return powerUp ? Step10ActionType.POWER_UP_H : Step10ActionType.BOUNCE_H;
			case Rectangle2D.OUT_TOP: //2
				return powerUp ?  Step10ActionType.POWER_UP_V : Step10ActionType.BOUNCE_V;
			case Rectangle2D.OUT_LEFT + Rectangle2D.OUT_TOP: //3
				return powerUp ? Step10ActionType.POWER_UP_D : Step10ActionType.BOUNCE_D;
			case Rectangle2D.OUT_RIGHT: //4
				return powerUp ? Step10ActionType.POWER_UP_H : Step10ActionType.BOUNCE_H;
			case Rectangle2D.OUT_RIGHT + Rectangle2D.OUT_TOP: //6
				return powerUp ? Step10ActionType.POWER_UP_D : Step10ActionType.BOUNCE_D;
			case Rectangle2D.OUT_BOTTOM: //8
				return powerUp ? Step10ActionType.POWER_UP_V : Step10ActionType.BOUNCE_V;
			default: //9+
				return powerUp ? Step10ActionType.POWER_UP_D : Step10ActionType.BOUNCE_D;
		}
	}
	
	private Step10ActionType boundryBounce(Rectangle2D bounds, Rectangle2D ball, boolean powerUp){
		if(ball.getMaxX() > bounds.getMaxX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return powerUp ? Step10ActionType.POWER_UP_D : Step10ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return powerUp ? Step10ActionType.POWER_UP_D : Step10ActionType.BOUNCE_D;
			}else{
				return powerUp ? Step10ActionType.POWER_UP_H : Step10ActionType.BOUNCE_H;
			}
		}else if(ball.getMinX() < bounds.getMinX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return powerUp ? Step10ActionType.POWER_UP_D : Step10ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return powerUp ? Step10ActionType.POWER_UP_D : Step10ActionType.BOUNCE_D;
			}else{
				return powerUp ? Step10ActionType.POWER_UP_H : Step10ActionType.BOUNCE_H;
			}
		}else{
			if(ball.getMaxY() > bounds.getMaxY()){
				return powerUp ? Step10ActionType.POWER_UP_V : Step10ActionType.BOUNCE_V;
			}else if(ball.getMinY() < bounds.getMinY()){
				return powerUp ? Step10ActionType.POWER_UP_V : Step10ActionType.BOUNCE_V;
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
	public boolean canMergeWith(MergingCollidable<Step10Attributes> target) {
		return target.getAttributes().getType() == this.getAttributes().getType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.mdmk3.core.loader.MergingCollidable#merge(org.mdmk3.core.Node, org.mdmk3.core.loader.MergingCollidable<A>[])
	 * 
	 * This assumes if A.canMergeWith(B) == true and A.canMergeWith(C) == true then B.canMergeWith(C) can never be false.
	 */
	@Override
	public void merge(Node<Step10Attributes> parent, MergingCollidable<Step10Attributes>... children) {
		for(MergingCollidable<Step10Attributes> child : children){
			if(!canMergeWith(child)){
				return;
			}
		}
		
		/*Step10Attributes atts = parent.getAttributes();
		Area a = new Area(atts.getCollisionSurface());*/
		Area a = new Area();
		
		for(MergingCollidable<Step10Attributes> child : children){
			a.add(new Area(child.getAttributes().getCollisionSurface()));
			child.removeDecorator(Step10BouncingDecorator.class);
		}
		
		Step10Attributes atts = parent.getAttributes();
		atts.setShape(a);
		atts.setType(Step10ObjectType.BOX);
		new Step10BouncingDecorator(parent);
	}

}
