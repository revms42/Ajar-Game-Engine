package test.experimental;

public class StaticActionArg implements IActionArg {

	private final Number n;
	
	public StaticActionArg(Number n){
		this.n = n;
	}
	
	@Override
	public Number get() {
		return n;
	}

}
