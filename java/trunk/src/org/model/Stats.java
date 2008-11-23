package org.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.model.number.Number;

public class Stats<K> extends LinkedHashMap<K,IStat> implements IStats<K> {
	private static final long serialVersionUID = -6333863845752869421L;
	
	public Stats(K[] keys, IStat[] stats){
		super();
		
		for(int i = 0; i < keys.length; i++){
			if(stats.length > i){
				this.put(keys[i], stats[i]);
			}else{
				break;
			}
		}
	}
	
	public Stats(Collection<K> keys, Collection<IStat> stats){
		super();
		
		Iterator<IStat> 	j = stats.iterator();
		Iterator<K> 	i = keys.iterator();
		for( ;i.hasNext() && j.hasNext(); ){
			this.put(i.next(), j.next());
		}
	}
	
	public Stats(){
		super();
	}
	
	public IStat getStat(K key){
		return this.get(key);
	}
	
	public void setStat(K key, IStat stat){
		this.put(key, stat);
	}
	
	public Number value(K key){
		return containsKey(key) ? get(key).value() : null;
	}

	
	public void value(K key, java.lang.Number number){
		if(containsKey(key)) this.get(key).value(number);
	}
	
	public Number max(K key){
		return containsKey(key) ? get(key).max() : null;
	}
	
	public void max(K key, java.lang.Number number){
		if(containsKey(key)) this.get(key).max(number);
	}
	
	public Number min(K key){
		return containsKey(key) ? get(key).min() : null;
	}
	
	public void min(K key, java.lang.Number number){
		if(containsKey(key)) this.get(key).min(number);
	}
	
	public Number nominal(K key){
		return containsKey(key) ? get(key).nominal() : null;
	}
	
	public void nominal(K key, java.lang.Number number){
		if(containsKey(key)) this.get(key).nominal(number);
	}
	
	public void reset(){
		for(IStat i : this.values()){
			i.reset();
		}
	}
	
	public Stats<K> clone(){
		Collection<K> keys = this.keySet();
		Vector<IStat> clone = new Vector<IStat>();
		
		for(IStat i : this.values()){
			clone.add(i.clone());
		}
		
		return new Stats<K>(keys,clone);
	}

	@Override
	public int compareTo(IStats<K> object, K key) {
		return this.get(key).compareTo(object.value(key));
	}

	@Override
	public Number into(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).into(o) : null;
	}

	@Override
	public Number minus(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).minus(o) : null;
	}

	@Override
	public Number times(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).times(o) : null;
	}

	@Override
	public Number plus(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).plus(o) : null;
	}

	@Override
	public Number intoEq(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).intoEq(o) : null;
	}

	@Override
	public Number minusEq(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).minusEq(o) : null;
	}

	@Override
	public Number timesEq(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).timesEq(o) : null;
	}

	@Override
	public Number plusEq(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).plusEq(o) : null;
	}

	@Override
	public Number power(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).power(o) : null;
	}

	@Override
	public Number powerEq(K key, java.lang.Number o) {
		return containsKey(key) ? this.get(key).powerEq(o) : null;
	}
	
	
}
