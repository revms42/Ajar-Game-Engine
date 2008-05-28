package test.ai;

import java.util.Vector;

import org.IGameManifest;
import org.interaction.AbstractCondition;
import org.interaction.ActionPalette;
import org.interaction.IEntity;

public class Update extends AbstractCondition<String, String> implements
		FieldConstants {
	
	public static String POSITION = "update position";
	public static String VELOCITY = "update velocity";
	public static String INERTIA = "update inertia";
	public static String HEADING = "update heading";

	public Update(ActionPalette<String, String> palette,
			Vector<String> actions, IGameManifest manifest) {
		super(palette, actions, manifest);
	}

	public Update(ActionPalette<String, String> palette, IGameManifest manifest) {
		super(palette, manifest);
		
		this.addAction(INERTIA);
		this.addAction(HEADING);
		this.addAction(VELOCITY);
		this.addAction(POSITION);
	}

	public Update(IGameManifest manifest) {
		super(manifest);
		
		this.setActionPalette(Update.makeActionPalette(manifest));
		
		this.addAction(INERTIA);
		this.addAction(HEADING);
		this.addAction(VELOCITY);
		this.addAction(POSITION);
	}
	
	public static ActionPalette<String,String> makeActionPalette(IGameManifest manifest){
		ActionPalette<String,String> palette = new ActionPalette<String,String>();
		
		palette.put(POSITION, new UpdatePosition(manifest));
		palette.put(VELOCITY, new UpdateVelocity(manifest));
		palette.put(INERTIA, new UpdateInertia(manifest));
		palette.put(HEADING, new UpdateHeading(manifest));
		
		return palette;
	}

	@Override
	public boolean isFullfilled(IEntity<String> subject, IEntity<String>... objects) {
		return true;
	}

}
