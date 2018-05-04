package org.ajar.age.logic;

import org.ajar.age.Node;
import org.ajar.age.VisitorTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Queue;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogicVisitorTest extends VisitorTest<Entity> {

    @Before
    public void setup() {
        visitor = new LogicVisitor(Entity.class);
        decoratorClass = Entity.class;
    }

    @Override
    public void setupShouldVisit(Entity decoratorMock) {
        State state = mock(State.class);
        when(decoratorMock.getState()).thenReturn(state);
    }

    @Override
    public void setupShouldNotVisit(Entity decoratorMock) {
        when(decoratorMock.getState()).thenReturn(null);
    }

    @Test
    public void testVisitEntity() {
        Entity entity = mock(Entity.class);
        Queue<Event> queue = mock(Queue.class);
        Event event = mock(Event.class);
        State firstState = mock(State.class);
        State secondState = mock(State.class);

        // Visit calls.
        when(entity.getEventQueue()).thenReturn(queue);
        when(entity.getState()).thenReturn(firstState);
        when(queue.isEmpty()).thenReturn(false).thenReturn(true);
        when(queue.poll()).thenReturn(event);
        when(firstState.perform(entity,event)).thenReturn(secondState);

        visitor.visit(entity);
        verify(entity).setState(secondState);
    }
}
