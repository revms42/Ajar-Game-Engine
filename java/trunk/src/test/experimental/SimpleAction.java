package test.experimental;

import org.IGameManifest;
import org.interaction.IAction;
import org.interaction.IEntity;

public class SimpleAction implements IAction<String> {
	
	public final ActionCore type;
	private final IActionTarget target;
	private final IActionArg firstarg;
	private final IActionArg secondarg; 
	private IGameManifest manifest;
	
	public SimpleAction(
			String type,
			IActionTarget target,
			IActionArg firstarg,
			IActionArg secondarg,
			IGameManifest manifest
	){
		this.type = ActionCore.parse(type);
		this.target = target;
		this.firstarg = firstarg;
		this.secondarg = secondarg;
		this.manifest = manifest;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void performAction(IEntity<String> subject,
			IEntity<String>... objects) {
		if(target != null && firstarg != null){
			if(target instanceof IEntityAction){
				IEntityAction<String> tar = (IEntityAction<String>)target;
				
				if(tar.getOrder() < 0){
					tar.setEntity(subject);
				}else{
					tar.setEntity(objects[tar.getOrder()]);
				}
			}
			
			if(firstarg != null && firstarg instanceof IEntityAction){
				IEntityAction<String> fa = (IEntityAction<String>)firstarg;
				
				if(fa.getOrder() < 0){
					fa.setEntity(subject);
				}else{
					fa.setEntity(objects[fa.getOrder()]);
				}
			}
			
			if(secondarg != null && secondarg instanceof IEntityAction){
				IEntityAction<String> sa = (IEntityAction<String>)secondarg;
				
				if(sa.getOrder() < 0){
					sa.setEntity(subject);
				}else{
					sa.setEntity(objects[sa.getOrder()]);
				}
			}
			
			Number n = type.performOpperation(firstarg,secondarg);
			target.set(n);
		}
	}

	@Override
	public IGameManifest getManifest() {
		return manifest;
	}
	
	public void setManifest(IGameManifest manifest) {
		this.manifest = manifest;
	}
}
