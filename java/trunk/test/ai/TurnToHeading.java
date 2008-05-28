package test.ai;

import javax.swing.JTree;

import org.IGameManifest;
import org.interaction.IEntity;
import org.interaction.util.Geometry;
import org.model.CircularStat;
import org.model.number.Number;

public class TurnToHeading extends VerboseAction<String> implements FieldConstants {

	public TurnToHeading(IGameManifest manifest, JTree log) {
		super(manifest, log);
	}

	@Override
	public void performAction(IEntity<String> subject, IEntity<String>... objects) {
		Number h = Geometry.heading(subject, objects[0], X, Y, true);
		
		CircularStat c = (CircularStat)subject.getStat(HEADING);
		if(c.shortestArc(h) <= 0){
			h.minusEq(subject.value(HEADING)).minusEq(subject.value(INERTIA));
			subject.plusEq(TURNING, h);
		}else{
			h.timesEq(-1);
			h.plusEq(subject.value(HEADING)).plusEq(subject.value(INERTIA));
			subject.minusEq(TURNING, h);
		}
		
		logAction(subject.toString() + " turning to " + objects[0].toString() + ".");
		logAction(subject.toString() + " heading = " + subject.value(HEADING).doubleValue());
		logAction(subject.toString() + " turning = " + subject.value(TURNING).doubleValue());
		logAction(subject.toString() + " inertia = " + subject.value(INERTIA).doubleValue());
	}
}
