package org.mdmk3.sprint1.step10;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step10ShootEffect extends AbstractEffect<Step10Attributes> {

	private final Step10TilePalette palette;
	
	public Step10ShootEffect(State<Step10Attributes> result) throws IOException {
		super(Step10ActionType.SHOOT, result);
		this.palette = new Step10TilePalette();
	}

	@Override
	protected void doAction(Entity<Step10Attributes> state) {
		Step10Attributes atts = new Step10Attributes(new Rectangle2D.Double(20.0d,20.0d,20.0d,20.0d), Step10ObjectType.BULLET);
		atts.setCurrentFrame(10);
		atts.setPosition(state.getAttributes().getXPos(), state.getAttributes().getYPos());
		
		int xvel = state.getAttributes().getXVel();
		int yvel = state.getAttributes().getYVel();
		
		xvel = xvel * 2;
		yvel = yvel * 2;
		
		atts.setXVel(xvel);
		atts.setYVel(yvel);
		
		DefaultNode<Step10Attributes> bullet = new DefaultNode<Step10Attributes>(atts);
		new Step10Entity(new Step10BouncingDecorator(new Step10DisplayDecorator(bullet)), new Step10BulletState());
		state.addChild(bullet);
		
		bullet.getDecorator(Step10DisplayDecorator.class).setProvider(palette);
	}

}
