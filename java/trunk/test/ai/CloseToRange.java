package test.ai;

import java.util.Vector;

import javax.swing.JTree;

import org.IGameManifest;
import org.interaction.IEntity;
import org.interaction.util.Geometry;
import org.interaction.ActionPalette;

public class CloseToRange extends VerboseCondition<String, String> implements FieldConstants {

	public final static String[] KEYS = {"TurnToHeading","ThrustToRange"};
	public final VerboseAction<String>[] ACTIONS;
	
	@SuppressWarnings("unchecked")
	public CloseToRange(IGameManifest manifest, JTree log) {
		super(manifest, log);
		
		ACTIONS = new VerboseAction[]{
			new TurnToHeading(manifest,log),
			new ThrustToRange(manifest,log)
		};
		
		ActionPalette<String,String> palette = 
			new ActionPalette<String,String>();
		palette.put(KEYS[0], ACTIONS[0]);
		palette.put(KEYS[1], ACTIONS[1]);
		
		this.setActionPalette(palette);
		Vector<String> set = new Vector<String>();
		set.add(KEYS[0]); set.add(KEYS[1]);
		this.setActions(set);
	}

	@Override
	public boolean isFullfilled(IEntity<String> subject, IEntity<String>... objects) {
		Number n = Geometry.distance(subject,objects[0],X,Y);
		
		return subject.value(RANGE).compareTo(n) != 0 ? true : false;
	}

}
