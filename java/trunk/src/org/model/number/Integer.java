package org.model.number;

public class Integer extends Number{
	private static final long serialVersionUID = 1960769763519346178L;
	
	private int value;
	
	public Integer(Number number){
		value = number.intValue();
	}
	
	public Integer(int number){
		value = number;
	}

	@Override
	public void set(java.lang.Number number) {
		value = number.intValue();
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
		return value;
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
		int thisVal = this.value;
		int anotherVal = o.intValue();
		return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
	}
	
	@Override
	public Integer clone() {
		return new Integer(value);
	}

	@Override
	public Integer into(java.lang.Number o) {
		return new Integer((int)(this.value / o.intValue()));
	}

	@Override
	public Integer minus(java.lang.Number o) {
		return new Integer((int)(this.value - o.intValue()));
	}

	@Override
	public Integer times(java.lang.Number o) {
		return new Integer((int)(this.value * o.intValue()));
	}

	@Override
	public Integer plus(java.lang.Number o) {
		return new Integer((int)(this.value + o.intValue()));
	}
	
	@Override
	public Integer intoEq(java.lang.Number o) {
		this.value = (int)(this.value / o.intValue());
		return this;
	}

	@Override
	public Integer minusEq(java.lang.Number o) {
		this.value = (int)(this.value - o.intValue());
		return this;
	}

	@Override
	public Integer timesEq(java.lang.Number o) {
		this.value = (int)(this.value * o.intValue());
		return this;
	}

	@Override
	public Integer plusEq(java.lang.Number o) {
		this.value = (int)(this.value + o.intValue());
		return this;
	}
	
	@Override
	public Integer power(java.lang.Number o) {
		return new Integer((int)(Math.pow((double)this.value, o.doubleValue())));
	}

	@Override
	public Integer powerEq(java.lang.Number o) {
		this.value = (int)(Math.pow((double)this.value, o.doubleValue()));
		return this;
	}
}
