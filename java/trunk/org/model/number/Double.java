package org.model.number;

public class Double extends Number {
	private static final long serialVersionUID = 4543417223845158867L;
	
	private double value;
	
	public Double(Number number){
		value = number.doubleValue();
	}
	
	public Double(double number){
		value = number;
	}

	@Override
	public void set(java.lang.Number number) {
		value = number.doubleValue();
	}

	@Override
	public double doubleValue() {
		return value;
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
		return (long)value;
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
		double thisVal = this.value;
		double anotherVal = o.doubleValue();
		return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
	}
	
	@Override
	public Double clone() {
		return new Double(value);
	}

	@Override
	public Double into(java.lang.Number o) {
		return new Double((double)(this.value / o.doubleValue()));
	}

	@Override
	public Double minus(java.lang.Number o) {
		return new Double((double)(this.value - o.doubleValue()));
	}

	@Override
	public Double times(java.lang.Number o) {
		return new Double((double)(this.value * o.doubleValue()));
	}

	@Override
	public Double plus(java.lang.Number o) {
		return new Double((double)(this.value + o.doubleValue()));
	}
	
	@Override
	public Double intoEq(java.lang.Number o) {
		this.value = (double)(this.value / o.doubleValue());
		return this;
	}

	@Override
	public Double minusEq(java.lang.Number o) {
		this.value = (double)(this.value - o.doubleValue());
		return this;
	}

	@Override
	public Double timesEq(java.lang.Number o) {
		this.value = (double)(this.value * o.doubleValue());
		return this;
	}

	@Override
	public Double plusEq(java.lang.Number o) {
		this.value = (double)(this.value + o.doubleValue());
		return this;
	}
	
	@Override
	public Double power(java.lang.Number o) {
		return new Double(Math.pow(this.value, o.doubleValue()));
	}

	@Override
	public Double powerEq(java.lang.Number o) {
		this.value = Math.pow(this.value, o.doubleValue());
		return this;
	}
}
