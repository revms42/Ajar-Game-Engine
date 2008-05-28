package org.interaction.util;

import org.model.IStats;
import org.model.number.Number;

public class Geometry {

	public static <K> Number distance(
			IStats<K> position, 
			IStats<K> reference,
			K... dimensions)
	{
		if(reference != position){
			Number n = null;
			
			for(K o : dimensions){
				if(n == null){
					n = position.minus(o, reference.value(o)).power(2);
				}else{
					n.plusEq(position.minus(o, reference.value(o)).power(2));
				}
			}
			
			return n.powerEq(0.5d);
		}else{
			return Number.parse(0);
		}
		
	}
	
	public static <K> Number heading(
			IStats<K> position, 
			IStats<K> reference, 
			K x, 
			K y,
			boolean invert)
	{
		if(reference != position){
			if(invert){
				return Number.parse(
					Math.atan2(
						position.minus(y, reference.value(y)).times(-1).doubleValue(), 
						position.minus(x, reference.value(x)).times(-1).doubleValue()
					)
				);
			}else{
				return Number.parse(
					Math.atan2(
						position.minus(y, reference.value(y)).doubleValue(), 
						position.minus(x, reference.value(x)).doubleValue()
					)
				);
			}
		}else{
			return Number.parse(0);
		}
	}
	
	public static <K> boolean withinBounds(
			IStats<K> position, 
			IStats<K> reference, 
			K... dimensions)
	{
		if(reference != position){
			for(K d : dimensions){
				if(	position.value(d).compareTo(reference.max(d)) >= 0 &&
					position.value(d).compareTo(reference.min(d)) <= 0)
				{
					return false;
				}
			}
			
			return true;
		}else{
			return true;
		}
	}
}
