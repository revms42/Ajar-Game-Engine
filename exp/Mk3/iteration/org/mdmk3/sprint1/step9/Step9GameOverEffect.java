package org.mdmk3.sprint1.step9;

import org.mdmk3.core.logic.AbstractEffect;
import org.mdmk3.core.logic.Entity;
import org.mdmk3.core.logic.State;

public class Step9GameOverEffect extends AbstractEffect<Step9Attributes> {

	public Step9GameOverEffect(State<Step9Attributes> result) {
		super(Step9ActionType.GAME_OVER, result);
	}

	//TODO: Crude but effective. Make less crude.
	@Override
	protected void doAction(Entity<Step9Attributes> state) {
		System.out.println("GAME OVER!");
		System.exit(0);
	}

}
