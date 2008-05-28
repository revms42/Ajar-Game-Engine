package org.model;

import org.model.number.IMath;
import org.model.number.Number;

public interface IStat extends IMath {

	public Number value();
	public void value(java.lang.Number value);
	
	public Number max();
	public void max(java.lang.Number max);
	
	public Number min();
	public void min(java.lang.Number min);
	
	public Number nominal();
	public void nominal(java.lang.Number nominal);
	
	public void reset();
	public IStat clone();
}
