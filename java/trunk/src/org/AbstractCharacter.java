package org;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.display.IDisplayable;
import org.interaction.IBounded;
import org.interaction.IEntity;
import org.model.IStat;
import org.model.IStats;
import org.model.number.Number;

public abstract class AbstractCharacter<I,K> 
		implements IDisplayable<I,K>, IEntity<K>, IBounded 
{

	private IStats<K> stats;
	private IDisplayContext<I,K> context;
	private IDisplayFactory<I,K> factory;
	
	public AbstractCharacter(
			IStats<K> stats, 
			IDisplayContext<I,K> context, 
			IDisplayFactory<I,K> factory) 
	{
		this.stats = stats;
		this.context = context;
		this.factory = factory;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		factory.display(this, g2);
	}

	@Override
	public IDisplayContext<I,K> getDisplayContext() {
		return context;
	}

	@Override
	public abstract int getDisplayDepth();

	@Override
	public IDisplayFactory<I,K> getDisplayFactory() {
		return factory;
	}

	@Override
	public abstract boolean isOnScreen(Rectangle screen);

	@Override
	public void setDisplayContext(IDisplayContext<I,K> context) {
		this.context = context;
	}

	@Override
	public void setDisplayFactory(IDisplayFactory<I,K> factory) {
		this.factory = factory;
	}

	@Override
	public IStats<K> getStats() {
		return stats;
	}

	@Override
	public void setStats(IStats<K> stats) {
		this.stats  = stats;
	}

	@Override
	public Number max(K key) {
		return stats.max(key);
	}

	@Override
	public Number min(K key) {
		return stats.min(key);
	}

	@Override
	public Number nominal(K key) {
		return stats.nominal(key);
	}

	@Override
	public IStat getStat(K key) {
		return stats.getStat(key);
	}

	@Override
	public Number value(K key) {
		return stats.value(key);
	}

	@Override
	public void reset() {
		stats.reset();
	}

	@Override
	public void max(K key, java.lang.Number max) {
		stats.max(key, max);
	}

	@Override
	public void min(K key, java.lang.Number min) {
		stats.min(key, min);
	}

	@Override
	public void nominal(K key, java.lang.Number nominal) {
		stats.nominal(key, nominal);
	}

	@Override
	public void setStat(K key, IStat value) {
		stats.setStat(key, value);
	}

	@Override
	public void value(K key, java.lang.Number value) {
		stats.value(key, value);
	}
	
	@Override
	public abstract AbstractCharacter<I,K> clone();

	@Override
	public int compareTo(IStats<K> object, K key) {
		return stats.compareTo(object, key);
	}

	@Override
	public Number into(K key, java.lang.Number o) {
		return stats.into(key, o);
	}

	@Override
	public Number intoEq(K key, java.lang.Number o) {
		return stats.intoEq(key, o);
	}

	@Override
	public Number minusEq(K key, java.lang.Number o) {
		return stats.minusEq(key, o);
	}

	@Override
	public Number timesEq(K key, java.lang.Number o) {
		return stats.timesEq(key, o);
	}

	@Override
	public Number plusEq(K key, java.lang.Number o) {
		return stats.plusEq(key, o);
	}

	@Override
	public Number minus(K key, java.lang.Number o) {
		return stats.minus(key, o);
	}

	@Override
	public Number times(K key, java.lang.Number o) {
		return stats.times(key, o);
	}

	@Override
	public Number power(K key, java.lang.Number o) {
		return stats.power(key, o);
	}

	@Override
	public Number powerEq(K key, java.lang.Number o) {
		return stats.powerEq(key, o);
	}
	
	@Override
	public Number plus(K key, java.lang.Number o) {
		return stats.plus(key, o);
	}
}
