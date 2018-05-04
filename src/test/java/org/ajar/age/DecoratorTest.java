package org.ajar.age;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class DecoratorTest {

    public abstract Decorator decorate(Node node);

    @Test
    public void testHasChildren() {
        Node node = mock(Node.class);

        when(node.hasChildren()).thenReturn(true);

        Decorator decorator = decorate(node);

        assertTrue(decorator.hasChildren());
        verify(node).hasChildren();
    }

    @Test
    public void testGetChildren() {
        Node node = mock(Node.class);
        List<Node> list = mock(List.class);

        when(node.getChildren()).thenReturn(list);

        Decorator decorator = decorate(node);

        assertEquals(decorator.getChildren(), list);
        verify(node).getChildren();
    }

    @Test
    public void testAddChild() {
        Node node = mock(Node.class);
        Node child = mock(Node.class);

        Decorator decorator = decorate(node);

        decorator.addChild(child);
        verify(node).addChild(child);
    }

    @Test
    public void testAddChildAtIndex() {
        Node node = mock(Node.class);
        Node child = mock(Node.class);

        Decorator decorator = decorate(node);

        decorator.addChild(1, child);
        verify(node).addChild(1,child);
    }

    @Test
    public void testRemoveChild() {
        Node node = mock(Node.class);
        Node child = mock(Node.class);

        when(node.removeChild(child)).thenReturn(true);

        Decorator decorator = decorate(node);

        assertTrue(decorator.removeChild(child));
        verify(node).removeChild(child);
    }

    @Test
    public void testGetUndecoratedNode() {
        Node node = mock(Node.class);

        when(node.getUndecoratedNode()).thenReturn(node);

        Decorator decorator = decorate(node);

        assertEquals(node, decorator.getUndecoratedNode());
        verify(node).getUndecoratedNode();
    }

    @Test
    public void testGetParent() {
        Node node = mock(Node.class);
        Node parent = mock(Node.class);

        when(node.getParent()).thenReturn(parent);

        Decorator decorator = decorate(node);

        assertEquals(parent, decorator.getParent());
        verify(node).getParent();
    }

    @Test
    public void testGetRoot() {
        Node node = mock(Node.class);
        Node root = mock(Node.class);

        when(node.getRoot()).thenReturn(root);

        Decorator decorator = decorate(node);

        assertEquals(root, decorator.getRoot());
        verify(node).getRoot();
    }

    @Test
    public void testHasCapabilities() {
        Node node = mock(Node.class);
        Decorator mockDecorator = mock(Decorator.class);

        when(node.hasCapability(mockDecorator.getClass())).thenReturn(true);

        Decorator decorator = decorate(node);

        assertTrue(decorator.hasCapability(mockDecorator.getClass()));
        verify(node).hasCapability(mockDecorator.getClass());
    }

    @Test
    public void testGetDecorator() {
        Node node = mock(Node.class);
        Decorator mockDecorator = mock(Decorator.class);

        // This won't work because of the ? in the when definition.
        // when(node.getDecorator(mockDecorator.getClass())).thenReturn(mockDecorator);

        Decorator decorator = decorate(node);

        // See above.
        // assertEquals(mockDecorator, decorator.getDecorator(mockDecorator.getClass()));
        decorator.getDecorator(mockDecorator.getClass());
        verify(node).getDecorator(mockDecorator.getClass());
    }

    @Test
    public void testAddDecorator() {
        Node node = mock(Node.class);
        Decorator mockDecorator = mock(Decorator.class);

        Decorator decorator = decorate(node);

        decorator.addDecorator(mockDecorator);
        verify(node).addDecorator(mockDecorator);
    }

    @Test
    public void testRemoveDecorator() {
        Node node = mock(Node.class);
        Decorator mockDecorator = mock(Decorator.class);

        // This won't work because of the ? in the when definition.
        // when(node.removeDecorator(mockDecorator.getClass())).thenReturn(mockDecorator);

        Decorator decorator = decorate(node);

        // See above.
        // assertEquals(mockDecorator, decorator.removeDecorator(mockDecorator.getClass()));
        decorator.removeDecorator(mockDecorator.getClass());
        verify(node).removeDecorator(mockDecorator.getClass());
    }

    @Test
    public void testAccept() {
        Node node = mock(Node.class);
        Visitor visitor = mock(Visitor.class);

        Decorator decorator = decorate(node);

        decorator.accept(visitor);
        verify(node).accept(visitor);
    }
}
