package space.model.component;

import space.model.ships.ICombatObject;

public class ComponentBonusFactory {

	public static int computeFinalStat(String stat, IComponent<?> component, ICombatObject<?> owner){
		//TODO: Figure out how you want to do this properly.
		return component.value(stat).intValue();
	}
}
