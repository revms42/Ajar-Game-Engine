package space.model.ships;

import space.model.Price;
import space.model.component.IComponentType;

public enum ShipStats {

	STAT_X_POS(SpaceObjectStats.STAT_X_POS),
	STAT_Y_POS(SpaceObjectStats.STAT_Y_POS),
	STAT_ROTATION(SpaceObjectStats.STAT_ROTATION),
	STAT_WIDTH(SpaceObjectStats.STAT_WIDTH),
	STAT_HEIGHT(SpaceObjectStats.STAT_HEIGHT),
	STAT_MASS(SpaceObjectStats.STAT_MASS),
	STAT_SIGNATURE(SpaceObjectStats.STAT_SIGNATURE),
	STAT_X_VELOCITY("X Velocity"),
	STAT_Y_VELOCITY("Y Velocity"),
	STAT_THRUST("Thrust"),
	STAT_AGILITY(IComponentType.STAT_AGILITY),
	STAT_WARP("Warp Factor"),
	STAT_HYPERSPACE("Hyper Space Threshold"),
	CARGO_PEOPLE(Cargo.PEOPLE.getName()),
	CARGO_COLONISTS(Cargo.COLONISTS.getName()),
	CARGO_MARINES(Cargo.MARINES.getName()),
	CARGO_TROOPS(Cargo.TROOPS.getName()),
	CARGO_SHIPS(Cargo.SHIPS.getName()),
	CARGO_MINERAL1(Cargo.MINERAL1.getName()),
	CARGO_MINERAL2(Cargo.MINERAL2.getName()),
	CARGO_MINERAL3(Cargo.MINERAL3.getName()),
	CARGO_MINERAL4(Cargo.MINERAL4.getName()),
	CARGO_MINERAL5(Cargo.MINERAL5.getName()),
	PRICE_PRODUCTION(Price.PRICE_PRODUCTION.toString()),
	PRICE_MINERAL1(Price.PRICE_MINERAL1.toString()),
	PRICE_MINERAL2(Price.PRICE_MINERAL2.toString()),
	PRICE_MINERAL3(Price.PRICE_MINERAL3.toString()),
	PRICE_MINERAL4(Price.PRICE_MINERAL4.toString()),
	PRICE_MINERAL5(Price.PRICE_MINERAL5.toString());
	
	
	public final String name;
	
	public final static ShipStats[] COLLAPSABLE = {
		STAT_MASS,
		STAT_SIGNATURE,
		STAT_THRUST,
		STAT_AGILITY
	};
	
	public final static ShipStats[] PRICE = {
		PRICE_PRODUCTION,
		PRICE_MINERAL1,
		PRICE_MINERAL2,
		PRICE_MINERAL3,
		PRICE_MINERAL4,
		PRICE_MINERAL5
	};
	
	public final static ShipStats[] CARGO = {
		CARGO_PEOPLE,
		CARGO_COLONISTS,
		CARGO_MARINES,
		CARGO_TROOPS,
		CARGO_SHIPS,
		CARGO_MINERAL1,
		CARGO_MINERAL2,
		CARGO_MINERAL3,
		CARGO_MINERAL4,
		CARGO_MINERAL5
	};
	
	public final static ShipStats[] TOPABLE = {
		STAT_WARP,
		STAT_HYPERSPACE
	};
	
	public final static ShipStats[] STATS = {
		STAT_X_POS,
		STAT_Y_POS,
		STAT_ROTATION,
		STAT_WIDTH,
		STAT_HEIGHT,
		STAT_MASS,
		STAT_SIGNATURE,
		STAT_X_VELOCITY,
		STAT_Y_VELOCITY,
		STAT_THRUST,
		STAT_AGILITY,
		STAT_WARP,
		STAT_HYPERSPACE,
		CARGO_PEOPLE,
		CARGO_COLONISTS,
		CARGO_MARINES,
		CARGO_TROOPS,
		CARGO_SHIPS,
		CARGO_MINERAL1,
		CARGO_MINERAL2,
		CARGO_MINERAL3,
		CARGO_MINERAL4,
		CARGO_MINERAL5,
		PRICE_PRODUCTION,
		PRICE_MINERAL1,
		PRICE_MINERAL2,
		PRICE_MINERAL3,
		PRICE_MINERAL4,
		PRICE_MINERAL5
	};
	
	ShipStats(String name){
		this.name = name;
	}
}
