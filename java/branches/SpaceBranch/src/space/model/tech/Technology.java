package space.model.tech;

public enum Technology {

	TECH1("tech1"),
	TECH2("tech2"),
	TECH3("tech3"),
	TECH4("tech4"),
	TECH5("tech5"),
	TECH6("tech6"),
	TECH7("tech7"),
	TECH8("tech8");
	
	public final static Technology[] TECHS = {TECH1,TECH2,TECH3,TECH4,TECH5,TECH6,TECH7,TECH8};
	
	private final String name;
	
	private Technology(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
