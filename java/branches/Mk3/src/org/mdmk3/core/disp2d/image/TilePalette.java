package org.mdmk3.core.disp2d.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class TilePalette<I> implements TileProvider<I> {

	private final HashMap<I,TileProvider<I>> palette;
	
	public TilePalette(){
		palette = new HashMap<I,TileProvider<I>>();
	}
	
	@Override
	public void drawImage(I imageCode, Graphics2D g2, AffineTransform at, BufferedImageOp op) {
		getSourceFromCode(imageCode).drawImage(imageCode, g2, at, op);
	}

	@Override
	public BufferedImage getTile(I imageCode) {
		return getSourceFromCode(imageCode).getTile(imageCode);
	}
	
	public abstract I getSourceCodeFromImageCode(I imageCode);
	
	public TileProvider<I> getSourceFromCode(I imageCode) {
		return palette.get(getSourceCodeFromImageCode(imageCode));
	}

	public boolean canDraw(I key) {
		return palette.containsKey(getSourceCodeFromImageCode(key));
	}

	public boolean containsSource(TileSource<I> value) {
		return palette.containsValue(value);
	}

	public boolean isEmpty() {
		return palette.isEmpty();
	}

	public Set<I> keySet() {
		return palette.keySet();
	}

	public TileProvider<I> addSource(I key, TileSource<I> value) {
		return palette.put(key, value);
	}

	public void addSourceMap(Map<? extends I, ? extends TileSource<I>> m) {
		palette.putAll(m);
	}

	public TileProvider<I> removeSource(I key) {
		return palette.remove(getSourceCodeFromImageCode(key));
	}

	public int size() {
		return palette.size();
	}
	
	public Collection<TileProvider<I>> sources() {
		return palette.values();
	}
}
