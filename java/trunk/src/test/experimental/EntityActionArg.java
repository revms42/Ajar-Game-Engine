package test.experimental;

import org.interaction.IEntity;

public class EntityActionArg<K> implements IActionArg, IEntityAction<K> {

	private IEntity<K> entity;
	private final K key;
	private final int order;
	private final Part part;
	
	public EntityActionArg(K key, int order, String part){
		this.key = key;
		this.order = order;
		this.part = Part.parse(part);
	}
	
	public void setEntity(IEntity<K> entity){
		this.entity = entity;
	}
	
	@Override
	public Number get() {
		if(entity != null){
			switch(part){
			case VALUE:
				return entity.value(key);
			case MAX:
				return entity.max(key);
			case MIN:
				return entity.min(key);
			case NOMINAL:
				return entity.nominal(key);
			default:
				return entity.value(key);
			}
		}else{
			return null;
		}
	}

	@Override
	public int getOrder() {
		return order;
	}

}
