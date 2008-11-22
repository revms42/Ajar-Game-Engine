package space.model.component;

import java.util.Vector;

import space.model.Resource;
import space.model.planet.Environment;
import space.model.ships.Cargo;
import space.model.ships.Damage;
import space.model.tech.Technology;

public enum ComponentType implements IComponentType {
	
	WEAPON("Weapon",new String[]{STAT_DAMAGETYPE,STAT_ENERGY,STAT_POWER},WeaponType.WEAPONTYPES),
	ARMOR("Armor",new String[]{STAT_DAMAGETYPE,STAT_RATING}),
	SHIELD("Shield",new String[]{STAT_DAMAGETYPE,STAT_ENERGY,STAT_RATING}),
	ENGINE("Engine",new String[]{STAT_ENERGY},EngineType.ENGINETYPES),
	BULKHEAD("Bulkhead",new String[]{}),
	CONDUIT("Conduit",new String[]{STAT_ENERGY}),
	BAY("Bay",new String[]{STAT_ENERGY},BayType.BAYTYPES),
	ELECTRICAL("Electrical",new String[]{STAT_ENERGY},ElectricalType.ELECTRICALTYPES),
	MECHANICAL("Mechanical",new String[]{STAT_ENERGY},MechanicalType.MECHANICALTYPES),
	ORBITAL("Orbital",new String[]{STAT_ENERGY},OrbitalType.ORBITALTYPES),
	MISC("Misc.",new String[]{STAT_ENERGY}),
	NULL("",new String[]{});
	
	public static ComponentType[] COMPONENTTYPES =
		{WEAPON,ARMOR,SHIELD,ENGINE,BULKHEAD,CONDUIT,BAY,ELECTRICAL,MECHANICAL,ORBITAL,MISC};
	
	private final SubType[] subs;
	private final String name;
	private final Vector<String> associatedStats;
	
	private ComponentType(String name, String[] stats, SubType... subs){
		this.name = name;
		this.subs = subs;
		
		associatedStats = new Vector<String>();
		associatedStats.add(STAT_MASS);
		associatedStats.add(STAT_SIGNATURE);
		associatedStats.add(STAT_HITPOINTS);
		
		for(Resource r : Resource.RESOURCES){
			associatedStats.add("cost" + r.shortName());
		}
		
		for(Technology t : Technology.TECHS){
			associatedStats.add(t.getName());
		}
		
		for(String stat : stats){
			if(stat == STAT_POWER || stat == STAT_RATING){
				for(Damage type : Damage.TYPES){
					associatedStats.add(stat + "(" + type.getName() + ")");
				}
			}else if(stat != STAT_DAMAGETYPE){
				associatedStats.add(stat);
			}
		}
	}
	
	public Vector<String> getAssociatedStats() {
		return associatedStats;
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
		BEAM(	"Beam",
				STAT_FIRINGARC,
				STAT_ACCURACY,
				STAT_RELOAD
		),
		CANNON(	"Cannon",
				STAT_FIRINGARC,
				STAT_ACCURACY,
				STAT_RELOAD,
				STAT_SPEED,
				STAT_BURST
		),
		ROCKET(	"Rocket",
				STAT_FIRINGARC,
				STAT_ACCURACY,
				STAT_RELOAD,
				STAT_SPEED,
				STAT_BURST,
				STAT_HITPOINTS
		),
		MISSILE("Missile",
				STAT_FIRINGARC,
				STAT_ACCURACY,
				STAT_RELOAD,
				STAT_SPEED,
				STAT_BURST,
				STAT_AGILITY,
				STAT_HITPOINTS
		),
		TORPEADO("Torpeado",
				STAT_FIRINGARC,
				STAT_ACCURACY,
				STAT_RELOAD,
				STAT_SPEED,
				STAT_AGILITY,
				STAT_HITPOINTS
		),
		BOMB(	"Bomb",
				STAT_ACCURACY,
				STAT_HITPOINTS
		),
		MINE(	"Mine",
				STAT_RADIUS,
				STAT_HITPOINTS
		);
		
		private final Vector<String> associatedStats;
		
		public static WeaponType[] WEAPONTYPES = 
			{BEAM,CANNON,ROCKET,MISSILE,TORPEADO,BOMB,MINE};
		
		private final String name;
		
		private WeaponType(String name, String... stats){
			this.name = name;
			
			associatedStats = new Vector<String>();
			
			for(String stat : stats){
				associatedStats.add(stat);
			}
		}
		
		public String getName(){
			return name;
		}
		
		public Vector<String> getAssociatedStats() {
			return associatedStats;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.WEAPON;
		}
	}
	
	public enum EngineType implements SubType {
		THRUSTER("Thruster",STAT_SPEED,STAT_AGILITY),
		WARP("Warp",STAT_SPEED),
		HYPERSPACE("Hyperspace",STAT_SPEED);
		
		public static EngineType[] ENGINETYPES = 
			{THRUSTER,WARP,HYPERSPACE};
		
		private final String name;
		
		private final Vector<String> associatedStats;
		
