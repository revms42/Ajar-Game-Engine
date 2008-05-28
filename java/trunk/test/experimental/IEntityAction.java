package test.experimental;

import org.interaction.IEntity;

public interface IEntityAction<K> {
	
	public enum Part{
		VALUE("Value"),
		MAX("Max"),
		MIN("Min"),
		NOMINAL("Nominal");
		
		public final String name;
		
		private Part(String name){
			this.name = name;
		}
		
		public static Part parse(String part){
			if(part.equalsIgnoreCase(VALUE.name)){
				return VALUE;
			}else if(part.equalsIgnoreCase(MAX.name)){
				return MAX;
			}else if(part.equalsIgnoreCase(MIN.name)){
				return MIN;
			}else if(part.equalsIgnoreCase(NOMINAL.name)){
				return NOMINAL;
			}else{
				return null;
			}
		}
	};
	
	public int getOrder();
	public void setEntity(IEntity<K> entity);
}
