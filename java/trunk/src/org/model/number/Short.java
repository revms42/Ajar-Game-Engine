package org.model.number;

public class Short extends Number {
	private static final long serialVersionUID = 6042818861894263607L;
	
	private short value;
	
	public Short(Number number){
		value = number.shortValue();
	}
	
	public Short(short number){
		value = number;
	}

	@Override
	public void set(java.lang.Number number) {
		value = number.shortValue();
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
		return value;
	}

	@Override
	public byte byteValue() {
		return (byte)value;
	}
	
	@Override
	public int compareTo(java.lang.Number o) {
		short thisVal = this.value;
		short anotherVal = o.shortValue();
		return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
	}
	
	@Override
	public Short clone() {
		return new Short(value);
	}

	@Override
	public Short into(java.lang.Number o) {
		return new Short((short)(this.value / o.shortValue()));
	}

	@Override
	public Short minus(java.lang.Number o) {
		return new Short((short)(this.value - o.shortValue()));
	}

	@Override
	public Short times(java.lang.Number o) {
		return new Short((short)(this.value * o.shortValue()));
	}

	@Override
	public Short plus(java.lang.Number o) {
		return new Short((short)(this.value + o.shortValue()));
	}
	
	@Override
	public Short intoEq(java.lang.Number o) {
		this.value = (short)(this.value / o.shortValue());
		return this;
	}

	@Override
	public Short minusEq(java.lang.Number o) {
		this.value = (short)(this.value - o.shortValue());
		return this;
	}

	@Override
	public Short timesEq(java.lang.Number o) {
		this.value = (short)(this.value * o.shortValue());
		return this;
	}

	@Override
	public Short plusEq(java.lang.Number o) {
		this.value = (short)(this.value + o.shortValue());
		return this;
	}
	
	@Override
	public Short power(java.lang.Number o) {
		return new Short((short)(Math.pow((double)this.value, o.doubleValue())));
	}

	@Override
	public Short powerEq(java.lang.Number o) {
		this.value = (short)(Math.pow((double)this.value, o.doubleValue()));
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof java.lang.Number){
			return value == ((java.lang.Number)o).shortValue();
		}else{
			return super.equals(o);
		}
	}
}
