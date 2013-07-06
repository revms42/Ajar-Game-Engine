package space.model.ships;

import space.model.Resource;

public enum Cargo {
	
	PEOPLE("people"),
	COLONISTS("colonists"),
	MARINES("marines"),
	TROOPS("troops"),
	SHIPS("ships"),
	MINERAL1(Resource.MINERAL1.shortName()),
	MINERAL2(Resource.MINERAL2.shortName()),
	MINERAL3(Resource.MINERAL3.shortName()),
	MINERAL4(Resource.MINERAL4.shortName()),
	MINERAL5(Resource.MINERAL5.shortName());

	public final static Cargo[] CARGOS = {
		PEOPLE,COLONISTS,MARINES,TROOPS,SHIPS,
		MINERAL1,MINERAL2,MINERAL3,MINERAL4,MINERAL5
	};
	
	private final String name;
	
	private Cargo(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
