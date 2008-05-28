package org.model.number;

public abstract class Number extends java.lang.Number implements INumber {
	
	public abstract void set(java.lang.Number number);
	
	public abstract Number clone();
	
	public static Double parse(double number){
		return new Double(number);
	}
	
	public static Float parse(float number){
		return new Float(number);
	}
	
	public static Long parse(long number){
		return new Long(number);
	}
	
	public static Integer parse(int number){
		return new Integer(number);
	}
	
	public static Short parse(short number){
		return new Short(number);
	}
	
	public static Byte parse(byte number){
		return new Byte(number);
	}
}
