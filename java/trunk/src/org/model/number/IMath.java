package org.model.number;

public interface IMath extends Comparable<java.lang.Number>{

	public Number minus(java.lang.Number o);
	public Number minusEq(java.lang.Number o);
	
	public Number plus(java.lang.Number o);
	public Number plusEq(java.lang.Number o);
	
	public Number times(java.lang.Number o);
	public Number timesEq(java.lang.Number o);
	
	public Number into(java.lang.Number o);
	public Number intoEq(java.lang.Number o);
	
	public Number power(java.lang.Number o);
	public Number powerEq(java.lang.Number o);
	
}
