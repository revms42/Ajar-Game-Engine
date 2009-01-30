package space.model;


public enum Price {

	PRICE_PRODUCTION(Resource.PRODUCTION),
	PRICE_MINERAL1(Resource.MINERAL1),
	PRICE_MINERAL2(Resource.MINERAL2),
	PRICE_MINERAL3(Resource.MINERAL3),
	PRICE_MINERAL4(Resource.MINERAL4),
	PRICE_MINERAL5(Resource.MINERAL5);
	
	public static final Price[] PRICES = {
		PRICE_PRODUCTION,
		PRICE_MINERAL1,
		PRICE_MINERAL2,
		PRICE_MINERAL3,
		PRICE_MINERAL4,
		PRICE_MINERAL5
	};
	
	private final String name;
	private final String shortName;
	private final Resource resource;
	
	Price(Resource resource){
		this.resource = resource;
		this.name = resource.toString() + " cost";
		this.shortName = "cost(" + resource.shortName() + ")";
	}
	
	public String toString(){
		return name;
	}
	
	public String shortName(){
		return shortName;
	}
	
	public Resource resource(){
		return resource;
	}
}
