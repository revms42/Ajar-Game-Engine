package test.ai;

import org.IGameManifest;
import org.interaction.AbstractAction;
import org.interaction.IEntity;

public class UpdateInertia extends AbstractAction<String> implements FieldConstants {

	public UpdateInertia(IGameManifest manifest) {
		super(manifest);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		subject.plusEq(INERTIA, subject.value(TURNING));
	}

}
