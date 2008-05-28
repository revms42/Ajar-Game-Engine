package test.ai;

import javax.swing.JTree;

import org.IGameManifest;
import org.interaction.IEntity;
import org.interaction.util.Geometry;
import org.model.number.Number;

public class ThrustToRange extends VerboseAction<String> implements FieldConstants {
	
	public ThrustToRange(IGameManifest manifest, JTree log) {
		super(manifest,log);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		Number n = Geometry.distance(subject,objects[0],X,Y);
		
		n.minusEq(subject.nominal(RANGE)).minusEq(subject.value(VELOCITY));
		subject.plusEq(THRUST, n);
		
		logAction(subject.toString() + " thrusting to " + objects[0].toString() + ".");
		logAction(subject.toString() + " range = " + subject.value(RANGE).intValue());
		logAction(subject.toString() + " thrust = " + subject.value(THRUST).intValue());
		logAction(subject.toString() + " velocity = " + subject.value(VELOCITY).intValue());
	}
}
