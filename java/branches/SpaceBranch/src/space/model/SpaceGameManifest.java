package space.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.IGameManifest;
import org.control.IController;
import org.display.IDisplayable;
import org.display.IEnvironment;
import org.interaction.IEntity;

import space.model.ships.DefaultShip;
import space.model.ships.Fleet;

public class SpaceGameManifest<I> implements IGameManifest {
	private final HashMap<IController<?,?>,Vector<Fleet<I>>> fleets;
	private final HashMap<IController<?,?>,Vector<DefaultShip<I>>> prototypes;
	private final HashMap<Object,Vector<IController<?,?>>> controllers;
	
	public SpaceGameManifest(){
		fleets = new HashMap<IController<?,?>,Vector<Fleet<I>>>();
		controllers = new HashMap<Object,Vector<IController<?,?>>>();
		prototypes = new HashMap<IController<?,?>,Vector<DefaultShip<I>>>();
	}

	@SuppressWarnings("unchecked")
	public <C extends IController<?, ?>> Collection<C> getControllers(Object caller) {
		if(controllers.containsKey(caller)){
			return (Collection<C>)controllers.get(caller);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <D extends IDisplayable<?, ?>> Collection<D> getDisplayables(Object caller) {
		if(caller instanceof IController){
			if(prototypes.containsKey(caller)){
				return (Collection<D>)prototypes.get((IController<?,?>)caller);
			}else if(fleets.containsKey(caller)){
				return (Collection<D>)fleets.get((IController<?,?>)caller);
			}else{
				return null;
			}
		}else{
			Collection<IController<?,?>> controls = getControllers(caller);
			Vector<D> displayables = new Vector<D>();
			
			if(controls != null){
				for(IController<?,?> controller : controls){
					displayables.addAll((Collection<D>)getDisplayables(controller));
				}
				return displayables;
			}
			
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <E extends IEntity<?>> Collection<E> getEntities(Object caller) {
		if(caller instanceof IController){
			if(prototypes.containsKey(caller)){
				return (Collection<E>)prototypes.get((IController<?,?>)caller);
			}else if(fleets.containsKey(caller)){
				return (Collection<E>)fleets.get((IController<?,?>)caller);
			}else{
				return null;
			}
		}else{
			Collection<IController<?,?>> controls = getControllers(caller);
			Vector<E> displayables = new Vector<E>();
			
			if(controls != null){
				for(IController<?,?> controller : controls){
					displayables.addAll((Collection<E>)getDisplayables(controller));
				}
				return displayables;
			}
			
			return null;
		}
	}

	public <V extends IEnvironment<?, ?>> Collection<V> getEnvironments(
			Object caller) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
