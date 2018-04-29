package space.model.tech;

import java.io.IOException;
import java.util.Vector;

import org.xml.sax.SAXException;

import space.model.component.IComponent;

public interface ITechTreeLoader<F,I> {

	public Vector<IComponent<I>> loadTree(F file) throws SAXException, IOException;
}
