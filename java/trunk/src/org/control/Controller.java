package org.control;

import java.awt.AWTEvent;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Vector;

import org.interaction.ConditionPalette;
import org.interaction.IEntity;

public abstract class Controller<C,K> implements IController<C,K> {
	
	private ConditionPalette<C,?,K> palette;
	
	protected Deque<AWTEvent> deque;
	private Vector<IControllable<C,K>> targets;
	private AWTEvent currentevent;
	
	private IControlMapping<C,K> mapping;
	private IEntity<K> entity;
	private C condition;
	
	public Controller(ConditionPalette<C,?,K> palette){
		this.palette = palette;
		
		this.deque = new ArrayDeque<AWTEvent>();
		this.targets = new Vector<IControllable<C,K>>();
	}

	@Override
	public void evaluateAllEvents() {
		for(int i = 0; i < deque.size(); i++){
			evaluateNextEvent();
		}
	}

	@Override
	public synchronized void evaluateNextEvent() {
		startEvent();
		
		for(IControllable<C,K> target : targets){
			mapping = target.getMapping();
			entity = target.getAsEntity();
			condition = mapping.getMapingFor(entity, currentevent);
			
			if(condition != null){
				palette.performAction(condition, entity);				
			}
		}
		
		endEvent();
	}
	
	protected abstract AWTEvent getNextEvent();
	
	protected final void endEvent(){
		deque.remove(currentevent);
	}
	
	protected final void startEvent(){
		currentevent = getNextEvent();
	}

	@Override
	public void flushEvents() {
		deque.removeAll(deque);
	}

	@Override
	public Vector<AWTEvent> getEvents() {
		return new Vector<AWTEvent>(deque);
	}

	@Override
	public Collection<IControllable<C,K>> getTargets() {
		return targets;
	}

	@Override
	public void mapTarget(IControllable<C,K> target) {
		this.targets.add(target);
	}

	@Override
	public boolean unMapTarget(IControllable<C,K> target) {
		return this.targets.remove(target);
	}

	@Override
	public ConditionPalette<C, ?, K> getPalette() {
		return palette;
	}

	@Override
	public void setPalette(ConditionPalette<C, ?, K> palette) {
		this.palette = palette;
	}

}
