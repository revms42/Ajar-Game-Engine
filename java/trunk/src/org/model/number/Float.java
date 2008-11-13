package org.model.number;

public class Float extends Number {
	private static final long serialVersionUID = 8961086329441422850L;
	
	private float value;
	
	public Float(Number number){
		value = number.floatValue();
	}
	
	public Float(float number){
		value = number;
	}

	@Override
	public void set(java.lang.Number number) {
		value = number.floatValue();
	}

	@Override
	public double doubleValue() {
		return (double)value;
	}

	@Override
	public float floatValue() {
		return value;
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
		float thisVal = this.value;
		float anotherVal = o.floatValue();
		return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
	}
	
	@Override
	public Float clone() {
		return new Float(value);
	}

	@Override
	public Float into(java.lang.Number o) {
		return new Float((float)(this.value / o.floatValue()));
	}

	@Override
	public Float minus(java.lang.Number o) {
		return new Float((float)(this.value - o.floatValue()));
	}

	@Override
	public Float times(java.lang.Number o) {
		return new Float((float)(this.value * o.floatValue()));
	}

	@Override
	public Float plus(java.lang.Number o) {
		return new Float((float)(this.value + o.floatValue()));
	}
	
	@Override
	public Float intoEq(java.lang.Number o) {
		this.value = (float)(this.value / o.floatValue());
		return this;
	}

	@Override
	public Float minusEq(java.lang.Number o) {
		this.value = (float)(this.value - o.floatValue());
		return this;
	}

	@Override
	public Float timesEq(java.lang.Number o) {
		this.value = (float)(this.value * o.floatValue());
		return this;
	}

	@Override
	public Float plusEq(java.lang.Number o) {
		this.value = (float)(this.value + o.floatValue());
		return this;
	}
	
	@Override
	public Float power(java.lang.Number o) {
		return new Float((float)(Math.pow((double)this.value, o.doubleValue())));
	}

	@Override
	public Float powerEq(java.lang.Number o) {
		this.value = (float)(Math.pow((double)this.value, o.doubleValue()));
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof java.lang.Number){
			return value == ((java.lang.Number)o).floatValue();
		}else{
			return super.equals(o);
		}
	}
}
