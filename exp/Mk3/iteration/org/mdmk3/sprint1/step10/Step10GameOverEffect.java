package org.mdmk3.sprint1.step10;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step10GameOverEffect extends AbstractEffect<Step10Attributes> {

	public Step10GameOverEffect(State<Step10Attributes> result) {
		super(Step10ActionType.GAME_OVER, result);
	}

	//TODO: Crude but effective. Make less crude.
	@Override
	protected void doAction(Entity<Step10Attributes> state) {
		System.out.println("GAME OVER!");
		System.exit(0);
	}

}
