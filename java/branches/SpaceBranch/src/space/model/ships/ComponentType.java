package space.model.ships;

public enum ComponentType implements IComponentType {

	WEAPON("Weapons",WeaponType.WEAPONTYPES),
	ARMOR("Armor"),
	SHIELD("Shields"),
	ENGINE("Engines",EngineType.ENGINETYPES),
	BULKHEAD("Bulkheads"),
	CONDUIT("Conduits"),
	BAY("Bays",BayType.BAYTYPES),
	ELECTRICAL("Electrical",ElectricalType.ELECTRICALTYPES),
	MECHANICAL("Mechanical",MechanicalType.MECHANICALTYPES),
	ORBITAL("Orbital",OrbitalType.ORBITALTYPES),
	MISC("Misc.");
	
	public static ComponentType[] COMPONENTTYPES =
		{WEAPON,ARMOR,SHIELD,ENGINE,BULKHEAD,CONDUIT,BAY,ELECTRICAL,MECHANICAL,ORBITAL,MISC};
	
	private final SubType[] subs;
	private final String name;
	
	private ComponentType(String name, SubType... subs){
		this.name = name;
		this.subs = subs;
	}
	
	public String[] getAssociatedStats() {
		return null;
	}
	
	public boolean hasSubTypes(){
		return subs != null;
	}
	
	public SubType[] getSubTypes(){
		return subs;
	}
	
	public String getName(){
		return name;
	}
	
	public interface SubType extends IComponentType {
		public ComponentType getSuperType();
	}
	
	public enum WeaponType implements SubType  {
		BEAM("Beams"),
		CANNON("Cannons"),
		ROCKET("Rockets"),
		MISSILE("Missiles"),
		TORPEADO("Torpeados"),
		BOMB("Bombs"),
		MINE("Mines");
		
		public static WeaponType[] WEAPONTYPES = 
			{BEAM,CANNON,ROCKET,MISSILE,TORPEADO,BOMB,MINE};
		
		private final String name;
		
		private WeaponType(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public String[] getAssociatedStats(){
			return null;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.WEAPON;
		}
	}
	
	public enum EngineType implements SubType {
		THRUSTER("Thrusters"),
		WARP("Warp Engines"),
		HYPERSPACE("Hyperspace Uplinks");
		
		public static EngineType[] ENGINETYPES = 
			{THRUSTER,WARP,HYPERSPACE};
		
		private final String name;
		
		private EngineType(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public String[] getAssociatedStats(){
			return null;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.ENGINE;
		}
	}
	
	public enum BayType implements SubType {
		CARGO("Cargo Bays"),
		REPAIR("Repair Bays"),
		CARRIER("Carrier Berths"),
		BOARDING("Marine Berths"),
		COLONIZATION("Colonist Berths"),
		LANDING("Assualt Berths");
		
		public static BayType[] BAYTYPES =
			{CARGO,REPAIR,CARRIER,BOARDING,COLONIZATION,LANDING};
		
		private final String name;
		
		private BayType(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public String[] getAssociatedStats(){
			return null;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.BAY;
		}
	}
	
	public enum ElectricalType implements SubType {
		COMPUTER("Computers"),
		JAMMER("Jammers"),
		STEALTH("Stealth"),
		SCANNER("Scanners");
		
		public static ElectricalType[] ELECTRICALTYPES =
			{COMPUTER,JAMMER,STEALTH,SCANNER};
		
		private final String name;
		
		private ElectricalType(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public String[] getAssociatedStats(){
			return null;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.ELECTRICAL;
		}
	}
	
	public enum MechanicalType implements SubType {
		MINING("Mining Drones"),
		MANUEVER("Manuevering"),
		TERRAFORM("Terraforming"),
		MISC("Misc.");
		
		public static MechanicalType[] MECHANICALTYPES =
			{MINING,MANUEVER,TERRAFORM,MISC};
		
		private final String name;
		
		private MechanicalType(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public String[] getAssociatedStats(){
			return null;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.MECHANICAL;
		}
	}
	
	public enum OrbitalType implements SubType {
		MASS_DRIVER("Mass Drivers"),
		STARGATE("Stargates"),
		HYPERSPACE_NODE("Hyperspace Nodes"),
		CONSTRUCTION("Ship Yards");
		
		public static OrbitalType[] ORBITALTYPES = 
			{MASS_DRIVER,STARGATE,HYPERSPACE_NODE,CONSTRUCTION};
		
		private final String name;
		
		private OrbitalType(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public String[] getAssociatedStats(){
			return null;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.ORBITAL;
		}
	}

}
