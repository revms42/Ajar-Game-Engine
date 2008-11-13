package org.model;

import org.model.number.Number;

public abstract class AbstractStat extends Number implements IStat {
	private static final long serialVersionUID = -6435230096090064587L;
	
	protected Number value;
	protected Number max;
	protected Number min;
	protected Number nominal;
	
	public AbstractStat(Number value, Number max, Number min, Number nominal){
		this.value = value;
		this.max = max;
		this.min = min;		
		this.nominal = nominal;
	}
	
	public AbstractStat(double value, double max, double min, double nominal){
		this.value = Number.parse(value);
		this.max = Number.parse(max);
		this.min = Number.parse(min);
		this.nominal = Number.parse(nominal);
	}
	
	public AbstractStat(float value, float max, float min, float nominal){
		this.value = Number.parse(value);
		this.max = Number.parse(max);
		this.min = Number.parse(min);
		this.nominal = Number.parse(nominal);
	}
	
	public AbstractStat(long value, long max, long min, long nominal){
		this.value = Number.parse(value);
		this.max = Number.parse(max);
		this.min = Number.parse(min);
		this.nominal = Number.parse(nominal);
	}
	
	public AbstractStat(int value, int max, int min, int nominal){
		this.value = Number.parse(value);
		this.max = Number.parse(max);
		this.min = Number.parse(min);
		this.nominal = Number.parse(nominal);
	}
	
	public AbstractStat(short value, short max, short min, short nominal){
		this.value = Number.parse(value);
		this.max = Number.parse(max);
		this.min = Number.parse(min);
		this.nominal = Number.parse(nominal);
	}
	
	public AbstractStat(byte value, byte max, byte min, byte nominal){
		this.value = Number.parse(value);
		this.max = Number.parse(max);
		this.min = Number.parse(min);
		this.nominal = Number.parse(nominal);
	}
	
	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public float floatValue() {
		return value.floatValue();
	}

	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public long longValue() {
		return value.longValue();
	}

	@Override
	public Number max() {
		return max;
	}

	@Override
	public Number min() {
		return min;
	}

	@Override
	public Number nominal() {
		return nominal;
	}

	@Override
	public Number value() {
		return value;
	}
	
	@Override
	public void max(java.lang.Number max) {
		if(max != null){
			if(min.compareTo(max) <= 0){
				this.max.set(max);
				
				if(value.compareTo(max) >= 0){
					this.value.set(max);
				}
			}
		}
	}

	@Override
	public void min(java.lang.Number min) {
		if(min != null){
			if(max.compareTo(min) >= 0){
				this.min.set(min);
				
				if(value.compareTo(min) <= 0){
					this.value.set(min);
				}
			}
		}
	}
	
	@Override
	public void nominal(java.lang.Number nominal) {
		if(nominal != null){
			if(max.compareTo(nominal) >= 0 && min.compareTo(nominal) <= 0){
				this.nominal.set(nominal);
			}else if(min.compareTo(nominal) >= 0){
				this.nominal.set(min);
			}else{
				this.nominal.set(max);
			}
		}
	}

	@Override
	public void reset() {
		value.set(nominal);
	}

	@Override
	public int compareTo(java.lang.Number o) {
		return value.compareTo(o);
	}

	public abstract AbstractStat clone();
	
	@Override
	public Number into(java.lang.Number o) {
		return value.into(o);
	}

	@Override
	public Number minus(java.lang.Number o) {
		return value.minus(o);
	}

	@Override
	public Number times(java.lang.Number o) {
		return value.times(o);
	}

	@Override
	public Number plus(java.lang.Number o) {
		return value.plus(o);
	}

	@Override /*TODO: Test Efficiency of these methods.... */
	public AbstractStat intoEq(java.lang.Number o) {
		value(value().into(o));
		return this;
	}

	@Override /*TODO: Test Efficiency of these methods.... */
	public AbstractStat minusEq(java.lang.Number o) {
		value(value().minus(o));
		return this;
	}

	@Override /*TODO: Test Efficiency of these methods.... */
	public AbstractStat timesEq(java.lang.Number o) {
		value(value().times(o));
		return this;
	}

	@Override /*TODO: Test Efficiency of these methods.... */
	public AbstractStat plusEq(java.lang.Number o) {
		value(value().plus(o));
		return this;
	}
	
	@Override
	public Number power(java.lang.Number o) {
		return value.power(o);
	}

	@Override /*TODO: Test Efficiency of these methods.... */
	public AbstractStat powerEq(java.lang.Number o) {
		value(value().powerEq(o));
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof IStat){
			return ((IStat)o).value().equals(value);
		}else if(o instanceof java.lang.Number){
			return value.equals(o);
		}else{
			return super.equals(o);
		}
	}
}
