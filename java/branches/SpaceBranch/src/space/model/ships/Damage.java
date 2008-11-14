package space.model.ships;

public enum Damage {

	EXPLOSIVE("explosive"),
	IMPACT("impact"),
	PLASMA("plasma"),
	BEAM("beam"),
	ACID("acid");
	
	private final String name;
	
	public final static Damage[] TYPES = {EXPLOSIVE,IMPACT,PLASMA,BEAM,ACID};
	
	private Damage(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
