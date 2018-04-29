package iso.environment;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import org.AbstractLevel;
import org.display.IEnvironmentContext;
import org.display.ITileFactory;
import org.model.IStats;
import org.model.number.Number;

public class IsometricLevel<I,K> extends AbstractLevel<I,K> {

	public IsometricLevel(
			Vector<BufferedImage> maps,
			IStats<K> stats,
			IEnvironmentContext<I> context,
			ITileFactory<I> factory) 
	{
		super(maps, stats, context, factory); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public IsometricLevel<I,K> clone() {
		return new IsometricLevel<I,K>(
				(Vector<BufferedImage>)this.getMaps().clone(),
				this.getStats().clone(),
				this.getEnvironmentContext(),
				this.getTileFactory()
		);
	}

	@Override
	public boolean isOnScreen(Rectangle arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int compareTo(IStats<K> object, K key) {
		return getStats().compareTo(object, key);
	}

	@Override
	public Number into(K key, java.lang.Number o) {
		return getStats().into(key, o);
	}

	@Override
	public Number intoEq(K key, java.lang.Number o) {
		return getStats().intoEq(key, o);
	}

	@Override
	public Number minusEq(K key, java.lang.Number o) {
		return getStats().minusEq(key, o);
	}

	@Override
	public Number timesEq(K key, java.lang.Number o) {
		return getStats().timesEq(key, o);
	}

	@Override
	public Number plusEq(K key, java.lang.Number o) {
		return getStats().plusEq(key, o);
	}

	@Override
	public Number minus(K key, java.lang.Number o) {
		return getStats().minus(key, o);
	}

	@Override
	public Number times(K key, java.lang.Number o) {
		return getStats().times(key, o);
	}

	@Override
	public Number power(K key, java.lang.Number o) {
		return getStats().power(key, o);
	}

	@Override
	public Number powerEq(K key, java.lang.Number o) {
		return getStats().powerEq(key, o);
	}
	
	@Override
	public Number plus(K key, java.lang.Number o) {
		return getStats().plus(key, o);
	}

}
