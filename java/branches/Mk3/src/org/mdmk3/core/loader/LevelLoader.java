package org.mdmk3.core.loader;

import java.util.List;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Node;

@Deprecated
public interface LevelLoader<D,A extends Attributes> {

	public Node<A> loadFromDesc(List<D> desc);
	
	public void setConverter(Converter<D,A> converter);
}