		private EngineType(String name, String... stats){
			this.name = name;
			
			associatedStats = new Vector<String>();
			
			for(String stat : stats){
				associatedStats.add(stat);
			}
		}
		
		public String getName(){
			return name;
		}
		
		public Vector<String> getAssociatedStats() {
			return associatedStats;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.ENGINE;
		}
	}
	
	public enum BayType implements SubType {
		CARGO("Cargo",
				Cargo.PEOPLE,
				Cargo.MINERAL1,
				Cargo.MINERAL2,
				Cargo.MINERAL3,
				Cargo.MINERAL4,
				Cargo.MINERAL5
		),
		REPAIR("Repair",Cargo.SHIPS),
		CARRIER("Carrier",Cargo.SHIPS),
		BOARDING("SpaceMarine",Cargo.MARINES),
		COLONIZATION("Colonist",Cargo.COLONISTS),
		LANDING("Assualt",Cargo.TROOPS);
		
		public static BayType[] BAYTYPES =
			{CARGO,REPAIR,CARRIER,BOARDING,COLONIZATION,LANDING};
		
		private final String name;
		
		private final Vector<String> associatedStats;
		
		private BayType(String name, Cargo... cargo){
			this.name = name;
			
			associatedStats = new Vector<String>();
			
			for(Cargo c : cargo){
				associatedStats.add("cargo(" + c.getName() + ")");
			}
			
			if(name == "Repair Bays"){
				associatedStats.add(STAT_POWER);
			}
		}
		
		public String getName(){
			return name;
		}
		
		public Vector<String> getAssociatedStats() {
			return associatedStats;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.BAY;
		}
	}
	
	public enum ElectricalType implements SubType {
		COMPUTER("Computer",STAT_POWER),
		COUNTERMEASURES("Countermeasures",STAT_POWER),
		CLOAK("Cloak",STAT_POWER),
		SCANNER("Scanner",STAT_POWER,STAT_RANGE,STAT_RADIUS),
		OVERCHARGER("Overcharger",STAT_POWER);
		
		public static ElectricalType[] ELECTRICALTYPES =
			{COMPUTER,COUNTERMEASURES,CLOAK,SCANNER,OVERCHARGER};
		
		private final String name;
		
		private final Vector<String> associatedStats;
		
		private ElectricalType(String name, String... stats){
			this.name = name;
			
			associatedStats = new Vector<String>();
			
			for(String stat : stats){
				associatedStats.add(stat);
			}
		}
		
		public String getName(){
			return name;
		}
		
		public Vector<String> getAssociatedStats(){
			return associatedStats;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.ELECTRICAL;
		}
	}
	
	public enum MechanicalType implements SubType {
		MINING("MiningBeams",
				"mining(" + Resource.MINERAL1.shortName() + ")",
				"mining(" + Resource.MINERAL2.shortName() + ")",
				"mining(" + Resource.MINERAL3.shortName() + ")",
				"mining(" + Resource.MINERAL4.shortName() + ")"),
		THRUSTER("Thrusters",STAT_AGILITY),
		TERRAFORM("Terraformer",
				"terraform(" + Environment.RADIATION.getName() + ")",
				"terraform(" + Environment.TEMPERATURE.getName() + ")",
				"terraform(" + Environment.ATMOSPHERE.getName() + ")",
				"terraform(" + Environment.GRAVITY.getName() + ")"),
		TURRET("Turret",STAT_FIRINGARC),
		EXTENDER("Extender",STAT_RANGE),
		OVERDRIVE("Overdriver",STAT_SPEED);
		
		public static MechanicalType[] MECHANICALTYPES =
			{MINING,THRUSTER,TERRAFORM,TURRET,EXTENDER,OVERDRIVE};
		
		private final String name;
		
		private final Vector<String> associatedStats;
		
		private MechanicalType(String name, String... stats){
			this.name = name;
			
			associatedStats = new Vector<String>();
			
			for(String stat : stats){
				associatedStats.add(stat);
			}
		}
		
		public String getName(){
			return name;
		}
		
		public Vector<String> getAssociatedStats(){
			return associatedStats;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.MECHANICAL;
		}
	}
	
	public enum OrbitalType implements SubType {
		MASS_DRIVER("MassAccelerator"),
		STARGATE("Stargate"),
		HYPERSPACE_NODE("HyperspaceNode"),
		CONSTRUCTION("ShipYard",STAT_WIDTH,STAT_HEIGHT);
		
		public static OrbitalType[] ORBITALTYPES = 
			{MASS_DRIVER,STARGATE,HYPERSPACE_NODE,CONSTRUCTION};
		
		private final String name;
		
		private final Vector<String> associatedStats;
		
		private OrbitalType(String name, String... stats){
			this.name = name;
			
			associatedStats = new Vector<String>();
			
			for(String stat : stats){
				associatedStats.add(stat);
			}
		}
		
		public String getName(){
			return name;
		}
		
		public Vector<String> getAssociatedStats(){
			return associatedStats;
		}
		
		public ComponentType getSuperType(){
			return ComponentType.ORBITAL;
		}
	}


}
