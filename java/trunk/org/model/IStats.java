package org.model;

import org.model.number.Number;

public interface IStats<K> {

	public IStat getStat(K key);
	public void setStat(K key, IStat value);
	
	public Number value(K key);
	public void value(K key, java.lang.Number value);

	public Number max(K key);
	public void max(K key, java.lang.Number max);
	
	public Number min(K key);
	public void min(K key, java.lang.Number min);
	
	public Number nominal(K key);
	public void nominal(K key, java.lang.Number nominal);
	
	public int compareTo(IStats<K> object, K key);
	
	public Number minus(K key, java.lang.Number o);
	public Number minusEq(K key, java.lang.Number o);
	
	public Number plus(K key, java.lang.Number o);
	public Number plusEq(K key, java.lang.Number o);
	
	public Number times(K key, java.lang.Number o);
	public Number timesEq(K key, java.lang.Number o);
	
	public Number into(K key, java.lang.Number o);
	public Number intoEq(K key, java.lang.Number o);
	
	public Number power(K key, java.lang.Number o);
	public Number powerEq(K key, java.lang.Number o);
	
	public void reset();
	public IStats<K> clone();
}
