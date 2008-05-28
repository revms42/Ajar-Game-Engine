package test.ai;

import javax.swing.JTree;

import org.IGameManifest;
import org.interaction.IEntity;

public class StopThrust extends VerboseAction<String> implements FieldConstants {

	public StopThrust(IGameManifest manifest, JTree log) {
		super(manifest, log);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		subject.minusEq(THRUST, subject.value(VELOCITY).times(-1));
		
		logAction(subject.toString() + " stopping thrust.");
		logAction(subject.toString() + " velocity = " + subject.value(VELOCITY).intValue());
		logAction(subject.toString() + " thrust = " + subject.value(THRUST).intValue());
	}

}
