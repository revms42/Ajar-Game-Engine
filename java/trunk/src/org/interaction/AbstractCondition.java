package org.interaction;

import java.util.Vector;

import org.IGameManifest;

public abstract class AbstractCondition<A,K> implements ICondition<A,K> {

	private final IGameManifest manifest;
	private ActionPalette<A,K> palette;
	private Vector<A> actions;
	
	public AbstractCondition(ActionPalette<A,K> palette, Vector<A> actions, IGameManifest manifest){
		this.actions = actions;
		this.palette = palette;
		this.manifest = manifest;
	}
	
	public AbstractCondition(ActionPalette<A,K> palette, IGameManifest manifest){
		this.actions = new Vector<A>();
		this.palette = palette;
		this.manifest = manifest;
	}
	
	public AbstractCondition(IGameManifest manifest){
		this.actions = new Vector<A>();
		this.palette = null;
		this.manifest = manifest;
	}
	
	@Override
	public synchronized boolean addAction(A action) {
		if(palette != null){
			if(palette.containsKey(action) && !actions.contains(action)){
				actions.add(action);
				return true;
			}
		}
		return false;
	}

	@Override
	public A getAction(int index) {
		if(actions.size() > index){
			return actions.elementAt(index);
		}else{
			return null;
		}
	}

	@Override
	public ActionPalette<A, K> getActionPalette() {
		return palette;
	}

	@Override
	public Vector<A> getActions() {
		return actions;
	}

	@Override
	public abstract boolean isFullfilled(IEntity<K> subject, IEntity<K>... objects);

	@Override
	public A removeAction(A action) {
		if(action != null && actions.contains(action)){
			int i = actions.indexOf(action);
			return actions.remove(i);
		}else{
			return null;
		}
	}

	@Override
	public void removeAllActions() {
		actions.removeAllElements();
	}

	@Override
	public synchronized void setAction(int index, A action) {
		if(actions.size() > index && action != null){
			actions.set(index, action);
		}
	}

	@Override
	public void setActionPalette(ActionPalette<A, K> palette) {
		if(palette != null){
			this.palette = palette;
		}
	}

	@Override
	public void setActions(Vector<A> actions) {
		if(actions != null){
			this.actions = actions;
		}
	}

	@Override
	public synchronized void performAction(IEntity<K> subject, IEntity<K>... objects) {
		if(isFullfilled(subject,objects)){
			for(A action : actions){
				palette.performAction(action, subject, objects);
			}
		}
	}

	@Override
	public IGameManifest getManifest(){
		return manifest;
	}
}
