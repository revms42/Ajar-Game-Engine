package org.interaction;

import org.IGameManifest;

public abstract class AbstractAction<K> implements IAction<K> {

	private final IGameManifest manifest;
	
	public AbstractAction(IGameManifest manifest){
		this.manifest = manifest;
	}
	
	@Override
	public abstract void performAction(IEntity<K> subject, IEntity<K>... objects);

	@Override
	public IGameManifest getManifest() {
		return manifest;
	}

}
