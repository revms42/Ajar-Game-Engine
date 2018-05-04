package org.ajar.age;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;

public abstract class NodeTest {

    protected Node node;

    @Test
    public void testAddChildNode() {
        Node child = mock(Node.class);

        when(child.getUndecoratedNode()).thenReturn(child);

        node.addChild(child);

        assertThat(node.getChildren(), hasItem(child));

        assertTrue(node.removeChild(child));
    }

    @Test
    public void testAddNodeInOrder() {
        Node childOne = mock(Node.class);
        Node childTwo = mock(Node.class);

        when(childOne.getUndecoratedNode()).thenReturn(childOne);
        when(childTwo.getUndecoratedNode()).thenReturn(childTwo);

        node.addChild(childTwo);
        node.addChild(0, childOne);

        assertThat(node.getChildren().get(0), is(childOne));
        assertThat(node.getChildren().get(1), is(childTwo));
    }

    @Test
    public void testAddChildDefaultNode() {
        assumeTrue(DefaultNode.class.isAssignableFrom(node.getClass()));

        Node child = mock(DefaultNode.class);
        when(child.getUndecoratedNode()).thenReturn(child);

        node.addChild(child);

        assertThat(node.getChildren(), hasItem(child));
        //verify(child).setParent(node);

        assertTrue(node.removeChild(child));
        //verify(child).setParent(null);
    }

    @Test
    public void testGetRootNode() {
        assumeTrue(DefaultNode.class.isAssignableFrom(node.getClass()));

        Node root = mock(Node.class);
        Node branch = mock(Node.class);

        when(branch.getRoot()).thenReturn(root);

        ((DefaultNode)node).setParent(branch);

        assertThat(root, is(node.getRoot()));
    }

    @Test
    public void testDecorators() {
        Decorator d = mock(Decorator.class);

        node.addDecorator(d);

        assertThat(d, is(node.getDecorator(d.getClass())));
        assertThat(node, is(node.getUndecoratedNode()));

        assertTrue(node.hasCapability(d.getClass()));

        assertThat(d, is(node.removeDecorator(d.getClass())));

        assertNull(node.getDecorator(d.getClass()));
        assertFalse(node.hasCapability(d.getClass()));
    }

    @Test
    public void testVisitNode() {
        Visitor v = mock(Visitor.class);

        when(v.shouldVisit(node)).thenReturn(true);

        node.accept(v);

        verify(v).scheduleVisit(node);
    }

    @Test
    public void testVisitChildren() {
        Node childOne = mock(Node.class);
        Node childTwo = mock(Node.class);

        when(childOne.getUndecoratedNode()).thenReturn(childOne);
        when(childTwo.getUndecoratedNode()).thenReturn(childTwo);

        Visitor v = mock(Visitor.class);

        when(v.shouldVisit(node)).thenReturn(true);

        node.addChild(childOne);
        node.addChild(childTwo);

        node.accept(v);

        verify(v).scheduleVisit(node);
        verify(childOne).accept(v);
        verify(childTwo).accept(v);
    }
}
