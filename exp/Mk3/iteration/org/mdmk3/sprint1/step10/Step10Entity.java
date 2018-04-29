package org.mdmk3.sprint1.step10;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.DefaultEntity;
import org.mdmk3.core.logic.State;

public class Step10Entity extends DefaultEntity<Step10Attributes>{

	public Step10Entity(Node<Step10Attributes> node, State<Step10Attributes> state) {
		super(node);
		setState(state);
	}

}
