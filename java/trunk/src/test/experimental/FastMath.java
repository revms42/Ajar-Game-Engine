package test.experimental;

import org.model.number.Number;

public class FastMath {

	static int CARMACK_NUMBER = 0x5f3759df;
	
	public static double sqrt(java.lang.Number val){
		double b = val.doubleValue() / 2;
		long i = Double.doubleToRawLongBits(val.doubleValue());
		
		i = CARMACK_NUMBER - (i/2);
		double a = Double.longBitsToDouble(i);
		a =  a * (0.5d - (b * a * a));
		
		return a;
	}
	
	public static boolean isBetween(Number x, Number lower, Number upper) 
			throws IllegalArgumentException 
	{
		if(lower.compareTo(upper) <= 0){
			if(lower.compareTo(x) >= 0 && upper.compareTo(x) <= 0){
				return true;
			}else{
				return false;
			}
		}else{
			throw new IllegalArgumentException("Lower cannot be larger than upper");
		}
	}
	
	public static boolean isWithin(Number x, Number start, Number bounds) 
		throws IllegalArgumentException 
	{
		if(	start.compareTo(x) <= 0){
			if(	x.minus(start).compareTo(bounds) < 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
