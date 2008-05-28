package test.ai;

import org.IGameManifest;
import org.interaction.AbstractAction;
import org.interaction.IEntity;
import org.model.number.Number;

public class UpdatePosition extends AbstractAction<String> implements
		FieldConstants {

	public UpdatePosition(IGameManifest manifest) {
		super(manifest);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		Number x = Number.parse(
				Math.cos(subject.value(HEADING).doubleValue()) * 
				subject.value(VELOCITY).doubleValue()
		);
		Number y = Number.parse(
				Math.sin(subject.value(HEADING).doubleValue()) * 
				subject.value(VELOCITY).doubleValue()
		);
		
		subject.plusEq(X, x

		);
		subject.plusEq(Y, y

		);
	}

}
