package space.model.tech;

public enum Technology {

	TECH1("AppPhys"),
	TECH2("MatSci"),
	TECH3("CompSci"),
	TECH4("MechE"),
	TECH5("ElecE"),
	TECH6("ChemE"),
	TECH7("BioE"),
	TECH8("XenSci");
	
	public final static Technology[] TECHS = {TECH1,TECH2,TECH3,TECH4,TECH5,TECH6,TECH7,TECH8};
	
	private final String name;
	
	private Technology(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
