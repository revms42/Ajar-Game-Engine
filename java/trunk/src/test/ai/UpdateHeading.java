package test.ai;

import org.IGameManifest;
import org.interaction.AbstractAction;
import org.interaction.IEntity;

public class UpdateHeading extends AbstractAction<String> implements
		FieldConstants {

	public UpdateHeading(IGameManifest manifest) {
		super(manifest);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		subject.plusEq(HEADING, subject.value(INERTIA));
	}

}
