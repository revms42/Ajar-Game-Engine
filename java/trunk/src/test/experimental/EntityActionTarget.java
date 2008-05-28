package test.experimental;

import org.interaction.IEntity;

public class EntityActionTarget<K> implements IActionTarget, IEntityAction<K> {	
	private IEntity<K> entity;
	private final K key;
	private final int order;
	private final Part part;
	
	public EntityActionTarget(K key, int order, String part){
		this.key = key;
		this.order = order;
		this.part = Part.parse(part);
	}
	
	@Override
	public void set(Number n) {
		if(entity != null){
			switch(part){
			case VALUE:
				entity.value(key,n);
				return;
			case MAX:
				entity.max(key,n);
				return;
			case MIN:
				entity.min(key,n);
				return;
			case NOMINAL:
				entity.nominal(key,n);
				return;
			default:
				entity.value(key,n);
				return;
			}
		}
	}

	@Override
	public void setEntity(IEntity<K> entity) {
		this.entity = entity;
	}

	@Override
	public int getOrder() {
		return order;
	}

}
