package space.model.planet;

public enum Environment {

	GRAVITY("Gravity"),
	RADIATION("Radiation"),
	TEMPERATURE("Temperature"),
	ATMOSPHERE("Atmosphere");
	
	public final static Environment[] ATTRIBUTES = 
		{GRAVITY,RADIATION,TEMPERATURE,ATMOSPHERE};
	
	private final String name;
	
	private Environment(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
