package org;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import org.display.IEnvironment;
import org.display.IEnvironmentContext;
import org.display.ITileFactory;
import org.interaction.IEntity;
import org.model.IStat;
import org.model.IStats;
import org.model.number.Number;

public abstract class AbstractLevel<I,K> implements IEnvironment<I,K>, IEntity<K> {

	private Vector<BufferedImage> maps;
	private IStats<K> stats;
	private IEnvironmentContext<I> context;
	private ITileFactory<I> factory;
	
	public AbstractLevel(
			Vector<BufferedImage> maps,
			IStats<K> stats,
			IEnvironmentContext<I> context,
			ITileFactory<I> factory) 
	{
		this.maps = maps;
		this.stats = stats;
		this.context = context;
		this.factory = factory;
	}
	
	@Override
	public void draw(Graphics2D g2, Rectangle window) {
		factory.displayAll(this, window, g2);
	}

	@Override
	public void draw(Graphics2D g2, Rectangle window, int depth) {
		factory.displayDepth(this, depth, window, g2);
	}

	@Override
	public IEnvironmentContext<I> getEnvironmentContext() {
		return context;
	}

	@Override
	public Vector<BufferedImage> getMaps() {
		return maps;
	}
	
	public BufferedImage getMapAtDepth(int depth) {
		return maps.elementAt(depth);
	}
	
	public void setMapAtDepth(BufferedImage map, int depth) {
		maps.set(depth, map);
	}

	@Override
	public ITileFactory<I> getTileFactory() {
		return factory;
	}

	@Override
	public abstract boolean isOnScreen(Rectangle screen, int depth);

	@Override
	public void setEnvironmentContext(IEnvironmentContext<I> context) {
		this.context = context;
	}

	@Override
	public void setTileFactory(ITileFactory<I> factory) {
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
	public abstract AbstractLevel<I,K> clone();

}
