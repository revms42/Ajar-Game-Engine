package iso;

import iso.environment.IsoEnvironmentContext;
import iso.environment.IsometricLevel;
import iso.environment.IsometricTileFactory;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.IGameManifest;
import org.control.IController;
import org.display.IDisplayable;
import org.display.IEnvironment;
import org.interaction.IEntity;
import org.model.Stats;

public class IsometricManifest<I,K> implements IGameManifest {

	private final HashMap<String,IsometricLevel<I,K>> environments;
	
	public IsometricManifest(){
		Vector<BufferedImage> levelMaps = new Vector<BufferedImage>();
		Stats<K> levelStats = new Stats<K>();
		IsoEnvironmentContext<I> levelContext = new IsoEnvironmentContext<I>();
		IsometricTileFactory<I> levelFactory = new IsometricTileFactory<I>();
		IsometricLevel<I,K> level = new IsometricLevel<I,K>(
			levelMaps,
			levelStats,
			levelContext,
			levelFactory
		);
		
		environments = new HashMap<String,IsometricLevel<I,K>>();
		environments.put("Level 1", level);
	}
	
	@Override
	public Collection<IController<?, ?>> getControllers(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IDisplayable<?, ?>> getDisplayables(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IEntity<?>> getEntities(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IEnvironment<?, ?>> getEnvironments(Object arg0) {
		return (Collection<IEnvironment<?, ?>>) environments;
	}

	@Override
	public int getMaxDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
