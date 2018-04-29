package org.mdmk3.sprint1.step2;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.mdmk3.core.Node;
import org.mdmk3.core.collision.AbstractCollidable;
import org.mdmk3.core.collision.Collidable;
import org.mdmk3.core.logic.Action;

public class Step2BouncingDecorator extends AbstractCollidable<Step2Attributes> {

	protected AffineTransform trans;
	
	public Step2BouncingDecorator(Node<Step2Attributes> node) {
		super(node);
		trans = AffineTransform.getTranslateInstance(0, 0);
	}

	@Override
	public Action collideWith(Collidable<Step2Attributes> s) {
		if(hasCapability(Step2Entity.class)){
			trans.setToTranslation(getAttributes().getXVel(), getAttributes().getYVel());
			//Since there is only one other collidable in the level we know that the thing we're colliding against is the wall.
			Rectangle2D bounds = s.getAttributes().getCollisionSurface().getBounds2D();
			
			Rectangle2D ball = trans.createTransformedShape(getAttributes().getCollisionSurface()).getBounds2D();
			
			if(ball.intersects(bounds)){
				//TODO: collision check the boundries.
				if(ball.getMaxX() > bounds.getMaxX()){
					if(ball.getMaxY() > bounds.getMaxY()){
						return Step2ActionType.BOUNCE_D;
					}else if(ball.getMinY() < bounds.getMinY()){
						return Step2ActionType.BOUNCE_D;
					}else{
						return Step2ActionType.BOUNCE_H;
					}
				}else if(ball.getMinX() < bounds.getMinX()){
					if(ball.getMaxY() > bounds.getMaxY()){
						return Step2ActionType.BOUNCE_D;
					}else if(ball.getMinY() < bounds.getMinY()){
						return Step2ActionType.BOUNCE_D;
					}else{
						return Step2ActionType.BOUNCE_H;
					}
				}else{
					if(ball.getMaxY() > bounds.getMaxY()){
						return Step2ActionType.BOUNCE_V;
					}else if(ball.getMinY() < bounds.getMinY()){
						return Step2ActionType.BOUNCE_V;
					}else{
						return Step2ActionType.MOVE;
					}
				}
			}else{
				return Step2ActionType.MOVE;
			}
		}else{
			return null;
		}
	}

	@Override
	public boolean needsCollisionCheck() {
		return true;
	}

}
