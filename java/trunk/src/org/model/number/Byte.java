package org.model.number;

public class Byte extends Number {
	private static final long serialVersionUID = 6042818861894263607L;
	
	private byte value;
	
	public Byte(Number number){
		value = number.byteValue();
	}
	
	public Byte(byte number){
		value = number;
	}

	@Override
	public void set(java.lang.Number number) {
		value = number.byteValue();
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
		return (long)value;
	}
	
	@Override
	public short shortValue() {
		return (short)value;
	}

	@Override
	public byte byteValue() {
		return value;
	}

	@Override
	public int compareTo(java.lang.Number o) {
		byte thisVal = this.value;
		byte anotherVal = o.byteValue();
		return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
	}

	@Override
	public Byte clone() {
		return new Byte(value);
	}

	@Override
	public Byte into(java.lang.Number o) {
		return new Byte((byte)(this.value / o.byteValue()));
	}

	@Override
	public Byte minus(java.lang.Number o) {
		return new Byte((byte)(this.value - o.byteValue()));
	}

	@Override
	public Byte times(java.lang.Number o) {
		return new Byte((byte)(this.value * o.byteValue()));
	}

	@Override
	public Byte plus(java.lang.Number o) {
		return new Byte((byte)(this.value + o.byteValue()));
	}

	@Override
	public Byte intoEq(java.lang.Number o) {
		this.value = (byte)(this.value / o.byteValue());
		return this;
	}

	@Override
	public Byte minusEq(java.lang.Number o) {
		this.value = (byte)(this.value - o.byteValue());
		return this;
	}

	@Override
	public Byte timesEq(java.lang.Number o) {
		this.value = (byte)(this.value * o.byteValue());
		return this;
	}

	@Override
	public Byte plusEq(java.lang.Number o) {
		this.value = (byte)(this.value + o.byteValue());
		return this;
	}

	@Override
	public Byte power(java.lang.Number o) {
		return new Byte((byte)(Math.pow((double)this.value, o.doubleValue())));
	}

	@Override
	public Byte powerEq(java.lang.Number o) {
		this.value = (byte)(Math.pow((double)this.value, o.doubleValue()));
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof java.lang.Number){
			return value == ((java.lang.Number)o).byteValue();
		}else{
			return super.equals(o);
		}
	}
}
