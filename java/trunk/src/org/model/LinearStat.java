package org.model;

import org.model.number.Number;

public class LinearStat extends AbstractStat {
	private static final long serialVersionUID = 4528528372834222188L;

	public LinearStat(Number value, Number max, Number min, Number nominal){
		super(value,max,min,nominal);
	}
	
	public LinearStat(double value, double max, double min, double nominal){
		super(value,max,min,nominal);
	}
	
	public LinearStat(float value, float max, float min, float nominal){
		super(value,max,min,nominal);
	}
	
	public LinearStat(long value, long max, long min, long nominal){
		super(value,max,min,nominal);
	}
	
	public LinearStat(int value, int max, int min, int nominal){
		super(value,max,min,nominal);
	}
	
	public LinearStat(short value, short max, short min, short nominal){
		super(value,max,min,nominal);
	}
	
	public LinearStat(byte value, byte max, byte min, byte nominal){
		super(value,max,min,nominal);
	}

	@Override
	public void value(java.lang.Number value) {
		if(value != null){
			if(max.compareTo(value) >= 0 && min.compareTo(value) <= 0){
				this.value.set(value);
			}else if(min.compareTo(value) >= 0){
				this.value.set(min);
			}else{
				this.value.set(max);
			}
		}
	}

	public LinearStat clone(){
		return new LinearStat(value.clone(),max.clone(),min.clone(),nominal.clone());
	}

	@Override
	public void set(java.lang.Number number) {
		value(number);
	}
}
