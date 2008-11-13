package org.model.number;

public class Long extends Number {
	private static final long serialVersionUID = -2428162211937398354L;
	
	private long value;
	
	public Long(Number number){
		value = number.longValue();
	}
	
	public Long(long number){
		value = number;
	}

	@Override
	public void set(java.lang.Number number) {
		value = number.longValue();
	}

	@Override
	public double doubleValue() {
		return (double)value;
	}

	@Override
	public float floatValue() {
		return (float)value;
	}

	@Override
	public int intValue() {
		return (int)value;
	}

	@Override
	public long longValue() {
		return value;
	}
	
	@Override
	public short shortValue() {
		return (short)value;
	}

	@Override
	public byte byteValue() {
		return (byte)value;
	}

	@Override
	public int compareTo(java.lang.Number o) {
		long thisVal = this.value;
		long anotherVal = o.longValue();
		return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
	}
	
	@Override
	public Long clone() {
		return new Long(value);
	}

	@Override
	public Long into(java.lang.Number o) {
		return new Long((long)(this.value / o.longValue()));
	}

	@Override
	public Long minus(java.lang.Number o) {
		return new Long((long)(this.value - o.longValue()));
	}

	@Override
	public Long times(java.lang.Number o) {
		return new Long((long)(this.value * o.longValue()));
	}

	@Override
	public Long plus(java.lang.Number o) {
		return new Long((long)(this.value + o.longValue()));
	}
	
	@Override
	public Long intoEq(java.lang.Number o) {
		this.value = (long)(this.value / o.longValue());
		return this;
	}

	@Override
	public Long minusEq(java.lang.Number o) {
		this.value = (long)(this.value - o.longValue());
		return this;
	}

	@Override
	public Long timesEq(java.lang.Number o) {
		this.value = (long)(this.value * o.longValue());
		return this;
	}

	@Override
	public Long plusEq(java.lang.Number o) {
		this.value = (long)(this.value + o.longValue());
		return this;
	}
	
	@Override
	public Long power(java.lang.Number o) {
		return new Long((long)(Math.pow((double)this.value, o.doubleValue())));
	}

	@Override
	public Long powerEq(java.lang.Number o) {
		this.value = (long)(Math.pow((double)this.value, o.doubleValue()));
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof java.lang.Number){
			return value == ((java.lang.Number)o).longValue();
		}else{
			return super.equals(o);
		}
	}
}
