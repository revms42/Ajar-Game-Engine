package test.experimental;

import java.util.Vector;

import org.IGameManifest;
import org.interaction.AbstractCondition;
import org.interaction.ActionPalette;
import org.interaction.IBounded;
import org.interaction.IEntity;

public class CollisionCondition<A,K> extends AbstractCondition<A,K>{
	
	private final boolean teststrict;

	public CollisionCondition(
			ActionPalette<A,K> palette, 
			Vector<A> actions, 
			IGameManifest manifest,
			boolean strict
	){
		super(palette,actions,manifest);
		teststrict = strict;
	}
	
	public CollisionCondition(
			ActionPalette<A,K> palette, 
			IGameManifest manifest, 
			boolean strict
	){
		super(palette,manifest);
		teststrict = strict;
	}
	
	public CollisionCondition(IGameManifest manifest, boolean strict){
		super(manifest);
		teststrict = strict;
	}
	
	public void performAction(IEntity<K> subject, IEntity<K>... objects) {
		if(subject != null && subject instanceof IBounded){
			for(IEntity<K> object : objects){
				if(object != null && object instanceof IBounded){
					if(intersects((IBounded)subject,(IBounded)object)){
						for(A action : getActions()){
							getActionPalette().performAction(action, subject, objects);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isFullfilled(IEntity<K> subject, IEntity<K>... objects) {
		if(subject != null && subject instanceof IBounded){
			for(IEntity<K> object : objects){
				if(object != null && object instanceof IBounded){
					if(intersects((IBounded)subject,(IBounded)object)){
						return true;
					}
				}
			}
		}
		
		return false;
	}

	protected boolean intersects(IBounded subject, IBounded object){
		if(teststrict){
			return 	subject.getBounds().intersects(object.getBounds().getBounds()) &&
				 	object.getBounds().intersects(subject.getBounds().getBounds());
		}else{
			return 	subject.getBounds().intersects(object.getBounds().getBounds());
		}
	}
}
