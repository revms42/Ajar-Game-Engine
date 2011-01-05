package org.mdmk3.core.loader;

import java.util.List;

import org.mdmk3.core.Attributes;
import org.mdmk3.core.Decorator;
import org.mdmk3.core.DefaultNode;
import org.mdmk3.core.Node;
/**
 * This class is used by the TileMapLoader to indicate that the position for the given tile is actually not a tile.
 * @author mstockbr
 *
 * @param <A>
 */
public final class NullTileNode<A extends Attributes> extends DefaultNode<A> {

	public NullTileNode(A attrs) {
		super(attrs);
	}

	@Override
	public boolean hasChildren() {return false;}

	@Override
	public void addChild(Node<A> child) {}

	@Override
	public boolean removeChild(Node<A> child) {return false;}

	@Override
	public List<Node<A>> getChildren() {return null;}

	@Override
	public Node<A> getParent() {return null;}

	@Override
	public boolean hasCapability(Class<? extends Decorator<A>> c) {return false;}

	@Override
	public <E extends Decorator<A>> E getDecorator(Class<E> c) {return null;}

	@Override
	public void addDecorator(Decorator<A> decorator) {}

	@Override
	public <E extends Decorator<A>> E removeDecorator(Class<E> c) {return null;}
}
