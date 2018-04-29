package org.mdmk3.sprint1.step5;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Node;
import org.mdmk3.core.collision.AbstractCollidable;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.logic.Action;

public class Step5BouncingDecorator extends AbstractCollidable<Step5Attributes> {

	protected AffineTransform trans;
	
	public Step5BouncingDecorator(Node<Step5Attributes> node) {
		super(node);
		trans = AffineTransform.getTranslateInstance(0, 0);
	}
	public int call = 0;
	@Override
	public Action collideWith(Collidable<Step5Attributes> s) {
		if(hasCapability(Step5Entity.class)){
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

	private Step5ActionType outcodeBounce(Rectangle2D bounds, Rectangle2D ball){
		int outcode = bounds.outcode(ball.getCenterX(),ball.getCenterY());
		switch(outcode){
			case Rectangle2D.OUT_LEFT: //1
				return Step5ActionType.BOUNCE_H;
			case Rectangle2D.OUT_TOP: //2
				return Step5ActionType.BOUNCE_V;
			case Rectangle2D.OUT_LEFT + Rectangle2D.OUT_TOP: //3
				return Step5ActionType.BOUNCE_D;
			case Rectangle2D.OUT_RIGHT: //4
				return Step5ActionType.BOUNCE_H;
			case Rectangle2D.OUT_RIGHT + Rectangle2D.OUT_TOP: //6
				return Step5ActionType.BOUNCE_D;
			case Rectangle2D.OUT_BOTTOM: //8
				return Step5ActionType.BOUNCE_V;
			default: //9+
				return Step5ActionType.BOUNCE_D;
		}
	}
	
	private Step5ActionType boundryBounce(Rectangle2D bounds, Rectangle2D ball){
		if(ball.getMaxX() > bounds.getMaxX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step5ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step5ActionType.BOUNCE_D;
			}else{
				return Step5ActionType.BOUNCE_H;
			}
		}else if(ball.getMinX() < bounds.getMinX()){
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step5ActionType.BOUNCE_D;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step5ActionType.BOUNCE_D;
			}else{
				return Step5ActionType.BOUNCE_H;
			}
		}else{
			if(ball.getMaxY() > bounds.getMaxY()){
				return Step5ActionType.BOUNCE_V;
			}else if(ball.getMinY() < bounds.getMinY()){
				return Step5ActionType.BOUNCE_V;
			}else{
				return null;
			}
		}
	}
	
	@Override
	public boolean needsCollisionCheck() {
		return true;
	}

}
