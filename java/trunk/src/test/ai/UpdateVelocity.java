package test.ai;

import org.IGameManifest;
import org.interaction.AbstractAction;
import org.interaction.IEntity;

public class UpdateVelocity extends AbstractAction<String> implements
		FieldConstants {

	public UpdateVelocity(IGameManifest manifest) {
		super(manifest);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		subject.plusEq(VELOCITY, subject.value(THRUST));
	}

}
