package space.model.component;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Vector;

import org.AbstractCharacter;
import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.model.IStats;
import org.model.Stats;

import space.model.Resource;

public class DefaultComponent<I> extends AbstractCharacter<I,String> implements
		IComponent<I> {

	private final String name;
	private final String description;
	private IComponentType type;
	private Vector<IComponentType> secondary;
	
	public DefaultComponent(
			String name,
			String description,
			IStats<String> stats,
			IDisplayContext<I,String> context,
			IDisplayFactory<I,String> factory
	) {
		super(stats, context, factory);
		this.name = name;
		this.description = description;
		secondary = new Vector<IComponentType>();
	}
	
	public DefaultComponent(String name, String description){
		super(new Stats<String>(),null,null);
		this.name = name;
		this.description = description;
	}

	@Override
	public AbstractCharacter<I,String> clone() {
		return new DefaultComponent<I>(
				this.name,
				this.description,
				this.getStats().clone(),
				this.getDisplayContext(),
				this.getDisplayFactory()
		);
	}

	@Override
	public int getDisplayDepth() {
		return 0;
	}

	@Override
	public boolean isOnScreen(Rectangle arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Shape getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost(Resource resource) {
		return this.value(resource.name()).intValue();
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public IComponentType getPrimaryType() {
		return type;
	}
	
	public void setType(IComponentType type) {
		this.type = type;
	}

	@Override
	public Vector<IComponentType> getSecondaryTypes() {
		return secondary;
	}

	public void addSecondaryType(IComponentType newType) {
		secondary.add(newType);
	}
	
	public boolean hasSecondaryType(IComponentType stype){
		return secondary.contains(stype);
	}
}
