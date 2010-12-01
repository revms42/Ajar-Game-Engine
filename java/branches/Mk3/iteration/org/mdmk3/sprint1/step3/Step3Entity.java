package org.mdmk3.sprint1.step3;

import org.mdmk3.core.Node;
import org.mdmk3.core.logic.DefaultEntity;

public class Step3Entity extends DefaultEntity<Step3Attributes> {

	public Step3Entity(Node<Step3Attributes> node) {
		super(node);
		setState(new Step3GameState());
	}

}
