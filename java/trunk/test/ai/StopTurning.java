package test.ai;

import javax.swing.JTree;

import org.IGameManifest;
import org.interaction.IEntity;

public class StopTurning extends VerboseAction<String> implements
		FieldConstants {

	public StopTurning(IGameManifest manifest, JTree log) {
		super(manifest, log);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		subject.minusEq(TURNING, subject.value(INERTIA).times(-1));

		logAction(subject.toString() + " stopping rotation.");
		logAction(subject.toString() + " inertia = " + subject.value(INERTIA).intValue());
		logAction(subject.toString() + " turning = " + subject.value(TURNING).intValue());
	}

}
