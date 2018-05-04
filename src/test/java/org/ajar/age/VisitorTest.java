package org.ajar.age;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class VisitorTest<D extends Decorator> {

    protected AbstractVisitor<D> visitor;
    protected Class<D> decoratorClass;

    public abstract void setupShouldVisit(D decoratorMock);

    public abstract void setupShouldNotVisit(D decoratorMock);

    @Test
    public void testShouldVisitNodeNominal() {
        Node node = mock(Node.class);

        when(node.hasCapability(decoratorClass)).thenReturn(true);

        D decorator = mock(decoratorClass);

        when(node.getDecorator(decoratorClass)).thenReturn(decorator);
        setupShouldVisit(decorator);

        assertTrue(visitor.shouldVisit(node));
    }

    @Test
    public void testShouldVisitNodeNoDecorator() {
        Node node = mock(Node.class);

        when(node.hasCapability(decoratorClass)).thenReturn(false);

        D decorator = mock(decoratorClass);

        when(node.getDecorator(decoratorClass)).thenReturn(decorator);
        setupShouldVisit(decorator);

        assertFalse(visitor.shouldVisit(node));
    }

    @Test
    public void testShouldVisitNodeNotReady() {
        Node node = mock(Node.class);

        when(node.hasCapability(decoratorClass)).thenReturn(true);

        D decorator = mock(decoratorClass);

        when(node.getDecorator(decoratorClass)).thenReturn(decorator);
        setupShouldNotVisit(decorator);

        assertFalse(visitor.shouldVisit(node));
    }

    @Test
    public void testScheduleVisitNominal() {
        Node node = mock(Node.class);
        D decorator = mock(decoratorClass);

        when(node.getDecorator(decoratorClass)).thenReturn(decorator);
        when(decorator.needsUpdate()).thenReturn(true);

        visitor.scheduleVisit(node);

        assertThat(visitor.needsUpdate, hasItem(decorator));
    }

    @Test
    public void testScheduleVisitNoUpdate() {
        Node node = mock(Node.class);
        D decorator = mock(decoratorClass);

        when(node.getDecorator(decoratorClass)).thenReturn(decorator);
        when(decorator.needsUpdate()).thenReturn(false);

        visitor.scheduleVisit(node);

        assertThat(visitor.needsUpdate, not(hasItem(decorator)));
    }

    @Test
    public void testVisitNominal() {
        Node nodeOne = mock(Node.class);
        D decoratorOne = mock(decoratorClass);

        when(nodeOne.getDecorator(decoratorClass)).thenReturn(decoratorOne);
        when(decoratorOne.needsUpdate()).thenReturn(true);

        visitor.scheduleVisit(nodeOne);

        Node nodeTwo = mock(Node.class);
        D decoratorTwo = mock(decoratorClass);

        when(nodeTwo.getDecorator(decoratorClass)).thenReturn(decoratorTwo);
        when(decoratorTwo.needsUpdate()).thenReturn(true);

        visitor.scheduleVisit(nodeTwo);

        assertThat(visitor.needsUpdate, hasItems(decoratorOne,decoratorTwo));
        visitor.visit();
        assertThat(visitor.needsUpdate, hasItems());
    }
}
