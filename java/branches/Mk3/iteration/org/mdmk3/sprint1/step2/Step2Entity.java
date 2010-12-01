package org.mdmk3.sprint1.step2;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.DefaultEntity;

public class Step2Entity extends DefaultEntity<Step2Attributes> {

	public Step2Entity(Node<Step2Attributes> node) {
		super(node);
		setState(new Step2GameState());
	}

}
