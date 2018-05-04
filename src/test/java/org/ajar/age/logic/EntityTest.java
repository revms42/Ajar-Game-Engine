package org.ajar.age.logic;

import org.ajar.age.DecoratorTest;
import org.ajar.age.Node;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public abstract class EntityTest extends DecoratorTest{

    @Override
    public abstract Entity decorate(Node node);

    @Test
    public void testGetSetState() {
        Node node = mock(Node.class);
        Entity entity = decorate(node);

        State state = mock(State.class);
        entity.setState(state);

        assertEquals(state, entity.getState());
    }

    @Test
    public void testQueueEvent() {
        Node node = mock(Node.class);
        Entity entity = decorate(node);

        Event event = mock(Event.class);
        entity.queueEvent(event);

        assertThat(entity.getEventQueue(), hasItem(event));
    }
}
