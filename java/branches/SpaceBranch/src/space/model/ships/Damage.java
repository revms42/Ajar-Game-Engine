package space.model.ships;

public enum Damage {

	EXPLOSIVE("explosive"),
	IMPACT("impact"),
	PLASMA("plasma"),
	ENERGY("energy"),
	ACID("acid");
	
	private final String name;
	
	public final static Damage[] TYPES = {EXPLOSIVE,IMPACT,PLASMA,ENERGY,ACID};
	
	private Damage(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
