package org.model;

import org.model.number.Number;

public class CircularStat extends AbstractStat {
	private static final long serialVersionUID = -5136922543131275845L;

	public CircularStat(Number value, Number max, Number min, Number nominal){
		super(value,max,min,nominal);
	}
	
	public CircularStat(double value, double max, double min, double nominal){
		super(value,max,min,nominal);
	}
	
	public CircularStat(float value, float max, float min, float nominal){
		super(value,max,min,nominal);
	}
	
	public CircularStat(long value, long max, long min, long nominal){
		super(value,max,min,nominal);
	}
	
	public CircularStat(int value, int max, int min, int nominal){
		super(value,max,min,nominal);
	}
	
	public CircularStat(short value, short max, short min, short nominal){
		super(value,max,min,nominal);
	}
	
	public CircularStat(byte value, byte max, byte min, byte nominal){
		super(value,max,min,nominal);
	}
	
	//TODO: UNTESTED!!!!!!!!!
	public int shortestArc(java.lang.Number number){
		if(number != null){
			return (min.minus(number).plus(max.minus(value)).compareTo(value.minus(number).times(-1)));
		}else{
			return 0;
		}
	}

	@Override
	public void set(java.lang.Number number) {
		value(number);
	}

	@Override
	public void value(java.lang.Number value) {
		if(value != null){
			if(max.compareTo(value) >= 0 && min.compareTo(value) <= 0){
				this.value.set(value);
			}else if(min.compareTo(value) >= 0){
				this.value.set(max.minus(min.minus(value)));
			}else{
				this.value.set(min.plus(max.plus(value)));
			}
		}
	}	

	public CircularStat clone(){
		return new CircularStat(value.clone(),max.clone(),min.clone(),nominal.clone());
	}
}
